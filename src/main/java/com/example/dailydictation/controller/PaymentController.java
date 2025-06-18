package com.example.dailydictation.controller;

import com.example.dailydictation.config.VNPayConfig;
import com.example.dailydictation.entity.Order;
import com.example.dailydictation.entity.User;
import com.example.dailydictation.enums.EStatusVNPay;
import com.example.dailydictation.mapper.OrderRepository;
import com.example.dailydictation.repository.UserRepository;
import com.example.dailydictation.utils.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private VNPayConfig vnPayConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 👉 Tạo link thanh toán
    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestParam("amount") int amount,
                                           @RequestParam("userId") int userId) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = VNPayUtils.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = vnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // VNPay yêu cầu nhân 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        // 👇 Gắn userId vào OrderInfo
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef + "-" + userId);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", VNPayUtils.getCurrentDate());

        String queryUrl = VNPayUtils.buildQuery(vnp_Params);
        String hash = VNPayUtils.hmacSHA512(vnPayConfig.vnp_HashSecret, VNPayUtils.buildHashData(vnp_Params));
        String paymentUrl = vnPayConfig.vnp_PayUrl + "?" + queryUrl + "&vnp_SecureHash=" + hash;

        return ResponseEntity.ok(paymentUrl);
    }

    // 👉 Xử lý callback từ VNPay
    @GetMapping("/vnpay-return")
    public ResponseEntity<String> paymentReturn(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> fields = new TreeMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldName.startsWith("vnp_")) {
                fields.put(fieldName, fieldValue);
            }
        }

        String receivedHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        String hashData = VNPayUtils.buildHashData(fields);
        String calculatedHash = VNPayUtils.hmacSHA512(vnPayConfig.vnp_HashSecret, hashData);

        if (calculatedHash.equalsIgnoreCase(receivedHash)) {
            String responseCode = fields.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                // ✅ Parse userId từ vnp_OrderInfo
                String orderInfo = fields.get("vnp_OrderInfo"); // "Thanh toan don hang: 12345678-7"
                int userId = extractUserId(orderInfo);

                Optional<User> userOpt = userRepository.findById(userId);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();

                    Order order = Order.builder()
                            .user(user)
                            .createDate(LocalDate.now())
                            .eStatusVNPay(EStatusVNPay.OPEN) // hoặc SUCCESS tùy logic bạn
                            .build();

                    orderRepository.save(order);
                    return ResponseEntity.ok("✅ Giao dịch thành công, đơn hàng đã được tạo!");
                } else {
                    return ResponseEntity.status(404).body("❌ Không tìm thấy user với ID: " + userId);
                }
            } else {
                return ResponseEntity.ok("❌ Giao dịch thất bại!");
            }
        }

        return ResponseEntity.badRequest().body("❌ Sai chữ ký");
    }

    private int extractUserId(String orderInfo) {
        try {
            // "Thanh toan don hang: 12345678-7"
            String[] parts = orderInfo.split("-");
            return Integer.parseInt(parts[1].trim());
        } catch (Exception e) {
            throw new RuntimeException("❌ Không thể lấy userId từ orderInfo: " + orderInfo);
        }
    }
}
