package rs.urosvesic.chatservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rs.urosvesic.chatservice.collection.Chat;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat,String> {
    @Query("{ 'members' : { $all: ?0 } }")
    Optional<Chat> findByMember1AndMember2(List<String> members);

    @Query(value = "{ 'members' : { $all: ?0 } }", fields ="{ _id: 1, messages: { $slice: -1 } }")
    Optional<Chat> findByMember1AndMember2WithoutMessages(List<String> members);

    @Query("{ 'members' : ?0  }")
    List<Chat> findByMember1OrMember2(String member);

    @Query(value ="{_id: ?0 }" , fields ="{ _id: 1, messages: { $slice: -1 } }" )
    Optional<Chat> findLastMessageFromChat(String id);

    @Query(value ="{_id: '?0' }" , fields ="{'messages': {$filter: {input: '$messages',cond: {'$and':[{'$eq': ['$$this.receiver','uros99']},{'$eq': ['$$this.seen',false]}]}}}}" )
    Optional<Chat> findMessagesByIdAndReceiverAndSeen(String chatId,String receiver, boolean seen);

    @Query(value ="{_id: '?0' }", fields = "{'id':1, 'createdAt':1,'members':1,'_class':1}")
    Optional<Chat> findByIdWithoutMessages(String s);
}
