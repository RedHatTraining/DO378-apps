import React from "react";
import { render, waitForElement } from "@testing-library/react";
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
        const { getByText } = render(<SessionsPage />);

        await waitForElement(() => getByText(/Sessions/i));
    });

    test("fetches list of sessions", async () => {
        const { getByText } = render(<SessionsPage />);

        await waitForElement(() => getByText(/se1/i));
        await waitForElement(() => getByText(/se2/i));
    });
});
