package com.vyg.service;

import com.vyg.entity.BaseEvent;
import com.vyg.entity.CapturedPoint;
import com.vyg.entity.Nations;
import com.vyg.model.CapturedPointRequest;
import com.vyg.repository.BaseEventRepository;
import com.vyg.repository.CapturedPointRepository;
import com.vyg.repository.NationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CapturedPointServiceImpl  {

    private final CapturedPointRepository capturedPointRepository;
    private final BaseEventRepository baseEventRepository;
    private final NationsRepository nationsRepository;

    public void capturePoints(CapturedPointRequest request) {
        LocalDate date = request.getDateCaptured();
        int week = date.get(WeekFields.ISO.weekOfYear());
        int year = date.getYear();

        Nations nation = nationsRepository.findById((long) request.getNationId())
                .orElseThrow(() -> new IllegalArgumentException("Nation not found"));

        for (Map.Entry<Integer, Integer> entry : request.getEventAttendance().entrySet()) {
            int eventId = entry.getKey();
            int numberOfPeople = entry.getValue();

            BaseEvent baseEvent = baseEventRepository.findById((long) eventId)
                    .orElseThrow(() -> new IllegalArgumentException("BaseEvent not found for ID: " + eventId));

            boolean alreadyCaptured = capturedPointRepository.existsByNation_IdAndBaseEvent_IdAndWeekNumberAndYear(
                    request.getNationId(), baseEvent.getId(), week, year
            );

            if (alreadyCaptured) {
                throw new IllegalStateException("Points already captured for event ID " + eventId + " this week.");
            }

            CapturedPoint point = CapturedPoint.builder()
                    .nation(nation)
                    .baseEvent(baseEvent) // ✅ Ensure this is not null!
                    .numberOfPeople(numberOfPeople)
                    .points(baseEvent.getDefaultPoints())
                    .dateCaptured(date)
                    .fullDate(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)))
                    .weekNumber(week)
                    .month(date.getMonthValue())
                    .year(year)
                    .capturedBy(request.getCapturedBy())
                    .totalPointsEarnedPerWeek(numberOfPeople * baseEvent.getDefaultPoints())
                    .build();

            capturedPointRepository.save(point);
        }
    }

}
