import { GardenStatus } from "@app/models/GardenStatus";
import {
    Card, CardTitle, CardBody,
    DescriptionList, DescriptionListGroup,
    DescriptionListTerm, DescriptionListDescription,
    Avatar, CardHeader
} from "@patternfly/react-core";
import React from "react";

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


interface GardenStatusCardProps {
    gardenStatus: GardenStatus
}

export function GardenStatusCard(props: GardenStatusCardProps): JSX.Element {
    const { gardenStatus } = props;
    return (<Card isFlat>
        <CardHeader>
            <Avatar src={getGardenImage(gardenStatus.gardenName)} alt={gardenStatus.gardenName} />
            <CardTitle>&nbsp;&nbsp;{gardenStatus.gardenName}</CardTitle>
        </CardHeader>
        <CardBody>
            <DescriptionList>
                {gardenStatus.temperature ? <React.Fragment>
                    <DescriptionListGroup>
                        <DescriptionListTerm>Last temperature value</DescriptionListTerm>
                        <DescriptionListDescription>{Number(gardenStatus.temperature).toFixed(2)} ºC</DescriptionListDescription>
                    </DescriptionListGroup>
                    <DescriptionListGroup>
                        <DescriptionListTerm>Temperature trend</DescriptionListTerm>
                        <DescriptionListDescription>{gardenStatus.temperatureTrend}</DescriptionListDescription>
                    </DescriptionListGroup>
                </React.Fragment> : ""}

                {gardenStatus.humidity ? <React.Fragment>
                    <DescriptionListGroup>
                        <DescriptionListTerm>Last humidity value</DescriptionListTerm>
                        <DescriptionListDescription>{Number(gardenStatus.humidity * 100).toFixed(1)} %</DescriptionListDescription>
                    </DescriptionListGroup>
                    <DescriptionListGroup>
                        <DescriptionListTerm>Humidity trend</DescriptionListTerm>
                        <DescriptionListDescription>{gardenStatus.humidityTrend}</DescriptionListDescription>
                    </DescriptionListGroup>
                </React.Fragment> : ""}

                {gardenStatus.wind ? <React.Fragment>
                    <DescriptionListGroup>
                        <DescriptionListTerm>Last wind value</DescriptionListTerm>
                        <DescriptionListDescription>{Number(gardenStatus.wind).toFixed(2)} m/s</DescriptionListDescription>
                    </DescriptionListGroup>
                    <DescriptionListGroup>
                        <DescriptionListTerm>Wind trend</DescriptionListTerm>
                        <DescriptionListDescription>{gardenStatus.windTrend}</DescriptionListDescription>
                    </DescriptionListGroup>
                </React.Fragment> : ""}
            </DescriptionList>
        </CardBody>
    </Card>);
}

function getGardenImage(gardenName: string): string {
    const lowerGardenName = gardenName.toLowerCase();

    const key = Object.keys(images).find(key => lowerGardenName.includes(key));

    return key ? images[key] : "";
}
