import { Bullseye, Spinner } from "@patternfly/react-core";
import React from "react";

export function BullseyeSpinner(): JSX.Element {
    return (
        <Bullseye>
            <Spinner />
        </Bullseye>
    );
}