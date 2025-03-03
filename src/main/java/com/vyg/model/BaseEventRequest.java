package com.vyg.model;


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
