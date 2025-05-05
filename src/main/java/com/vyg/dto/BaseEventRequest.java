package com.vyg.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseEventRequest {
    private String eventName;
    private int defaultPoints;
    private boolean showEvent = true;
}
