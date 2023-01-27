import * as React from 'react';
import '@patternfly/react-core/dist/styles/base.css';
import { AppLayout } from '@app/AppLayout';
import '@app/app.css';
import { Dashboard } from './components/Dashboard';
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from './services/keycloak';

const App: React.FunctionComponent = () => (
  <React.StrictMode>
    <ReactKeycloakProvider 
      authClient={keycloak}
      initOptions={{ onLoad: "login-required" }}>
            <AppLayout>
                  <Dashboard/>
            </AppLayout>
    </ReactKeycloakProvider>
  </React.StrictMode>
);

export default App;
