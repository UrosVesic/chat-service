package rs.urosvesic.chatservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.chatservice.collection.Chat;
import rs.urosvesic.chatservice.controller.InboxChatDto;
import rs.urosvesic.chatservice.util.UserUtil;

@RequiredArgsConstructor
@Component
public class InboxChatMapper implements GenericMapper<InboxChatDto, Chat> {

    private final MessageResponseMapper messageResponseMapper;
    @Override
    public Chat toEntity(InboxChatDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InboxChatDto toDto(Chat entity) {
        return InboxChatDto.builder()
                .chatId(entity.getChatId())
                .with(UserUtil.getCurrentUsername().equals(entity.getMembers().get(0)) ?
                        entity.getMembers().get(1) : entity.getMembers().get(0))
                .lastMessage(entity.getMessages()==null ?
                        null : messageResponseMapper.toDto(entity.getMessages().get(entity.getMessages().size()-1)))
                .totalMessages(entity.getMessages()==null?0:entity.getMessages().size())
                .build();
    }
}
