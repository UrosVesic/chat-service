package rs.urosvesic.chatservice.repository;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import rs.urosvesic.chatservice.collection.Chat;
import rs.urosvesic.chatservice.collection.Message;
import rs.urosvesic.chatservice.util.UserUtil;

@RequiredArgsConstructor
@Repository
public class CustomChatRepository{

    private final MongoTemplate template;

    public long readMessagesFromChat(String chatId){
        Query query = Query.query(Criteria
                .where("_id")
                .is(chatId));

        Update update = new Update()
                .set("messages.$[elem].seen", true)
                .filterArray(Criteria.where("elem.seen").is(false).and("elem.receiver").is(UserUtil.getCurrentUsername()));
        return template.updateMulti(query, update, Chat.class).getModifiedCount();

    }

    public void addMessageToChat(Message message, String chatId){
        Query query = Query.query(Criteria.where("_id").is(chatId));
        Update update = new Update().push("messages",message);
        UpdateResult updateResult = template.updateFirst(query, update, Chat.class);
        System.out.println(updateResult);
    }
}
