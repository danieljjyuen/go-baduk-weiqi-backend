package org.example.service;

import org.example.model.Player;
import org.example.model.Room;
import org.example.repository.PlayerRepository;
import org.example.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RoomRepository roomRepository;

    public RoomService(PlayerRepository playerRepository, RoomRepository roomRepository) {
        this.playerRepository = playerRepository;
        this.roomRepository = roomRepository;
    }


    public Room createRoom(String name, Long ownerId){
        Room room = new Room();
        Player owner = playerRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));
        room.setName(name);
        room.setOwner(owner);
        return roomRepository.save(room);

    }

    public Room joinRoom(Long roomId, Long playerId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        if(room.getChallenger() != null){
            throw new RuntimeException("Room is full");
        }

        room.setChallenger(player);
        return roomRepository.save(room);
    }

}
