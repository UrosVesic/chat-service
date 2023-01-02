package rs.urosvesic.chatservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.chatservice.collection.Chat;
import rs.urosvesic.chatservice.collection.Message;
import rs.urosvesic.chatservice.controller.InboxChatDto;
import rs.urosvesic.chatservice.dto.SendMessageRequest;
import rs.urosvesic.chatservice.service.ChatService;
import rs.urosvesic.chatservice.util.UserUtil;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MessageRequestMapper implements GenericMapper<SendMessageRequest, Message> {

    private final ChatService chatService;
    @Override
    public Message toEntity(SendMessageRequest dto) {
        InboxChatDto chat = chatService.getById(dto.getChatId());
        return Message.builder()
                .content(dto.getContent())
                .sender(UserUtil.getCurrentUsername())
                .receiver(chat.getWith())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public SendMessageRequest toDto(Message entity) {
        throw new UnsupportedOperationException();
    }

    public Message toEntityWithChat(SendMessageRequest messageRequest, Chat chat){
        Message message = toEntity(messageRequest);
        message.setReceiver(chat.getMembers()
                .stream()
                .filter(m->m.equals(UserUtil.getCurrentUsername()))
                .findFirst()
                .get());
        return message;
    }


}
