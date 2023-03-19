package rs.urosvesic.chatservice.urls;

public interface Urls {

    static final String SEND_MESSAGE = "/api/message";
    static final String NEW_MESSAGE_COUNT = "/api/message/new-msg-count";
    static final String SAVE_CHAT = "/api/chat/{username}";
    static final String READ_MESSAGES_FROM_CHAT = "/api/chat/read/{chatId}";
    static final String GET_INBOX = "/api/chat/inbox";
    static final String GET_CONVERSATION_BY_PARTICIPANTS = "/api/chat/{with}";
}
