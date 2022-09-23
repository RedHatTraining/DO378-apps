import * as React from 'react';
import { useHistory } from 'react-router-dom';
import {
  Page,
  PageHeader,
  PageHeaderTools
} from '@patternfly/react-core';
import logo from '@app/images/training_white.png';
import { AuthMenu } from './components/AuthMenu';

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

  const headerTools = <AuthMenu></AuthMenu>;


  const header = (
    <PageHeader
      logo={<LogoImg />}
      headerTools={<PageHeaderTools>{headerTools}</PageHeaderTools>}
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
