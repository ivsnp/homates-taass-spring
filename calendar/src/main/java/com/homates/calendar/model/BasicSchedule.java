package com.homates.calendar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.logicsquad.recurring.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
public class BasicSchedule {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @OneToMany
    private List<ScheduleElement> elements;

    /**
     * Constructor
     *
     * @param elements comprising {@link ScheduleElement}s
     * @throws NullPointerException if {@code elements} is {@code null}
     */
    BasicSchedule(List<ScheduleElement> elements) {
        this.elements = elements;
        return;
    }

    public boolean isOccurring(String event, LocalDate date) {
        for (ScheduleElement e : elements) {
            if (e.event().equals(event) && e.isOccurring(date)) {
                return true;
            }
        }
        return false;
    }


    public List<LocalDate> datesInRange(String event, LocalDate start, LocalDate end) {
        List<LocalDate> result = new ArrayList<>();
        LocalDate cursor = start;
        while (cursor.equals(end) || cursor.isBefore(end)) {
            if (isOccurring(event, cursor)) {
                result.add(cursor);
            }
            cursor = cursor.plusDays(1);
        }
        return result;
    }


    public LocalDate nextOccurrence(String event, LocalDate date) {
        LocalDate cursor = date;
        while (!isOccurring(event, cursor)) {
            cursor = cursor.plusDays(1);
        }
        return cursor;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[').append(this.getClass().getSimpleName()).append(": ").append(elements.stream().map(e -> e.toString()).collect(Collectors.joining(", ")))
                .append(']');
        return sb.toString();
    }


    public Stream<LocalDate> futureDates(String event, LocalDate start) {
        return Stream.iterate(nextOccurrence(event, start), seed -> nextOccurrence(event, seed.plusDays(1)));
    }


    public Stream<LocalDate> pastDates(String event, LocalDate start) {
        return Stream.iterate(previousOccurrence(event, start), seed -> previousOccurrence(event, seed.minusDays(1)));
    }


    public LocalDate previousOccurrence(String event, LocalDate date) {
        LocalDate cursor = date;
        while (!isOccurring(event, cursor)) {
            cursor = cursor.minusDays(1);
        }
        return cursor;
    }
}
