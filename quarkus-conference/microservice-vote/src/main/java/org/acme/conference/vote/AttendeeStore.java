package org.acme.conference.vote;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

@ApplicationScoped
public class AttendeeStore {

    private AttendeeIdGenerator generator = new AttendeeIdGenerator();

    public Attendee save (Attendee attendee) {
        attendee.setId(generator.nextValue());
        attendee.persist();
        return attendee;
    }

    public Optional<Attendee> findById (String id) {
        return Optional.ofNullable(Attendee.findById(id));
    }

    public Set<Attendee> findAll () {
        return Optional.ofNullable(Attendee.findAll()
                .<Attendee>stream())
                .orElseGet(Stream::<Attendee>empty)
                .collect(Collectors.<Attendee>toSet());
    }

    public boolean delete (@NotNull Attendee attendee) {
        attendee.delete();
        return true;
    }

    private static class AttendeeIdGenerator {

        public String nextValue () {
            return UUID.randomUUID()
                    .toString();
        }

    }
}
