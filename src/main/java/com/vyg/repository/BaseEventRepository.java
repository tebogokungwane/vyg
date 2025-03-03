package com.vyg.repository;


import com.vyg.entity.BaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseEventRepository extends JpaRepository<BaseEvent, Long> {
    List<BaseEvent> findByActiveTrue();
}

