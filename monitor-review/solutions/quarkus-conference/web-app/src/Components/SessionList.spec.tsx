import React from "react";
import { render, screen } from "@testing-library/react";
import { SessionList } from "./SessionList";
import mockData from "../../db";

const sessions = mockData().sessions;

describe("SessionList", () => {
    test("renders a list of sessions", async () => {
        render(
            <SessionList sessions={sessions}></SessionList>
        );

        expect(await screen.findByText(/Session se1/i)).toBeTruthy();
    });

    test("renders a link for each session", async () => {
        render(
            <SessionList sessions={sessions}></SessionList>
        );

        expect((await screen.findByText(/se2/)).getAttribute("href")).toMatch(/\/sessions\/se2$/);
        expect((await screen.findByText(/se4/)).getAttribute("href")).toMatch(/\/sessions\/se4$/);
    });
});
