import React from "react";
import { render, wait } from "@testing-library/react";
import { SpeakerDetailPage } from "./SpeakerDetailPage";
import * as speakerService from "../Services/SpeakerService";

jest.mock("../Services/SpeakerService");

describe("SpeakerDetailPage", () => {
    const urlParams = {
        params: { speakerId: "s1" },
    };

    test("renders the loader while loading data", async () => {
        (speakerService.findByUuid as jest.Mock).mockResolvedValue(null);

        const { getByRole } = render(<SpeakerDetailPage match={urlParams} />);

        await wait(() => expect(getByRole("progressbar")).toBeInTheDocument());
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

        await wait(() => expect(getByText(/Jon/i)).toBeInTheDocument());
        await wait(() => expect(getByText(/Snow/i)).toBeInTheDocument());
        await wait(() => expect(getByText(/Org1/i)).toBeInTheDocument());
        await wait(() => expect(getByText(/Test bio/i)).toBeInTheDocument());
        await wait(() =>
            expect(getByText(/@twitterAccount/i)).toBeInTheDocument()
        );
        await wait(() => expect(getByAltText(/Jon/i).src).toMatch(/test.png/));
    });
});
