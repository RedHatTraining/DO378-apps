import React from "react";
import { MemoryRouter } from "react-router-dom";
import { render, RenderResult, screen } from "@testing-library/react";
import { Navigation } from "./Navigation";

describe("Navigation", () => {

    test("renders link to /", async () => {
        render(<Navigation />, { wrapper: MemoryRouter });
        expect((await screen.findByText("Home")).getAttribute("href")).toMatch(/\/$/);
    });

    test("renders link to /speakers", async () => {
        render(<Navigation />, { wrapper: MemoryRouter });
        expect((await screen.findByText("Speakers")).getAttribute("href")).toMatch(/\/speakers$/);
    });

    test("renders link to /sessions", async () => {
        render(<Navigation />, { wrapper: MemoryRouter });
        expect((await screen.findByText("Sessions")).getAttribute("href")).toMatch(/\/sessions$/);
    });
});
