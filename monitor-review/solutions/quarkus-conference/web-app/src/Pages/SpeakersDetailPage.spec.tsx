import React from "react";
import { render, waitFor, screen, findByText } from "@testing-library/react";
import { SpeakerDetailPage } from "./SpeakerDetailPage";
import * as speakerService from "../Services/SpeakerService";

jest.mock("../Services/SpeakerService");

describe("SpeakerDetailPage", () => {
    const urlParams = {
        params: { speakerId: "s1" },
    };

    test("renders the loader while loading data", async () => {
        (speakerService.findByUuid as jest.Mock).mockResolvedValue(null);

        render(<SpeakerDetailPage match={urlParams} />);

        await expect(await screen.findByRole("progressbar")).toBeInTheDocument();
    });

    test("fetches and renders the speaker details ", async () => {
        (speakerService.findByUuid as jest.Mock).mockResolvedValue({
            uuid: "s1",
            nameFirst: "Jon",
            nameLast: "Snow",
            organization: "Org1",
            biography: "Test bio",
            picture: "test.png",
            twitterHandle: "@twitterAccount",
        });

        const { getByText, getByAltText } = render(
            <SpeakerDetailPage match={urlParams} />
        );

        expect(await screen.findByText(/Jon/i)).toBeInTheDocument();
        expect(await screen.findByText(/Snow/i)).toBeInTheDocument();
        expect(await screen.findByText(/Org1/i)).toBeInTheDocument();
        expect(await screen.findByText(/Test bio/i)).toBeInTheDocument();
        expect(await screen.findByText(/@twitterAccount/i)).toBeInTheDocument();
        expect((await screen.findAllByAltText(/Jon/i))[0].getAttribute("src")).toMatch(/test.png/);
    });
});
