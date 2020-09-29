import React from "react";
import { AlertActionCloseButton, Alert } from "@patternfly/react-core";

export type ErrorAlertProps = {
    errorTitle: string;
    errorDescription: string;
    onErrorClosed?: () => void;
};

export function ErrorAlert(props: ErrorAlertProps) {
    return (
        <Alert
            className="popup"
            variant="danger"
            title={props.errorTitle}
            actionClose={
                <AlertActionCloseButton onClose={props.onErrorClosed} />
            }
        >
            {props.errorDescription}
        </Alert>
    );
}
