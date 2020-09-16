package org.acme.conference.schedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Representation of the entity Schedule
 * 
 * @author jzuriaga
 *
 */
@Entity
public class Schedule extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public int id;

    @Column(name = "venue_id", nullable = false)
    public int venueId;

    public LocalDate date;

    public LocalTime startTime;

    public Duration duration;

    public static long deleteById(int id) {
        return delete("id = ?1",id);
    }

    public static Schedule merge (int id, final Schedule newSched) {
        Optional<Schedule> schedule = Schedule.findByIdOptional(id);
        if (schedule.isPresent()) {
            schedule.map(s -> {
                s.venueId=newSched.venueId;
                s.date=newSched.date;
                s.startTime=newSched.startTime;
                s.duration=newSched.duration;
                return s;
            }).orElseThrow().persist();
        }
        return schedule.orElseThrow();
    }

    @Override
    public String toString () {
        return Schedule.class.getName() + "[id=" + this.id + "]";
    }
}
