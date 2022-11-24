import * as service from "./VoteService";

describe("SpeakerService", () => {
    beforeEach(() => {
        fetchMock.resetMocks();
    });

    describe("#rateSession", () => {
        test("sends a POST request", async () => {
            fetchMock.mockResponseOnce(JSON.stringify({}));

            const response = await service.rateSession({
                session: "session-id",
                attendeeId: "atendee-id",
                rating: 3,
            });

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual({});
        });

        test("throws exception when POST fails", async () => {
            fetchMock.mockRejectedValue({});

            expect.assertions(1);
            try {
                await service.rateSession({
                    session: "session-id",
                    attendeeId: "atendee-id",
                    rating: 3,
                });
            } catch (error) {
                expect(error).toEqual({});
            }
        });
    });

    describe("#/getAverageRatingBySession", () => {
        test("sends a GET request", async () => {
            const dummyRating = { value: 3 };
            fetchMock.mockResponseOnce(JSON.stringify(dummyRating));

            const response = await service.getAverageRatingBySession("s1");

            expect(fetch).toHaveBeenCalledTimes(1);
            expect(response).toEqual(dummyRating.value); // service strips out value property
        });

        test("throws exception when GET fails", async () => {
            fetchMock.mockRejectedValue(3);

            expect.assertions(1);
            try {
                await service.getAverageRatingBySession("session-id");
            } catch (error) {
                expect(error).toEqual(3);
            }
        });
    });
});
