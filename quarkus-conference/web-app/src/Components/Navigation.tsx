import React from "react";
import { Nav, NavItem, NavList } from "@patternfly/react-core";

import { Link } from "react-router-dom";

export function Navigation() {
    const { pathname } = window.location;
    return (
        <Nav theme="dark">
            <NavList>
                <NavItem id="home" isActive={pathname.endsWith("/")}>
                    <Link to="/">Home</Link>
                </NavItem>
                <NavItem
                    id="schedules"
                    isActive={pathname.endsWith("/schedules")}
                >
                    <Link to="/schedules">Schedules</Link>
                </NavItem>
                <NavItem
                    id="sessions"
                    isActive={pathname.endsWith("/sessions")}
                >
                    <Link to="/sessions">Sessions</Link>
                </NavItem>
                <NavItem
                    id="speakers"
                    isActive={pathname.endsWith("/speakers")}
                >
                    <Link to="/speakers">Speakers</Link>
                </NavItem>
            </NavList>
        </Nav>
    );
}
