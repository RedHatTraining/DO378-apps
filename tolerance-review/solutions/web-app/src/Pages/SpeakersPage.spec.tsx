import React from "react";
import { render, wait } from "@testing-library/react";
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
        const { getByText } = render(<SpeakersPage />);

        await wait(() => expect(getByText(/Speakers/i)).toBeInTheDocument());
    });

    test("fetches list of speakers", async () => {
        const { getByText } = render(<SpeakersPage />);

        await wait(() => expect(getByText(/Jon/i)).toBeInTheDocument());
        await wait(() => expect(getByText(/Lisbeth/i)).toBeInTheDocument());
    });
});
