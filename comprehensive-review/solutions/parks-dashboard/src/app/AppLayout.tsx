import * as React from 'react';
import { useHistory } from 'react-router-dom';
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
    const history = useHistory();
    function handleClick() {
      history.push('/');
    }
    return (
      <img src={logo} className="logo" onClick={handleClick} alt="RH Training Logo" />
    );
  }

  const Header = (
    <PageHeader
      logo={<LogoImg />}
    />
  );

  const pageId = 'primary-app-container';

  return (
    <Page
      mainContainerId={pageId}
      header={Header}>
      {children}
    </Page>
  );
}

export { AppLayout };
