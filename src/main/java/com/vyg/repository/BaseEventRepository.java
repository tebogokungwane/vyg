package com.vyg.repository;


import com.vyg.entity.BaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseEventRepository extends JpaRepository<BaseEvent, Long> {
//    List<BaseEvent> findByActiveTrue();

    Optional<BaseEvent> findByName(String name);


}

