import React, { useState, useEffect } from "react";
import CaretDownIcon from "@patternfly/react-icons/dist/esm/icons/caret-down-icon";
import {
    ActionGroup, Avatar, Button, Dropdown, DropdownGroup,
    DropdownItem, DropdownToggle, Form, FormGroup, Modal,
    ModalVariant, TextInput
} from "@patternfly/react-core";

import avatarImg from "@app/images/avatarImg.svg";
import * as AuthService from "@app/services/AuthService";


interface AuthMenuProps {
    onAuthenticationSuccess: ({ username }: { username: string }) => unknown,
    onAuthenticationFailure: ({ error }) => unknown
}

export function AuthMenu(props: AuthMenuProps): JSX.Element {
    const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false);
    const [isLoginModalOpen, setIsLoginModalOpen] = useState<boolean>(false);
    const [usernameInput, setUsernameInput] = useState<string>("");
    const [passwordInput, setPasswordInput] = useState<string>("");
    const [authenticatedUsername, setAuthenticatedUsername] = useState<string>("");

    useEffect(() => {
        AuthService.getUsername()
            .then(username => {
                setAuthenticatedUsername(username || "");
            });
    }, []);

    const dropdownItems = [
        <DropdownGroup key="auth-dropdown">
            {!userIsAuthenticated() && <DropdownItem key="dropdown-login" component="button" isPlainText>
                    <a onClick={toggleLoginModal}>Log in</a>
            </DropdownItem>}
            {userIsAuthenticated() && <DropdownItem key="dropdown-logout" component="button" isPlainText>
            <a onClick={logOut}>Log out</a>
            </DropdownItem>}
        </DropdownGroup>
    ];


    function userIsAuthenticated() {
        return !!authenticatedUsername
    }

    function logOut() {
        AuthService.logOut();
        setAuthenticatedUsername("");
    }

    function submitLoginForm(event) {
        AuthService.login(usernameInput, passwordInput)
            .then((authenticated) => {
                if (authenticated) {
                    setAuthenticatedUsername(usernameInput);
                    props.onAuthenticationSuccess({ username: usernameInput })
                    setUsernameInput("");
                    setPasswordInput("")
                } else {
                    throw new Error("Invalid credentials");
                }
            })
            .catch(error => {
                console.log(error);
                console.log(`Error happened when trying to log in: ${error}`);
                props.onAuthenticationFailure(error);
            })
            .finally(() => {
                setIsLoginModalOpen(false);
            });

        event.preventDefault();

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
                        {authenticatedUsername || "Guest"}
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
                        <TextInput id="auth-login-username-input" isRequired value={usernameInput} onChange={setUsernameInput}></TextInput>
                    </FormGroup>
                    <FormGroup label="Password" isRequired>
                        <TextInput id="auth-login-password-input" type="password" isRequired value={passwordInput} onChange={setPasswordInput}></TextInput>
                    </FormGroup>
                    <ActionGroup>
                        <Button variant="primary" type="submit">Save</Button>
                        <Button variant="link">Cancel</Button>
                    </ActionGroup>
                </Form>
            </Modal>
        </React.Fragment >

    );
}
