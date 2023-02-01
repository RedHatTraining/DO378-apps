import React, { useState } from "react";
import { Brand, Page, PageHeader, PageSidebar } from "@patternfly/react-core";

import { Navigation } from "./Components/Navigation";
import imgBrand from "./Images/training_white.png";
import { UserAlert } from "./Components/UserAlert";

export function Layout(props: { children?: React.ReactNode }) {
    const [isNavOpen, setIsNavOpen] = useState<boolean>(true);

    const Header = (
        <PageHeader
            logo={
                <Brand
                    src={imgBrand}
                    alt="Red Hat Training Logo"
                    className="logo"
                />
            }
            logoProps={{ href: "/" }}
            showNavToggle
            isNavOpen={isNavOpen}
            onNavToggle={() => setIsNavOpen(!isNavOpen)}
            style={{ borderTop: "2px solid #c00" }}
        />
    );

    const Sidebar = (
        <PageSidebar nav={<Navigation />} isNavOpen={isNavOpen} theme="dark" />
    );

    return (
        <Page header={Header} sidebar={Sidebar} style={{ minHeight: 800 }}>
            <UserAlert />
            {props.children}
        </Page>
    );
}
