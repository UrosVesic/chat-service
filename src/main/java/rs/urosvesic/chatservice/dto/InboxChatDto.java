package rs.urosvesic.chatservice.dto;

import lombok.Builder;
import lombok.Data;
import rs.urosvesic.chatservice.dto.Dto;

@Data
@Builder
public class InboxChatDto implements Dto {

    private String chatId;
    private String with;
    private String content;
    private Long newMessages;
    private String time;


}
