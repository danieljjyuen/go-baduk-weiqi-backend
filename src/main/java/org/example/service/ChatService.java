package org.example.service;

import org.example.model.ChatMessage;
import org.example.model.Player;
import org.example.model.Room;
import org.example.repository.ChatMessageRepository;
import org.example.repository.PlayerRepository;
import org.example.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ChatService {

    private final RoomRepository roomRepository;

    private final PlayerRepository playerRepository;

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatService(RoomRepository roomRepository, PlayerRepository playerRepository, ChatMessageRepository chatMessageRepository) {
        this.roomRepository = roomRepository;
        this.playerRepository = playerRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Transactional
    public ChatMessage sendChatMessage(Long roomId, Long playerId, String message) {
        Room room  = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoom(room);
        chatMessage.setPlayer(player);
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(LocalDateTime.now());

        room.getChatMessages().add(chatMessage);

        return chatMessageRepository.save(chatMessage);
    }
}
