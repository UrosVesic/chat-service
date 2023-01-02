package rs.urosvesic.chatservice.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "chat")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Chat implements MyCollection {

    @Id
    private String chatId;
    private LocalDateTime createdAt;
    private List<String> members;
    private List<Message> messages;
    private Long totalMessages;

}
