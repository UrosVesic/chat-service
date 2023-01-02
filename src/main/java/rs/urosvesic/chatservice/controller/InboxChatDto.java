package rs.urosvesic.chatservice.controller;

import lombok.Builder;
import lombok.Data;
import rs.urosvesic.chatservice.dto.Dto;
import rs.urosvesic.chatservice.dto.MessageResponse;

@Data
@Builder
public class InboxChatDto implements Dto {

    private String chatId;
    private String with;
    private MessageResponse lastMessage;
    private Integer totalMessages;


}
