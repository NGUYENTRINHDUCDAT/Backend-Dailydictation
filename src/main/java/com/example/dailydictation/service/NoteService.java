package com.example.dailydictation.service;

import com.example.dailydictation.dto.request.NoteRequest;
import com.example.dailydictation.dto.response.NoteResponse;
import com.example.dailydictation.entity.Note;
import com.example.dailydictation.entity.User;
import com.example.dailydictation.mapper.NoteMapper;
import com.example.dailydictation.repository.NoteRepository;
import com.example.dailydictation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteMapper noteMapper;


    public NoteResponse createNote(NoteRequest noteRequest) {
        User user = userRepository.findUserById(noteRequest.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        Note note = Note.builder()
                .content(noteRequest.getContent())
                .createDate(LocalDate.now())
                .user(user)
                .build();
        Note savedNote = noteRepository.save(note);
        return noteMapper.toNoteResponse(savedNote);
    }

    public List<NoteResponse> showAllNote(int userId) {
        List<Note> notes = noteRepository.findAllByUserId(userId);
        return notes.stream()
                .map(noteMapper::toNoteResponse)
                .collect(Collectors.toList());
    }
}
