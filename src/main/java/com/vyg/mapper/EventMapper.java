package com.vyg.mapper;

import com.vyg.entity.BaseEvent;
import com.vyg.dto.BaseEventRequest;

public class EventMapper {

    public static BaseEvent toEntity(BaseEventRequest baseEventRequest){

        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setName(baseEventRequest.getEventName());
        baseEvent.setDefaultPoints(baseEventRequest.getDefaultPoints());
        return baseEvent;
    }
}
