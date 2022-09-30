import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import {
  Alert,
  AlertActionCloseButton,
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

  const [showAuthSuccessAlert, setAuthSuccessAlertVisibility] = useState<boolean>(false);
  const [authFailureAlert, setAuthFailureAlert] = useState<boolean|string>(false);

  function LogoImg() {
    const history = useHistory();
    function handleClick() {
      history.push('/');
    }
    return (
      <img src={logo} className="logo" onClick={handleClick} alt="RH Training Logo" />
    );
  }

  function showAuthSuccess() {
    setAuthSuccessAlertVisibility(true);

    setTimeout(closeAlerts, 5000);
  }

  function showAuthFailure(error) {
    setAuthFailureAlert(error);

    setTimeout(closeAlerts, 5000);
  }

  function closeAlerts() {
    setAuthSuccessAlertVisibility(false);
    setAuthFailureAlert(false);
  }

  const header = (
    <PageHeader
      logo={<LogoImg />}
      headerTools={<PageHeaderTools>
          <AuthMenu
            onAuthenticationSuccess={showAuthSuccess}
            onAuthenticationFailure={showAuthFailure}
          />
      </PageHeaderTools>}
    />
  );




  const authAlerts = <React.Fragment>
      {showAuthSuccessAlert && <Alert
        id="auhtorization-ok-alert"
        className="popup"
        variant="success"
        title="Logged in!"
        actionClose={<AlertActionCloseButton
          onClose={closeAlerts}
        />}
      ></Alert>}
      {authFailureAlert && <Alert
        id="auhtorization-error-alert"
        className="popup"
        variant="warning"
        title={`Error while logging in ${authFailureAlert}`}
        actionClose={<AlertActionCloseButton
          onClose={closeAlerts}
        />}
      ></Alert>}
  </React.Fragment>;


  return (
    <Page
      mainContainerId="primary-app-container"
      header={header}>
      {authAlerts}
      {children}
    </Page>
  );
}

export { AppLayout };
