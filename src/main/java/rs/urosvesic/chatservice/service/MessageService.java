package rs.urosvesic.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.urosvesic.chatservice.client.WebSocketClient;
import rs.urosvesic.chatservice.collection.Chat;
import rs.urosvesic.chatservice.collection.Message;
import rs.urosvesic.chatservice.dto.MessageResponse;
import rs.urosvesic.chatservice.dto.SendMessageRequest;
import rs.urosvesic.chatservice.mapper.MessageRequestMapper;
import rs.urosvesic.chatservice.mapper.MessageResponseMapper;
import rs.urosvesic.chatservice.repository.ChatRepository;
import rs.urosvesic.chatservice.repository.CustomChatRepository;
import rs.urosvesic.chatservice.util.UserUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatRepository chatRepository;
    private final CustomChatRepository customChatRepository;
    private final MessageRequestMapper messageRequestMapper;
    private final MessageResponseMapper messageResponseMapper;

    //private final KafkaProducer kafkaProducer;
    private final WebSocketClient webSocketClient;

    @Transactional
    public MessageResponse sendMessage(SendMessageRequest sendMessageRequest) {
        Optional<Chat> chatOpt = chatRepository.findByIdWithoutMessages(sendMessageRequest.getChatId());
        Chat chat = chatOpt.orElseThrow(() -> new RuntimeException("Chat does not exist"));

        if(!chat.getMembers().contains(UserUtil.getCurrentUsername())){
            throw new RuntimeException("You are not participant of this chat");
        }

        Message message = messageRequestMapper.toEntity(sendMessageRequest);
        customChatRepository.addMessageToChat(message,sendMessageRequest.getChatId());
        MessageResponse messageResponse = messageResponseMapper.toDto(message);
        //kafkaProducer.notifyUserAboutNewMessage(messageResponse);
        webSocketClient.notifyForMessage(messageResponse,UserUtil.getBearerToken());
        return messageResponse;
    }
}
