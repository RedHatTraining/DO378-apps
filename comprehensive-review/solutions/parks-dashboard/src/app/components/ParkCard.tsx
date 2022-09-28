import React, { useState } from "react";
import {
    Card, CardTitle, CardBody,
    DescriptionList, DescriptionListGroup,
    DescriptionListTerm, DescriptionListDescription,
    CardHeader, CardHeaderMain, Alert, CardFooter, Button, Modal, ModalVariant
} from "@patternfly/react-core";
import { Park, ParkStatus } from "@app/models/Park";
import * as ParksService from "@app/services/ParksService";

import garden0 from "@app/images/garden_0.jpg";
import garden1 from "@app/images/garden_1.jpg";
import garden2 from "@app/images/garden_2.jpg";
import garden3 from "@app/images/garden_3.jpg";
import { ParkForm } from "./ParkForm";

const images = {
    pablo: garden0,
    aykut: garden1,
    marek: garden2,
    jaime: garden3,
};


interface ParkCardProps {
    park: Park,
    onParkUpdated: () => Promise<unknown>
}

export function ParkCard(props: ParkCardProps): JSX.Element {
    const { park, onParkUpdated } = props;

    const [isModalOpen, setModalOpen] = useState<boolean>(false);
    const [isParkUpdating, setIsParkUpdating] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>("");

    // function toggleModal() {
    //     setModalOpen(isModalOpen => !isModalOpen);
    // }

    // async function editPark({ name, city, size }: { name: string, city: string, size: number }) {
    //     const newPark = { ...park, name, city, size };

    //     await ParksService.update(newPark);

    //     toggleModal();
    //     onParkUpdated();
    // }

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
            variant="primary"
            isDisabled={isParkUpdating}
            spinnerAriaValueText={isParkUpdating ? "Opening" : undefined}
            isLoading={isParkUpdating}
            onClick={() => openPark(park)}>
            {isParkUpdating ? "Opening...": "Open Park"}
        </Button>
    }
    function renderCloseParkButton(park: Park) {
        return <Button
            variant="warning"
            isDisabled={isParkUpdating}
            spinnerAriaValueText={isParkUpdating ? "Closing" : undefined}
            isLoading={isParkUpdating}
            onClick={() => closePark(park)}>
            {isParkUpdating ? "Closing...": "Close Park"}
        </Button>
    }


    return (<Card isFlat>
        <CardHeader>
            <CardHeaderMain>
                <iframe width="500" height="800" frameBorder="0" src="https://www.openstreetmap.org/export/embed.html?bbox=-1.8620699644088747%2C38.985566521593654%2C-1.8531382083892824%2C38.989027359465815&amp;layer=hot"></iframe>
                <br />
                <small><a href="https://www.openstreetmap.org/#map=18/38.98730/-1.85760&amp;layers=H">View Larger Map</a></small>

            </CardHeaderMain>
        </CardHeader>
        <CardTitle>&nbsp;&nbsp;{park.name}</CardTitle>
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
            {/* <React.Fragment>
                <Button variant="primary" onClick={toggleModal}>
                    Manage
                </Button>
                <Modal
                    variant={ModalVariant.small}
                    title={park.name}
                    isOpen={isModalOpen}
                    onClose={toggleModal}
                >
                    <ParkForm park={park} onSubmit={editPark}></ParkForm>
                </Modal>
            </React.Fragment> */}
        </CardFooter>
    </Card>);
}

function getAlert(park: Park) {
    const alerts = {
        "CLOSING": <Alert variant="warning" title="Park fences are closing" />,
        "CLOSED": <Alert variant="danger" title="Park is closed" />,
    }

    return alerts[park.status] || <Alert variant="info" title="Park is open" />
}

function getParkImage(gardenName: string): string {
    const lowerGardenName = gardenName.toLowerCase();

    const key = Object.keys(images).find(key => lowerGardenName.includes(key));

    return key ? images[key] : "";
}
