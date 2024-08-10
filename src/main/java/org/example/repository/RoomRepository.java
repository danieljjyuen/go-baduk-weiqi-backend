package org.example.repository;

import org.example.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // find rooms without a challenger
    List<Room> findByChallengerIsNull();
}
