import React from "react";
import { render, screen } from "@testing-library/react";
import { SpeakerList } from "./SpeakerList";

describe("SpeakerList", () => {

    test("renders a list of speakers", async () => {
        const speakers = [
            { uuid: "s1", nameFirst: "speaker1", nameLast: "lastName1" },
        ] as any;

        render(
            <SpeakerList speakers={speakers}></SpeakerList>
        );

        expect(await screen.findByText(/speaker1/i)).toBeInTheDocument();
        expect(await screen.findByText(/lastName1/i)).toBeInTheDocument();
    });

    test("renders a link for each speaker", async () => {
        const speakers = [
            { id: 1, uuid: "s1", nameFirst: "speaker1", nameLast: "lastName1" },
            { id: 2, uuid: "s2", nameFirst: "speaker2", nameLast: "lastName2" },
        ] as any;

        render(<SpeakerList speakers={speakers}></SpeakerList>);

        expect((await screen.findByText(/speaker1/)).getAttribute("href")).toMatch(/\/speakers\/s1$/);
        expect((await screen.findByText(/speaker2/)).getAttribute("href")).toMatch(/\/speakers\/s2$/);
    });
});
