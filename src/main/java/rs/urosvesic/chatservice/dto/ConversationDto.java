package rs.urosvesic.chatservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConversationDto implements Dto {

    private String chatId;
    private String with;
    private List<MessageResponse> messages;

}
