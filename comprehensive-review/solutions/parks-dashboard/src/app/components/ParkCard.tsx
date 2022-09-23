import { Park } from "@app/models/Park";
import {
    Card, CardTitle, CardBody,
    DescriptionList, DescriptionListGroup,
    DescriptionListTerm, DescriptionListDescription,
    Avatar, CardHeader, CardHeaderMain, Brand, CardActions, Checkbox, Alert
} from "@patternfly/react-core";
import React from "react";
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import XYZ from 'ol/source/XYZ';

import garden0 from "@app/images/garden_0.jpg";
import garden1 from "@app/images/garden_1.jpg";
import garden2 from "@app/images/garden_2.jpg";
import garden3 from "@app/images/garden_3.jpg";

const images = {
    pablo: garden0,
    aykut: garden1,
    marek: garden2,
    jaime: garden3,
};


interface ParkCardProps {
    park: Park
}

export function ParkCard(props: ParkCardProps): JSX.Element {
    const { park } = props;

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
                    <DescriptionListTerm>Status</DescriptionListTerm>
                    <DescriptionListDescription>{park.temperatureTrend}</DescriptionListDescription>
                </DescriptionListGroup>
                <Alert variant="warning" title="Park fences are closing." />
            </DescriptionList>
        </CardBody>
    </Card>);
}

function getGardenImage(gardenName: string): string {
    const lowerGardenName = gardenName.toLowerCase();

    const key = Object.keys(images).find(key => lowerGardenName.includes(key));

    return key ? images[key] : "";
}
