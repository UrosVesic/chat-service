package rs.urosvesic.chatservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse implements Dto{


    private String sender;
    private String receiver;
    private String content;
    private String time;
    private boolean seen;

}
