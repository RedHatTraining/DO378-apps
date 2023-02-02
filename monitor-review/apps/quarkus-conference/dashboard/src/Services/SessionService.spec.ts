import * as service from "./SessionService";
import { Session } from "../Models/Session";
import db from "../../db";

describe("SessionService", () => {
    beforeEach(() => {
        fetchMock.resetMocks();
    });

    describe("#get", () => {
        test("sends a GET request", async () => {
            const dummySession: Session = db().sessions[0];
            fetchMock.mockResponseOnce(JSON.stringify(dummySession));

            const response = await service.get("speaker-uuid");

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual(dummySession);
        });

        test("throws exception when GET fails", async () => {
            fetchMock.mockRejectedValue({});

            expect.assertions(1);
            try {
                await service.get("speaker-uuid");
            } catch (error) {
                expect(error).toEqual({});
            }
        });
    });

    describe("#getAll", () => {
        test("sends a GET request", async () => {
            const dummySessions: Session[] = db().sessions;
            fetchMock.mockResponseOnce(JSON.stringify(dummySessions));

            const response = await service.getAll();

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual(dummySessions);
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
