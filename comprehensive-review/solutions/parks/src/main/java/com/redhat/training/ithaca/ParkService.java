package com.redhat.training.ithaca;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import com.redhat.training.ithaca.Park.Status;

@ApplicationScoped
public class ParkService {
    private Set<Park> parks = Collections.newSetFromMap(Collections.synchronizedMap(new HashMap<>()));
    private ParkIdGenerator generator = new ParkIdGenerator();
    private Park newPark = new Park();

    @PostConstruct
    void initData() {
        parks.add(new Park("Park Güell", 1000, "Barcelona", Status.CLOSED));
        parks.add(new Park("Ibirapuera", 500, "São Paulo", Status.CLOSED));
    }

    public Set<Park> list() {
        return parks;
    }

    public Park create(Park park) {
        park.uuid=generator.generate();
        parks.add(park);
        return park;
    }

    public boolean delete(String uuid) {
        return parks.removeIf(parks -> parks.getUuid().equals(uuid));
    }

    public void update(Park park) {
        delete(park.getUuid());
        create(park);
    }

    public Park getParkByUuid (String Uuid) {
       if(Uuid == null) {
           throw new NotFoundException();
       }
       for(Park park : parks) {
           if(park.getUuid().equals(Uuid)) {
               newPark = park;
           }
       }
       return newPark;
    }

    private static class ParkIdGenerator {

        public String generate() {
            return UUID.randomUUID().toString();
        }
    }
}
