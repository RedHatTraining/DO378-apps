import { formatISO, parseISO } from "date-fns";
import * as service from "./ScheduleService";

const dummySchedule: service.RawScheduleData = {
    date: formatISO(new Date()),
    id: 4321,
    venueId: 1234,
    startTime: "12:34",
};

describe("ScheduleService", () => {
    beforeEach(() => {
        fetchMock.resetMocks();
    });

    describe("#get", () => {
        test("sends a GET request", async () => {
            fetchMock.mockResponseOnce(JSON.stringify(dummySchedule));

            const response = await service.get("scheduleId");

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual({
                ...dummySchedule,
                date: parseISO(dummySchedule.date),
            });
        });

        test("converts the date to a Date object", async () => {
            fetchMock.mockResponseOnce(JSON.stringify(dummySchedule));
            const schedule = await service.get("scheduleId");
            expect(schedule.date).toEqual(parseISO(dummySchedule.date));
        });

        test("throws exception when GET fails", async () => {
            fetchMock.mockRejectedValue({});

            expect.assertions(1);
            try {
                await service.get("scheduleId");
            } catch (error) {
                expect(error).toEqual({});
            }
        });
    });

    describe("#getAll", () => {
        test("sends a GET request", async () => {
            fetchMock.mockResponseOnce(JSON.stringify([dummySchedule]));

            const response = await service.getAll();

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual([
                { ...dummySchedule, date: parseISO(dummySchedule.date) },
            ]);
        });

        test("converts the dates to Date objects", async () => {
            fetchMock.mockResponseOnce(JSON.stringify([dummySchedule]));
            const schedules = await service.getAll();
            expect(schedules[0].date).toEqual(parseISO(dummySchedule.date));
        });

        test("throws exception when GET fails", async () => {
            fetchMock.mockRejectedValue({});

            expect.assertions(1);
            try {
                await service.getAll();
            } catch (error) {
                expect(error).toEqual({});
            }
        });
    });
});
