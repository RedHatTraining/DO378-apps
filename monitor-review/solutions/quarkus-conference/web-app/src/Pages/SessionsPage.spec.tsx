import React from "react";
import { render, screen } from "@testing-library/react";
import { SessionsPage } from "./SessionsPage";
import * as sessionService from "../Services/SessionService";
import mockData from "../../db";

jest.mock("../Services/SessionService");

describe("SessionsPage", () => {
    beforeEach(() => {
        (sessionService.getAll as jest.Mock).mockResolvedValue(
            mockData().sessions
        );
    });

    test("renders a title", async () => {
        render(<SessionsPage />);

        expect(await screen.findByText(/Sessions/i)).toBeTruthy();
    });

    test("fetches list of sessions", async () => {
        render(<SessionsPage />);

        expect(await screen.findByText(/se1/i)).toBeTruthy();
        expect(await screen.findByText(/se2/i)).toBeTruthy();
    });
});
