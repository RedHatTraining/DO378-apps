import React, { useState } from "react";
import {
    Card, CardTitle, CardBody,
    DescriptionList, DescriptionListGroup,
    DescriptionListTerm, DescriptionListDescription,
    Alert, CardFooter, Button
} from "@patternfly/react-core";
import { Park, ParkStatus } from "@app/models/Park";
import * as ParksService from "@app/services/ParksService";
import LockOpenIcon from "@patternfly/react-icons/dist/esm/icons/lock-open-icon";
import LockIcon from "@patternfly/react-icons/dist/esm/icons/lock-icon";
import LeafIcon from "@patternfly/react-icons/dist/esm/icons/leaf-icon";
import TreeIcon from "@patternfly/react-icons/dist/esm/icons/tree-icon";

const icons = [
    <TreeIcon key="park-icon-1" size="sm" color="#bb4400" />,
    <LeafIcon key="park-icon-2" size="sm" color="#aaaa22" />,
    <TreeIcon key="park-icon-3" size="sm" color="#11bb22" />,
    <LeafIcon key="park-icon-4" size="sm" color="#55aa11" />,
];


interface ParkCardProps {
    park: Park,
    onParkUpdated: () => Promise<unknown>
}

export function ParkCard(props: ParkCardProps): JSX.Element {
    const { park, onParkUpdated } = props;
    const [isParkUpdating, setIsParkUpdating] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>("");

    function openPark(park: Park) {
        setIsParkUpdating(true);
        setErrorMessage("");

        ParksService
            .open(park)
                .catch(showParkUpdateError)
                .then(onParkUpdated)
                .finally(() => setIsParkUpdating(false));
    }

    function closePark(park: Park) {
        setIsParkUpdating(true);
        setErrorMessage("");

        ParksService
            .close(park)
                .catch(showParkUpdateError)
                .then(onParkUpdated)
                .finally(() => setIsParkUpdating(false));
    }

    function isOpen(park: Park) {
        return park.status === ParkStatus.OPEN;
    }

    function showParkUpdateError(error: Error) {
        setErrorMessage(error.message);
        setTimeout(() => setErrorMessage(""), 5000);
    }


    function renderOpenParkButton(park: Park) {
        const isInactive = isOpen(park);

        return <Button
            icon={<LockOpenIcon></LockOpenIcon>}
            isDisabled={isInactive|| isParkUpdating}
            spinnerAriaValueText={!isInactive && isParkUpdating ? "Opening" : undefined}
            isLoading={!isInactive && isParkUpdating}
            onClick={() => openPark(park)}>
            {isParkUpdating ? "Opening...": "Open"}
        </Button>
    }
    function renderCloseParkButton(park: Park) {
        const isInactive = !isOpen(park);

        return <Button
            variant="warning"
            icon={<LockIcon></LockIcon>}
            isDisabled={isInactive|| isParkUpdating}
            spinnerAriaValueText={!isInactive && isParkUpdating ? "Closing" : undefined}
            isLoading={!isInactive && isParkUpdating}
            onClick={() => closePark(park)}>
            {isParkUpdating ? "Closing...": "Close"}
        </Button>
    }

    return (<Card isFlat>

        <CardTitle>{getParkIcon(park)}{" "}{park.name}</CardTitle>
        <CardBody>
            <DescriptionList>
                <DescriptionListGroup>
                    <DescriptionListTerm>City</DescriptionListTerm>
                    <DescriptionListDescription>{park.city}</DescriptionListDescription>
                </DescriptionListGroup>
                <DescriptionListGroup>
                    <DescriptionListTerm>Size</DescriptionListTerm>
                    <DescriptionListDescription>{park.size} square meters</DescriptionListDescription>
                </DescriptionListGroup>
                <DescriptionListGroup>
                    <DescriptionListTerm>Conditions</DescriptionListTerm>
                    {getAlert(park)}
                </DescriptionListGroup>
            </DescriptionList>
        </CardBody>
        <CardFooter>
            {renderOpenParkButton(park)}{" "}
            {renderCloseParkButton(park)}
            {errorMessage && <Alert variant="danger" title={errorMessage} />}

        </CardFooter>
    </Card>);
}

function getAlert(park: Park) {
    const alerts = {
        "OPEN": <Alert variant="info" title="Park is open" />,
        "CLOSED": <Alert variant="danger" title="Park is closed" />,
    }

    return alerts[park.status] || <Alert variant="warning" title="Park status is unknown" />
}

function getParkIcon(park: Park) {
    return icons[park.id - 1] || <LeafIcon key="park-icon-4" size="md" color="#ffaa22" />
}
