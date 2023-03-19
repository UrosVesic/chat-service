package rs.urosvesic.chatservice.mapper;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;
import rs.urosvesic.chatservice.collection.Message;
import rs.urosvesic.chatservice.dto.MessageResponse;
@Component
public class MessageResponseMapper implements GenericMapper<MessageResponse, Message> {
    @Override
    public Message toEntity(MessageResponse dto) {
        return null;
    }

    @Override
    public MessageResponse toDto(Message entity) {
        PrettyTime p = new PrettyTime();
        return MessageResponse.builder()
                .content(entity.getContent())
                .from(entity.getSender())
                .to(entity.getReceiver())
                .time(p.format(entity.getCreatedAt()))
                .seen(entity.isSeen())
                .build();
    }
}
