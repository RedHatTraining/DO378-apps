import React from "react";
import { render, waitForElement } from "@testing-library/react";
import { SessionDetailPage } from "./SessionDetailPage";
import * as sessionService from "../Services/SessionService";
import * as voteService from "../Services/VoteService";
import { MemoryRouter, Route } from "react-router-dom";

jest.mock("../Services/SessionService");
jest.mock("../Services/VoteService");

describe("SessionDetailPage", () => {
    const urlParams = {
        params: { sessionId: "s1" },
    };

    beforeEach(() => {
        (sessionService.get as jest.Mock).mockResolvedValue({
            id: "s1",
            schedule: 1500,
            speakers: [{ id: 1, name: "Jon" }],
        });
        (voteService.getAverageRatingBySession as jest.Mock).mockResolvedValue(
            4
        );
    });

    test("renders the loader while loading data", async () => {
        (sessionService.get as jest.Mock).mockResolvedValue(null);

        const { getByRole } = render(
            <MemoryRouter>
                <Route>
                    <SessionDetailPage match={urlParams} />
                </Route>
            </MemoryRouter>
        );

        await waitForElement(() => getByRole("progressbar"));
    });

    test("fetches and renders the session details ", async () => {
        (sessionService.get as jest.Mock).mockResolvedValue({
            id: "s1",
            schedule: 1500,
            speakers: [{ id: 1, name: "Jon" }],
        });

        const { getByText } = render(
            <MemoryRouter>
                <Route>
                    <SessionDetailPage match={urlParams} />
                </Route>
            </MemoryRouter>
        );

        await waitForElement(() => getByText(/s1/i));
        await waitForElement(() => getByText(/1500/i));
        await waitForElement(() => getByText(/Jon/i));
    });

    test("fetches and renders session average rating", async () => {
        (voteService.getAverageRatingBySession as jest.Mock).mockResolvedValue(
            3.14159
        );

        const { getByText } = render(
            <MemoryRouter>
                <Route>
                    <SessionDetailPage match={urlParams} />
                </Route>
            </MemoryRouter>
        );

        await waitForElement(() => getByText(/3.14159/));
    });

    test.todo("renders vote button");
});
