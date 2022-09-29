import React, { useState } from "react";
import {
    Card, CardTitle, CardBody,
    DescriptionList, DescriptionListGroup,
    DescriptionListTerm, DescriptionListDescription,
    CardHeader, CardHeaderMain, Alert, CardFooter, Button, Avatar
} from "@patternfly/react-core";
import { Park, ParkStatus } from "@app/models/Park";
import * as ParksService from "@app/services/ParksService";
import LockOpenIcon from "@patternfly/react-icons/dist/esm/icons/lock-open-icon";
import LockIcon from "@patternfly/react-icons/dist/esm/icons/lock-icon";
import LeafIcon from "@patternfly/react-icons/dist/esm/icons/leaf-icon";
import TreeIcon from "@patternfly/react-icons/dist/esm/icons/tree-icon";

const icons = [
    <TreeIcon key="park-icon-1" size="md" color="#bb4400" />,
    <LeafIcon key="park-icon-2" size="md" color="#aaaa22" />,
    <TreeIcon key="park-icon-3" size="md" color="#11bb22" />,
    <LeafIcon key="park-icon-4" size="md" color="#55aa11" />,
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

    function showParkUpdateError(error: Error) {
        setErrorMessage(error.message);
        setTimeout(() => setErrorMessage(""), 5000);
    }


    function renderOpenParkButton(park: Park) {
        return <Button
            icon={<LockOpenIcon></LockOpenIcon>}
            isDisabled={isParkUpdating}
            spinnerAriaValueText={isParkUpdating ? "Opening" : undefined}
            isLoading={isParkUpdating}
            onClick={() => openPark(park)}>
            {isParkUpdating ? "Opening...": "Open"}
        </Button>
    }
    function renderCloseParkButton(park: Park) {
        return <Button
            variant="warning"
            icon={<LockIcon></LockIcon>}
            isDisabled={isParkUpdating}
            spinnerAriaValueText={isParkUpdating ? "Closing" : undefined}
            isLoading={isParkUpdating}
            onClick={() => closePark(park)}>
            {isParkUpdating ? "Closing...": "Close"}
        </Button>
    }


    return (<Card isFlat>
        {/* <CardHeader>
            <CardHeaderMain>
                <iframe width="500" height="800" frameBorder="0" src="https://www.openstreetmap.org/export/embed.html?bbox=-1.8620699644088747%2C38.985566521593654%2C-1.8531382083892824%2C38.989027359465815&amp;layer=hot"></iframe>
                <br />
                <small><a href="https://www.openstreetmap.org/#map=18/38.98730/-1.85760&amp;layers=H">View Larger Map</a></small>

            </CardHeaderMain>
        </CardHeader> */}
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

            {park.status == ParkStatus.OPEN ? renderCloseParkButton(park) : renderOpenParkButton(park)}
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
