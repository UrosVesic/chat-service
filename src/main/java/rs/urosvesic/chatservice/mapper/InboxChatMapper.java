package rs.urosvesic.chatservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.chatservice.collection.Chat;
import rs.urosvesic.chatservice.dto.InboxChatDto;
import rs.urosvesic.chatservice.util.UserUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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
        ZonedDateTime ect = entity.getMessages().get(entity.getMessages().size() - 1).getCreatedAt().atZone(ZoneId.of("ECT", ZoneId.SHORT_IDS));
        int minute = ect.getMinute();
        int hour=ect.getHour();
        String min;
        if(minute<10){
            min = "0"+minute;
        }else{
            min = minute+"";
        }
        return InboxChatDto.builder()
                .chatId(entity.getChatId())
                .with(UserUtil.getCurrentUsername().equals(entity.getMembers().get(0)) ?
                        entity.getMembers().get(1) : entity.getMembers().get(0))
                .content(entity.getMessages()==null ?
                        null : formatContent(entity))
                .newMessages(entity.getMessages()==null ?
                        0L:entity.getMessages()
                        .stream()
                        .filter(m->!m.isSeen() && m.getReceiver().equals(UserUtil.getCurrentUsername())).count())
                .time(hour +":"+min)
                .build();
    }

    private String formatContent(Chat entity) {
        String content = entity.getMessages().get(entity.getMessages().size() - 1).getContent();
        if(content.length()>20){
            return content.substring(0,16)+"...";
        }else{
            return content;
        }
    }
}
