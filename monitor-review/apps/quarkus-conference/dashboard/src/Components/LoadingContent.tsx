import React, { ReactChildren } from "react";
import { Text, TextContent, Bullseye, Spinner } from "@patternfly/react-core";
import { ErrorAlert, ErrorAlertProps } from "./ErrorAlert";

/**
 * Use this component when you need to show a loader while loading data
 * and possibly show an error alert if data can't be loaded
 */
export function LoadingContent(props: {
    errorAlert?: ErrorAlertProps;
    title?: string;
    children?: ReactChildren;
}) {
    return (
        <>
            <TextContent>
                <Text component="h2">{props.title ?? "Loading"}</Text>
            </TextContent>
            {props.errorAlert && ErrorAlert(props.errorAlert)}
            <Bullseye>
                <Spinner></Spinner>
            </Bullseye>
            {props.children}
        </>
    );
}
