import React, { useState } from "react";
import { Park } from "@app/models/Park";
import { ActionGroup, Button, Form, FormGroup, TextInput } from "@patternfly/react-core";

interface ParkFormProps {
    park: Park;
    onSubmit: ({name, city, size}: {
        name: string,
        city: string,
        size: number
    }) => void;
}

export function ParkForm(props: ParkFormProps): JSX.Element {
    const { park, onSubmit } = props;

    const [name, setName] = useState<string>(park.name);
    const [city, setCity] = useState<string>(park.city);
    const [size, setSize] = useState<number>(park.size);


    function submit() {
        onSubmit({name, city, size})
    }

    return <Form onSubmit={submit}>
        <FormGroup label="Park name" isRequired>
            <TextInput id="park-name-input" isRequired value={name} onChange={setName}></TextInput>
        </FormGroup>
        <FormGroup label="City" isRequired>
            <TextInput id="park-city-input" isRequired value={city} onChange={setCity}></TextInput>
        </FormGroup>
        <FormGroup label="Size (in square meters)" isRequired>
            <TextInput id="park-size-input" isRequired type="number" value={size} onChange={(value => setSize(parseInt(value)))}></TextInput>
        </FormGroup>
        <ActionGroup>
            <Button variant="primary" type="submit">Save</Button>
            <Button variant="link">Cancel</Button>
        </ActionGroup>
    </Form>
}