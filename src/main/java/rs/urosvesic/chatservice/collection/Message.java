package rs.urosvesic.chatservice.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements MyCollection{

    String sender;
    private String receiver;
    private String content;
    private LocalDateTime createdAt;
    private boolean seen;
}
