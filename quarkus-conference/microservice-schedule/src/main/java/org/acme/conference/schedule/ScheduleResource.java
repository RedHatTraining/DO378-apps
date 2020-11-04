package org.acme.conference.schedule;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource {

    @POST
    @Consumes("application/json")
    @Transactional
    public Response add (final Schedule schedule) {
        schedule.persist();
        return Response.created(URI.create("/schedule/" + schedule.id))
                .entity(schedule)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Transactional
    public Response update (@PathParam("id") final int id, final Schedule schedule) {
        if (null == schedule) {
            throw new BadRequestException();
        }

        final Schedule created = Schedule.merge(id, schedule);
        return Response.created(URI.create("/schedule/" + created.id))
                .entity(created)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response retrieve (@PathParam("id") final int id) {
        return Schedule.findByIdOptional(id)
                .map(schedule -> Response.ok(schedule)
                        .build())
                .orElseThrow(NotFoundException::new);
    }

    @GET
    @Path("/all")
    public Response allSchedules () {
        final List<Schedule> allSchedules = Schedule.findAll().list();
        final GenericEntity<List<Schedule>> entity = buildEntity(allSchedules);
        return Response.ok(entity)
                .build();
    }

    @GET
    @Path("/venue/{venueId}")
    public Response allForVenue (@PathParam("venueId") final int venueId) {
        final List<Schedule> schedulesByVenue = Schedule.find("venueId", venueId).list();
        final GenericEntity<List<Schedule>> entity = buildEntity(schedulesByVenue);
        return Response.ok(entity)
                .build();
    }

    @GET
    @Path("/active/{dateTime}")
    public Response activeAtDate (@PathParam("dateTime") final String dateTimeString) {
        final LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
        final List<Schedule> schedulesByDate = Schedule.find("date", dateTime.toLocalDate()).list();
        final List<Schedule> activeAtTime = schedulesByDate.stream()
                .filter(schedule -> isTimeInSchedule(dateTime.toLocalTime(), schedule))
                .collect(Collectors.toList());
        final GenericEntity<List<Schedule>> entity = buildEntity(activeAtTime);
        return Response.ok(entity)
                .build();
    }

    @GET
    @Path("/all/{date}")
    public Response allForDay (@PathParam("date") final String dateString) {
        final LocalDate date = LocalDate.parse(dateString);
        final List<Schedule> schedulesByDate = Schedule.find("date", date).list();
        final GenericEntity<List<Schedule>> entity = buildEntity(schedulesByDate);
        return Response.ok(entity)
                .build();
    }

    @DELETE
    @Path("/{scheduleId}")
    @Transactional
    public Response remove(@PathParam("scheduleId") final int scheduleId) {
        if (Schedule.deleteById(scheduleId) > 0) {
            return Response.noContent()
                .build();
        } else {
            throw new NotFoundException();
        }
    }

    private GenericEntity<List<Schedule>> buildEntity (final List<Schedule> scheduleList) {
        return new GenericEntity<List<Schedule>>(scheduleList) {};
    }

    private boolean isTimeInSchedule (final LocalTime currentTime, final Schedule schedule) {
        final LocalTime scheduleStartTime = schedule.startTime;
        final LocalTime scheduleEndTime = scheduleStartTime.plus(schedule.duration);
        return scheduleStartTime.isBefore(currentTime) && scheduleEndTime.isAfter(currentTime);
    }

}
