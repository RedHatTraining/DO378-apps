import React, { useState } from "react";
import CaretDownIcon from "@patternfly/react-icons/dist/esm/icons/caret-down-icon";
import { ActionGroup, Avatar, Button, Dropdown, DropdownGroup, DropdownItem, DropdownToggle, Form, FormGroup, Modal, ModalVariant, TextInput } from "@patternfly/react-core";

import avatarImg from "@app/images/avatarImg.svg";
import { ParkForm } from "./ParkForm";

export function AuthMenu(): JSX.Element {
    const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false);
    const [isLoginModalOpen, setIsLoginModalOpen] = useState<boolean>(false);
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const dropdownItems = [
        <DropdownGroup key="auth-dropdown">
            <DropdownItem key="dropdown-login" component="button" isPlainText>
                <React.Fragment>
                    <Button variant="primary" onClick={toggleLoginModal}>
                        Log in
                    </Button>
                </React.Fragment>
            </DropdownItem>
            <DropdownItem key="dropdown-logout" component="button" isPlainText>
                Log out
            </DropdownItem>
        </DropdownGroup>
    ];

    function submitLoginForm() {
        // TODO
    }

    function toggleLoginModal() {
        setIsLoginModalOpen(previous => !previous);
    }

    function onDropdownToggle(newIsOpen) {
        setIsDropdownOpen(newIsOpen);
    }

    function onSelect() {
        setIsDropdownOpen(previousIsOpen => !previousIsOpen);
        document.getElementById("toggle-auth")?.focus();
    }

    return (
        <React.Fragment>
            <Dropdown
                onSelect={onSelect}
                toggle={
                    <DropdownToggle
                        isPlain
                        id="toggle-auth"
                        onToggle={onDropdownToggle}
                        toggleIndicator={CaretDownIcon}
                        icon={<Avatar src={avatarImg} alt="avatar"></Avatar>}
                    >
                        Guest
                    </DropdownToggle>
                }
                isOpen={isDropdownOpen}
                dropdownItems={dropdownItems}
            />
            <Modal
                variant={ModalVariant.small}
                title="Log in"
                isOpen={isLoginModalOpen}
                onClose={toggleLoginModal}
            >
                <Form onSubmit={submitLoginForm}>
                    <FormGroup label="Username" isRequired>
                        <TextInput id="auth-login-username-input" isRequired value={username} onChange={setUsername}></TextInput>
                    </FormGroup>
                    <FormGroup label="Password" isRequired>
                        <TextInput id="auth-login-password-input" type="password" isRequired value={password} onChange={setPassword}></TextInput>
                    </FormGroup>
                    <ActionGroup>
                        <Button variant="primary" type="submit">Save</Button>
                        <Button variant="link">Cancel</Button>
                    </ActionGroup>
                </Form>
            </Modal>
        </React.Fragment>

    );
}
