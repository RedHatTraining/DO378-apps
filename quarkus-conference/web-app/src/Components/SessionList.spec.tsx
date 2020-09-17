import React from "react";
import { render, waitForElement } from "@testing-library/react";
import { SessionList } from "./SessionList";
import mockData from "../../db";

const sessions = mockData().sessions;

describe("SessionList", () => {
    test("renders a list of sessions", async () => {
        const { getByText } = render(
            <SessionList sessions={sessions}></SessionList>
        );

        await waitForElement(() => getByText(/Session se1/i));
    });

    test("renders a link for each session", () => {
        const { getByText } = render(
            <SessionList sessions={sessions}></SessionList>
        );

        expect(getByText(/se2/).href).toMatch(/\/sessions\/se2$/);
        expect(getByText(/se4/).href).toMatch(/\/sessions\/se4$/);
    });
});
