import React, { useEffect, useState } from "react";
import { Alert, AlertActionCloseButton } from "@patternfly/react-core";

export interface UserAlertProps {
    message: string;
    type?: "info" | "success" | "warning" | "danger" | "default";
    onClose?: () => void;
}

// eslint-disable-next-line no-unused-vars
export let showAlert = (opts: UserAlertProps) => {
    // this function should get overwritten as soon as UserAlert is included
    console.warn("showAlert called without including <UserAlert /> anywhere");
};

export function UserAlert() {
    const timeoutInMillis = 5000;

    const [isVisible, setIsVisible] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");
    const [type, setType] = useState<
        "info" | "success" | "warning" | "danger" | "default"
    >("default");
    const [onClose, setOnClose] = useState<() => void>(() => {});

    showAlert = (opts: UserAlertProps) => {
        const { message, type, onClose } = opts;
        setIsVisible(true);
        setMessage(message);
        setType(type ?? "default");
        setOnClose(onClose ?? (() => {}));
    };

    useEffect(() => {
        if (isVisible) {
            setTimeout(() => setIsVisible(false), timeoutInMillis);
        }
    }, [isVisible]);

    if (isVisible) {
        return (
            <Alert
                isInline
                title={message}
                variant={type}
                actionClose={
                    <AlertActionCloseButton
                        onClose={() => {
                            setIsVisible(false);
                            onClose?.();
                        }}
                    />
                }
            />
        );
    } else {
        return <></>;
    }
}
