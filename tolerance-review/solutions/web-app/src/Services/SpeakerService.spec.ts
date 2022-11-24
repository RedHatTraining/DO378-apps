import * as service from "./SpeakerService";
import { Speaker } from "../Models/Speaker";
import db from "../../db";

const dummySpeaker: Speaker = db().speakers[0];

describe("SpeakerService", () => {
    beforeEach(() => {
        fetchMock.resetMocks();
    });

    describe("#listAllSorted", () => {
        test("sends a GET request", async () => {
            fetchMock.mockResponseOnce(JSON.stringify(dummySpeaker));

            const response = await service.listAllSorted("nameFirst");

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual(dummySpeaker);
        });

        test("throws exception when GET fails", async () => {
            fetchMock.mockRejectedValue({});

            expect.assertions(1);

            try {
                const a = await service.listAllSorted("nameFirst");
                console.log(a);
            } catch (error) {
                expect(error).toEqual({});
            }
        });
    });

    describe("#findByUuid", () => {
        test("sends a GET request", async () => {
            fetchMock.mockResponseOnce(JSON.stringify(dummySpeaker));

            const response = await service.findByUuid("some-uuid");

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual(dummySpeaker);
        });
    });

    describe("#create", () => {
        test("sends a POST request", async () => {
            fetchMock.mockResponseOnce(JSON.stringify(dummySpeaker));

            const response = await service.create({
                uuid: "abcde",
                nameFirst: "test",
                nameLast: "test",
                organization: "test",
                biography: "test",
                picture: "test",
                twitterHandle: "test",
            });

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual(dummySpeaker);
        });
    });
});
