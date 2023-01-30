import React from "react";
import { render } from "@testing-library/react";
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
        const view = render(
            <MemoryRouter>
                <Route>
                    <SchedulesPage />
                </Route>
            </MemoryRouter>
        );

        expect(await view.findByText(/Schedule/i)).toBeTruthy();
    });

    test("fetches list of sessions", async () => {
        const view = render(
            <MemoryRouter>
                <Route>
                    <SchedulesPage />
                </Route>
            </MemoryRouter>
        );

        expect(await view.findByText(/1500/i)).toBeTruthy();
        expect(await view.findByText(/1600/i)).toBeTruthy();
    });
});
