package org.acme.conference.vote;

import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * @author jzuriaga
 *
 */
@ApplicationScoped
public class AttendeeDAO {

    private AttendeeStore store;

    @Inject
    public AttendeeDAO(AttendeeStore attendeeStore) {
        this.store = attendeeStore;
    }

    public Attendee create (final Attendee attendee) {
        return store.save(attendee);
    }

    public Optional<Attendee> update (final String id, @NotNull final Attendee attendee) {
        Optional<Attendee> original = get(id);
        original.ifPresent(a -> {
            a.setName(attendee.getName());
            a.update();
        });
        return original;
    }

    public Optional<Attendee> get (final String id) {
        return store.findById(id);
    }

    public Set<Attendee> findAll () {
        return store.findAll();
    }

    public boolean delete (@NotNull final Attendee attendee) {
        return store.delete(attendee);
    }

}
