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

        await waitForElement(() => getByText(/Session s1/i));
    });

    test("renders a link for each session", () => {
        const { getByText } = render(
            <SessionList sessions={sessions}></SessionList>
        );

        expect(getByText(/s1/).href).toMatch(/\/sessions\/s1$/);
        expect(getByText(/s4/).href).toMatch(/\/sessions\/s4$/);
    });
});
