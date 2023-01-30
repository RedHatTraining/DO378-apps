import { Modal, ModalVariant, Button, Form, FormGroup, TextInput } from "@patternfly/react-core";
import { useKeycloak } from "@react-keycloak/web";

import * as SpeakersService from "../services/SpeakersService";

import React from "react";


export function CreateUserModal({setShowAlert, getSpeakers}): JSX.Element {
    const [isModalOpen, setModalOpen] = React.useState(false);
    const { keycloak } = useKeycloak();
    const [firstName, setFirstName] = React.useState('');
    const [lastName, setLastName] = React.useState('');


    const handleModalToggle = () => {
        setModalOpen(!isModalOpen);
    };

    function createSpeaker() {
        SpeakersService.create(keycloak.token || "", {"nameFirst": firstName, "nameLast": lastName})
            .catch(_ => setShowAlert(true))
            .then(getSpeakers)
            .then(_ => setModalOpen(false));
    }

    return (
        <React.Fragment>
            <Modal
                variant={ModalVariant.small}
                title="Create Speaker"
                description="Enter speaker's first and last name to create the speaker."
                isOpen={isModalOpen}
                onClose={handleModalToggle}
                actions={[
                  <Button key="create" variant="primary" form="modal-with-form-form" onClick={createSpeaker}>
                    Confirm
                  </Button>,
                  <Button key="cancel" variant="link" onClick={handleModalToggle}>
                    Cancel
                  </Button>]}>
                
                  <Form id="modal-with-form-form">
                  <FormGroup
                        label="First Name"
                        isRequired
                        fieldId="modal-with-form-form-first-name">
                        <TextInput
                            isRequired
                            type="text"
                            id="modal-with-form-form-first-name"
                            name="modal-with-form-form-first-name"
                            value={firstName}
                            onChange={setFirstName}/>
                    </FormGroup>
                    <FormGroup
                        label="Last Name"
                        isRequired
                        fieldId="modal-with-form-form-last-name">
                        <TextInput
                            isRequired
                            type="text"
                            id="modal-with-form-form-last-name"
                            name="modal-with-form-form-last-name"
                            value={lastName}
                            onChange={setLastName}/>
                    </FormGroup>
                </Form>
            </Modal>
            <Button variant="primary" onClick={handleModalToggle}>
                Add a speaker
            </Button> 
        </React.Fragment>
    )
}