package ru.nsu.fit.borzov.chessserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class PingController {
    private final SimpMessagingTemplate messagingTemplate;

    public PingController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(@Payload String message) {
        Logger.getLogger("LOGGER").info("HELLO!!! " + message);
//        messagingTemplate.convertAndSend(
//                "hello", message);
//        new Thread(this::greeting).start();
        return message;
    }

//    public void greeting() {
//        messagingTemplate.convertAndSend(
//                "hello", "HEEEEEELLLLOOOO");
//    }
}
