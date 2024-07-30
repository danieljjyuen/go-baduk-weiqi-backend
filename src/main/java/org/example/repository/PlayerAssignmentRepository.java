package org.example.repository;


import org.example.model.PlayerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAssignmentRepository extends JpaRepository<PlayerAssignment, Long> {
}
