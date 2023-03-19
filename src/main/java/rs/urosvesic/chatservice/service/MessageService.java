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
import rs.urosvesic.chatservice.producer.KafkaProducer;
import rs.urosvesic.chatservice.repository.ChatRepository;
import rs.urosvesic.chatservice.repository.CustomChatRepository;
import rs.urosvesic.chatservice.util.UserUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatRepository chatRepository;
    private final CustomChatRepository customChatRepository;
    private final MessageRequestMapper messageRequestMapper;
    private final MessageResponseMapper messageResponseMapper;

    private final KafkaProducer kafkaProducer;
    private final WebSocketClient webSocketClient;

    @Transactional
    public MessageResponse sendMessage(SendMessageRequest sendMessageRequest) {
        Message message = messageRequestMapper.toEntity(sendMessageRequest);
        chatRepository.
                findByMember1AndMember2WithoutMessages(List.of(UserUtil.getCurrentUsername(),
                        sendMessageRequest.getTo()))
                .ifPresentOrElse(
                        (chat)-> customChatRepository.addMessageToChat(message,chat.getChatId()),
                        ()->chatRepository.save(Chat.builder()
                                .chatId(UUID.randomUUID().toString())
                                .members(List.of(UserUtil.getCurrentUsername(), sendMessageRequest.getTo()))
                                .createdAt(LocalDateTime.now())
                                .messages(List.of(message))
                                .build())
                );

        MessageResponse messageResponse = messageResponseMapper.toDto(message);
        kafkaProducer.notifyUserAboutNewMessage(messageResponse);
        //webSocketClient.notifyForMessage(messageResponse,UserUtil.getBearerToken());
        return messageResponse;
    }

    public Long countUnseenMessages() {
        List<Chat> byMember1OrMember2 = chatRepository.findByMember1OrMember2(UserUtil.getCurrentUsername());
        return byMember1OrMember2.stream().mapToLong((chat) -> chat.getMessages().stream().filter((msg) -> !msg.isSeen() && msg.getReceiver().equals(UserUtil.getCurrentUsername())).count()).sum();
    }
}
