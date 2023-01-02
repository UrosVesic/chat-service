package rs.urosvesic.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.urosvesic.chatservice.collection.Chat;
import rs.urosvesic.chatservice.controller.InboxChatDto;
import rs.urosvesic.chatservice.dto.ConversationDto;
import rs.urosvesic.chatservice.dto.MessageResponse;
import rs.urosvesic.chatservice.mapper.ConversationMapper;
import rs.urosvesic.chatservice.mapper.InboxChatMapper;
import rs.urosvesic.chatservice.mapper.MessageResponseMapper;
import rs.urosvesic.chatservice.repository.ChatRepository;
import rs.urosvesic.chatservice.repository.CustomChatRepository;
import rs.urosvesic.chatservice.util.UserUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final InboxChatMapper inboxChatMapper;
    private final ChatRepository chatRepository;
    private final ConversationMapper conversationMapper;

    private final MessageResponseMapper messageResponseMapper;
    private final CustomChatRepository customChatRepository;

    public void save(String member2) {
        chatRepository.findByMember1AndMember2(List.of(UserUtil.getCurrentUsername(), member2))
                .ifPresent(c -> {
                    throw new RuntimeException("Chat between "
                            + c.getMembers().get(0)
                            + " and "
                            + c.getMembers().get(1)
                            + " already exists");
                });
        Chat chat = Chat.builder()
                .chatId(UUID.randomUUID().toString())
                .members(List.of(UserUtil.getCurrentUsername(), member2))
                .createdAt(LocalDateTime.now())
                .build();
        chatRepository.save(chat);
    }

    public List<InboxChatDto> getAllChats() {
        return chatRepository
                .findAll()
                .stream()
                .map(inboxChatMapper::toDto)
                .collect(Collectors.toList());
    }

    public InboxChatDto getById(String chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        return inboxChatMapper.toDto(chat);
    }


    public ConversationDto getChatByParticipant(String with) {
        String currentUsername = UserUtil.getCurrentUsername();
        Chat chat = chatRepository
                .findByMember1AndMember2(List.of(currentUsername, with))
                .orElseThrow(() -> new RuntimeException("Chat does not exist"));
        return conversationMapper.toDto(chat);
    }

    public List<InboxChatDto> getInbox() {
        List<Chat> inbox = chatRepository.findByMember1OrMember2(UserUtil.getCurrentUsername());
        return inbox.stream().map(inboxChatMapper::toDto).collect(Collectors.toList());
    }

    public MessageResponse getLastMessageFromChat(String chatId) {
        Chat chat = chatRepository.findLastMessageFromChat(chatId).orElseThrow(() -> new RuntimeException("Message not found"));
        return messageResponseMapper.toDto(chat.getMessages().get(0));
    }

    public void readMessagesFrom(String chatId) {
        customChatRepository.readMessagesFromChat(chatId);
    }
}
