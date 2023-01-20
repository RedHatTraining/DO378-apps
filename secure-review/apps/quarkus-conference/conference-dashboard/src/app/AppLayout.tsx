import React from 'react';
import {
  Page,
  PageHeader
} from '@patternfly/react-core';
import logo from '@app/images/training_white.png';

interface IAppLayout {
  children: React.ReactNode;
}

const AppLayout: React.FunctionComponent<IAppLayout> = ({ children }) => {

  function LogoImg() {
    return (
      <img src={logo} className="logo" alt="RH Training Logo" />
    );
  }

  const header = (
    <PageHeader
      logo={<LogoImg />}
    />
  );


  return (
    <Page
      mainContainerId="primary-app-container"
      header={header}>
      {children}
    </Page>
  );
}

export { AppLayout };
