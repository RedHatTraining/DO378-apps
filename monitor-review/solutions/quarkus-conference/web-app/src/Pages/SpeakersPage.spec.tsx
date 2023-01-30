import React from "react";
import { render } from "@testing-library/react";
import { SpeakersPage } from "./SpeakersPage";
import * as speakerService from "../Services/SpeakerService";
import mockData from "../../db";

jest.mock("../Services/SpeakerService");

describe("SpeakersPage", () => {
    beforeEach(() => {
        (speakerService.listAllSorted as jest.Mock).mockResolvedValue(
            mockData().speakers
        );
    });

    test("renders a title", async () => {
        const view = render(<SpeakersPage />);

        expect(await view.findByText(/Speakers/i)).toBeInTheDocument();
    });

    test("fetches list of speakers", async () => {
        const view = render(<SpeakersPage />);

        expect(await view.findByText(/Jon/i)).toBeInTheDocument();
        expect(await view.findByText(/Lisbeth/i)).toBeInTheDocument();
    });
});
