package com.example.article.controller;

import com.example.article.payload.Note;
import com.example.article.servise.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sendNotification")
public class WirabaseController {
    @Autowired
    FirebaseMessagingService firebaseService;


    @GetMapping("/sendNotification/save")
    public String sendNotification(@RequestBody Note note,
                                   @RequestParam String token) throws FirebaseMessagingException {
        return firebaseService.sendNotification(note, token);
    }
}
