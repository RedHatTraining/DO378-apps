import React from "react";
import { render } from "@testing-library/react";
import { SpeakerList } from "./SpeakerList";

describe("SpeakerList", () => {
    test("renders a list of speakers", () => {
        const speakers = [
            { uuid: "s1", nameFirst: "speaker1", nameLast: "lastName1" },
        ] as any;

        const { getByText } = render(
            <SpeakerList speakers={speakers}></SpeakerList>
        );

        expect(getByText(/speaker1/i)).toBeInTheDocument();
        expect(getByText(/lastName1/i)).toBeInTheDocument();
    });

    test("renders a link for each speaker", () => {
        const speakers = [
            { id: 1, uuid: "s1", nameFirst: "speaker1", nameLast: "lastName1" },
            { id: 2, uuid: "s2", nameFirst: "speaker2", nameLast: "lastName2" },
        ] as any;

        const { getByText } = render(
            <SpeakerList speakers={speakers}></SpeakerList>
        );

        expect(getByText(/speaker1/).href).toMatch(/\/speakers\/s1$/);
        expect(getByText(/speaker2/).href).toMatch(/\/speakers\/s2$/);
    });
});
