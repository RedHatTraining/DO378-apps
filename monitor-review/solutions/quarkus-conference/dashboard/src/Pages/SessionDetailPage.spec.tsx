import React from "react";
import { findByText, render, screen } from "@testing-library/react";
import { SessionDetailPage } from "./SessionDetailPage";
import * as sessionService from "../Services/SessionService";
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
    });

    test("renders the loader while loading data", async () => {
        (sessionService.get as jest.Mock).mockResolvedValue(null);

        render(
            <MemoryRouter>
                <Route>
                    <SessionDetailPage match={urlParams} />
                </Route>
            </MemoryRouter>
        );

        expect(await screen.findByRole("progressbar")).toBeTruthy();
    });

    test("fetches and renders the session details", async () => {
        (sessionService.get as jest.Mock).mockResolvedValue({
            id: "s1",
            schedule: 1500,
            speakers: [{ id: 1, name: "Jon" }],
        });

        render(
            <MemoryRouter>
                <Route>
                    <SessionDetailPage match={urlParams} />
                </Route>
            </MemoryRouter>
        );

        expect(await screen.findByText(/s1/i)).toBeTruthy();
        expect(await screen.findByText(/1500/i)).toBeTruthy();
        expect(await screen.findByText(/Jon/i)).toBeTruthy();
    });

    test.todo("renders vote button");
});
