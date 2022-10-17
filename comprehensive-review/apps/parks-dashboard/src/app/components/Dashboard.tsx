import React, { useEffect, useState } from "react";
import {
    PageSection, Title, PageSectionVariants, Card,
    CardBody, Gallery,
    GalleryItem, Text, TextVariants, Skeleton, Button, CardFooter, Badge
} from "@patternfly/react-core";
import * as ParksService from "@app/services/ParksService";
import * as WeatherService from "@app/services/WeatherService";
import { waitForLiveness } from "../services/LivenessService";
import { Park } from "@app/models/Park";
import { ParkCard } from "./ParkCard";
import { TableComposable, Tbody, Td, Th, Thead, Tr } from "@patternfly/react-table";


// Icons list: https://patternfly-react.surge.sh/icons/
import TreeIcon from "@patternfly/react-icons/dist/esm/icons/tree-icon";
import WindIcon from "@patternfly/react-icons/dist/esm/icons/wind-icon";
import CloudShowersHeavyIcon from "@patternfly/react-icons/dist/esm/icons/cloud-showers-heavy-icon";
import ProcessAutomationIcon from "@patternfly/react-icons/dist/esm/icons/process-automation-icon";
import { WeatherWarning } from "@app/models/WeatherWarning";



export function Dashboard(): JSX.Element {
    const [ready, setReady] = useState<boolean>(false);
    const [parks, setParks] = useState<Park[]>([]);
    const [weatherWarnings, setWeatherWarnings] = useState<WeatherWarning[]>([]);

    useEffect(() => {
        waitForLiveness()
            .then(() => {
                getParks();
                setInterval(getParks, 2000);
                getWeatherWarnings();
                setReady(true);
            });

    }, []);

    async function getParks() {
        const parks = await ParksService.all();

        sortParks(parks);
        setParks(parks);
    }

    async function getWeatherWarnings() {
        const warnings = await WeatherService.all();

        setWeatherWarnings(warnings);
    }

    function sortParks(parks: Park[]) {
        parks.sort((p1, p2) => p1.name.localeCompare(p2.name));
    }

    async function simulateWeatherAlerts() {
        await WeatherService.simulateWarnings();
        getWeatherWarnings();
    }

    async function runWeatherChecks() {
        await Promise.all(parks.map(ParksService.checkWeather));

        getParks();
    }


    function renderWeatherWarningsTable() {
        return <TableComposable
            aria-label="Measurements table"
            variant="compact"
            borders={true}>
            <Thead>
                <Tr>
                    <Th key={0}>City</Th>
                    <Th key={1}>Type</Th>
                    <Th key={2}>Level</Th>
                </Tr>
            </Thead>
            <Tbody>
                {weatherWarnings.map(renderWeatherWarningRow)}
            </Tbody>
        </TableComposable>;
    }

    function renderParksGallery() {
        return <Gallery hasGutter minWidths={{
            md: "300px",
            lg: "300px",
            xl: "400px"
        }}>
            {parks.map(p => <GalleryItem key={p.id}>
                <ParkCard park={p} onParkUpdated={getParks}></ParkCard>
            </GalleryItem>)}
        </Gallery>;
    }

    function renderWeatherWarningRow(w: WeatherWarning) {

        const badges = {
            yellow: <Badge style={{ "backgroundColor": "#bfaf00" }}>{w.level}</Badge>,
            orange: <Badge style={{ "backgroundColor": "orange" }}>{w.level}</Badge>,
            red: <Badge style={{ "backgroundColor": "red" }}>{w.level}</Badge>,
        }

        const tableIndex = `${w.city}_${w.level}_${w.type}`;
        return (<Tr key={tableIndex}>
            <Td key={`${tableIndex}_city`} dataLabel="City">
                {w.city}
            </Td>
            <Td key={`${tableIndex}_type`} dataLabel="Type">
                {w.type}
            </Td>
            <Td key={`${tableIndex}_level`} dataLabel="Level">
                {badges[w.level.toLowerCase()]}
            </Td>
        </Tr>);
    }


    return (<React.Fragment>
        <PageSection variant={PageSectionVariants.light}>
            <Title title="Parks" headingLevel="h1" size="lg">
                <TreeIcon size="md" color="#114411" />&nbsp;
                Parks&nbsp;
            </Title>
            <Text component={TextVariants.small}>
                General data for each park.
            </Text>
            <br />
            <br />
            {ready ? renderParksGallery() : <Skeleton />}
        </PageSection>

        <PageSection >
            <Title headingLevel="h1" size="lg">
                <WindIcon size="md" color="#2222aa" />&nbsp;
                Weather Warnings&nbsp;
            </Title>

            <Text component={TextVariants.small}>
                Weather warnings issued by the weather service.
            </Text>
            <br />
            <br />
            <Card>
                <CardBody>
                    {ready ? renderWeatherWarningsTable() : <Skeleton />}
                </CardBody>
                <CardFooter>
                    <Button
                        icon={<CloudShowersHeavyIcon></CloudShowersHeavyIcon>}
                        onClick={simulateWeatherAlerts}>
                        Simulate weather warnings
                    </Button>{" "}
                    <Button
                        icon={<ProcessAutomationIcon></ProcessAutomationIcon>}
                        variant="secondary" onClick={runWeatherChecks}>
                        Run park weather checks
                    </Button>
                </CardFooter>
            </Card>

        </PageSection>
    </React.Fragment >);
}
