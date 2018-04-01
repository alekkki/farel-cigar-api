package com.farelcigar.api.repository;

import com.farelcigar.api.domain.EventPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventPictureRepository extends JpaRepository<EventPicture, Long> {

    List<EventPicture> findByEventId(Long eventId);
}
