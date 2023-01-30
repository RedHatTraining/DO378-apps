import React, { useEffect, useState } from "react";
import {
    PageSection, Title, PageSectionVariants, Gallery,
    GalleryItem, Text, TextVariants, Skeleton, Alert, AlertActionCloseButton
} from "@patternfly/react-core";
import * as SpeakersService from "../services/SpeakersService";


// Icons list: https://patternfly-react.surge.sh/icons/
import SpeakerIcon from "@patternfly/react-icons/dist/esm/icons/male-icon";
import { Speaker } from "@app/models/Speaker";
import { SpeakerCard } from "./SpeakerCard";
import { useKeycloak } from "@react-keycloak/web";
import { CreateUserModal } from "./CreateUserModal";

export function Dashboard(): JSX.Element {
    const [speakers, setSpeakers] = useState<Speaker[]>([]);
    const [showAlert, setShowAlert] = useState<boolean>(false);
    const { keycloak, initialized } = useKeycloak();

    useEffect(() => {
        if(keycloak.authenticated) {
            console.log(`Using token: ${keycloak.token}`);
            getSpeakers()
        }
    }, [initialized]);

    async function getSpeakers() {
        const speakers = await SpeakersService.all(keycloak.token || "");

        sortSpeakers(speakers);
        setSpeakers(speakers);
    }

    function sortSpeakers(speakers: Speaker[]) {
        speakers.sort((s1, s2) => s1.nameFirst.localeCompare(s2.nameFirst));
    }

    function renderSpeakersGallery() {
        return <Gallery hasGutter minWidths={{
            md: "300px",
            lg: "300px",
            xl: "400px"
        }}>
            {speakers.map(s => 
            <GalleryItem key={s.id}>
                <SpeakerCard speaker={s} ></SpeakerCard>
            </GalleryItem>)}
        </Gallery>;
    }


    return (
    <React.Fragment>
        <PageSection variant={PageSectionVariants.light}>
        { showAlert && <Alert
            variant="danger"
            title="Could not complete action"
            actionClose={<AlertActionCloseButton onClose={() => setShowAlert(false)} />}>
                <p>See the console for more information</p>
            </Alert>}
            <Title title="Speakers" headingLevel="h1" size="lg">
                <SpeakerIcon size="md" color="#114411" />&nbsp;
                Speakers&nbsp;
            </Title>
            <Text component={TextVariants.small}>
                General data for each speaker.
            </Text>
            <br />
            <br />
            {speakers ? renderSpeakersGallery() : <Skeleton />}
            <br />
            {<CreateUserModal
                setShowAlert={setShowAlert}
                getSpeakers={getSpeakers} />}
        </PageSection>
    </React.Fragment >
    );
}
