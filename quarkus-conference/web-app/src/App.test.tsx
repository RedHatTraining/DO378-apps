import React from "react";
import { render } from "@testing-library/react";

import App from "./App";

describe("App", () => {
    test("renders a title", () => {
        const { getByText } = render(<App />);
        const element = getByText(/Quarkus conference/i);
        expect(element).toBeInTheDocument();
    });
});
