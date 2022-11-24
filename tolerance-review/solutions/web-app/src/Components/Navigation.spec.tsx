import React from "react";
import { MemoryRouter } from "react-router-dom";
import { render, RenderResult } from "@testing-library/react";
import { Navigation } from "./Navigation";

describe("Navigation", () => {
    let navigation: RenderResult;

    beforeEach(() => {
        navigation = render(<Navigation />, { wrapper: MemoryRouter });
    });

    test("renders link to /", async () => {
        expect(navigation.getByText("Home").href).toMatch(/\/$/);
    });

    test("renders link to /speakers", async () => {
        expect(navigation.getByText("Speakers").href).toMatch(/\/speakers$/);
    });

    test("renders link to /sessions", async () => {
        expect(navigation.getByText("Sessions").href).toMatch(/\/sessions$/);
    });
});
