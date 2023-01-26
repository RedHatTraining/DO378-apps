import React from "react";
import { render, waitForElement } from "@testing-library/react";
import * as scheduleService from "../Services/ScheduleService";
import mockData from "../../db";
import { SchedulesPage } from "./SchedulesPage";
import { MemoryRouter, Route } from "react-router-dom";

jest.mock("../Services/ScheduleService");

describe("SessionsPage", () => {
    beforeEach(() => {
        (scheduleService.getAll as jest.Mock).mockResolvedValue(
            mockData().schedules
        );
    });

    test("renders a title", async () => {
        const { getByText } = render(
            <MemoryRouter>
                <Route>
                    <SchedulesPage />
                </Route>
            </MemoryRouter>
        );

        await waitForElement(() => getByText(/Schedule/i));
    });

    test("fetches list of sessions", async () => {
        const { getByText } = render(
            <MemoryRouter>
                <Route>
                    <SchedulesPage />
                </Route>
            </MemoryRouter>
        );

        await waitForElement(() => getByText(/1500/i));
        await waitForElement(() => getByText(/1600/i));
    });
});
