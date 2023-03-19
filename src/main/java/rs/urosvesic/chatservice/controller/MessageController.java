package rs.urosvesic.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.urosvesic.chatservice.dto.MessageResponse;
import rs.urosvesic.chatservice.dto.SendMessageRequest;
import rs.urosvesic.chatservice.service.ChatService;
import rs.urosvesic.chatservice.service.MessageService;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody SendMessageRequest message){
        MessageResponse messageResponse = messageService.sendMessage(message);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/last/{chatId}")
    public ResponseEntity<MessageResponse> lastFromChat(@PathVariable  String chatId){
        MessageResponse messageResponse = chatService.getLastMessageFromChat(chatId);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/new-msg-count")
    public ResponseEntity<Long> newMessageCount(){
        Long i= messageService.countUnseenMessages();
        return new ResponseEntity<>(i, HttpStatus.OK);
    }


}
