package com.example.demo.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.services.FirestoreListenerService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EventController {

    private final FirestoreListenerService listenerService;

    public EventController(FirestoreListenerService listenerService) {
        this.listenerService = listenerService;
    }

    @GetMapping("/events")
    public SseEmitter streamEvents() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        var consumer = (java.util.function.Consumer<String>) change -> {
            try {
                emitter.send(SseEmitter.event().data(change));
            } catch (IOException e) {
                emitter.complete();
            }
        };

        listenerService.subscribe(consumer);

        emitter.onCompletion(() -> listenerService.unsubscribe(consumer));
        emitter.onTimeout(() -> listenerService.unsubscribe(consumer));

        return emitter;
    }
}