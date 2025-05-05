package com.vyg.service;


import com.vyg.entity.BaseEvent;
import com.vyg.dto.BaseEventRequest;

import java.util.List;

public interface BaseEventService {

  List<BaseEvent> getAllEvents();

  BaseEvent createEvent(BaseEventRequest baseEventRequest);

  BaseEvent updateEvent(Long id, BaseEventRequest baseEventRequest);


}

