package rs.urosvesic.chatservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.chatservice.collection.Chat;
import rs.urosvesic.chatservice.dto.ConversationDto;
import rs.urosvesic.chatservice.util.UserUtil;

@Component
@RequiredArgsConstructor
public class ConversationMapper implements GenericMapper<ConversationDto, Chat> {

    private final MessageResponseMapper messageResponseMapper;
    @Override
    public Chat toEntity(ConversationDto dto) {
        return null;
    }

    @Override
    public ConversationDto toDto(Chat entity) {
        return ConversationDto.builder()
                .chatId(entity.getChatId())
                .with(UserUtil.getCurrentUsername().equals(entity.getMembers().get(0)) ?
                        entity.getMembers().get(1) : entity.getMembers().get(0))
                .messages(entity.getMessages().stream()
                        .map(messageResponseMapper::toDto)
                        .toList())
                .build();
    }
}
