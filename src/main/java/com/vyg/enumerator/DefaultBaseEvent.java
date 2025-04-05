package com.vyg.enumerator;

public enum DefaultBaseEvent {

    SUNDAY_SERVICE("Sunday Service", 50),
    SUNDAY_TSHIRT("Sunday (T-shirt)", 50),
    YOUTH_HANGOUT("New Youth for Hangout", 100),
    RETURNING_YOUTH("Returning Youth for Hangout", 50),
    HANGOUT_PER_PERSON("Hangout per person", 50),
    DISCIPLES_MEETING("Disciples Meeting", 50),
    SATURDAY_WORKOUT("Saturday Workout", 50),
    SATURDAY_PROJECTS("Saturday (Projects)", 50),
    DOUBLE_YOUR_NATION("Double your Nation", 1000),
    WEDNESDAY_SERVICE("Wednesday (Service)", 80),
    FRIDAY_SERVICE("Friday (Service)", 80),
    THURSDAY_TOL("Thursday (TOL)", 50),
    STAND_EVENT("Stand Event", 100),
    MONDAY_SERVICE("Monday (Service)", 50);

    private final String eventName;
    private final int defaultPoints;


     DefaultBaseEvent(String eventName, int defaultPoints) {
        this.eventName = eventName;
        this.defaultPoints = defaultPoints;
    }

    public String getEventName() {
        return eventName;
    }

    public int getDefaultPoints() {
        return defaultPoints;
    }
}
