package rs.urosvesic.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.urosvesic.chatservice.dto.ConversationDto;
import rs.urosvesic.chatservice.dto.MessageResponse;
import rs.urosvesic.chatservice.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{username}")
    private ResponseEntity<Void> save(@PathVariable("username") String member2){
        chatService.save(member2);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/read/{chatId}")
    public ResponseEntity readMessagesFromChat(@PathVariable String chatId){
        chatService.readMessagesFrom(chatId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*@GetMapping
    private ResponseEntity<List<InboxChatDto>> getAllChats(){
        List<InboxChatDto> allChats = chatService.getAllChats();
        return new ResponseEntity<>(allChats, HttpStatus.CREATED);
    }*/

    @GetMapping("/{chatId}/last-message")
    public ResponseEntity<MessageResponse> getLastMessageFromChat(@PathVariable String chatId){
        MessageResponse message = chatService.getLastMessageFromChat(chatId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<InboxChatDto>> getInbox(){
        List<InboxChatDto> inbox = chatService.getInbox();
        return new ResponseEntity<>(inbox, HttpStatus.OK);
    }

    @GetMapping("/{with}")
    private ResponseEntity<ConversationDto> getConversationByParticipant(@PathVariable String with){
        ConversationDto chatByParticipant = chatService.getChatByParticipant(with);
        return new ResponseEntity<>(chatByParticipant,HttpStatus.OK);
    }

}
