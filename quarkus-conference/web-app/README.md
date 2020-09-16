# Quarkus Conference Web App

This is the front-end layer for the quarkus-conference application.
It consumes the APIs exposed by the backend microservices.

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `yarn dev`

Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br />
You will also see any lint errors in the console.

NB: For local development, you will likely need to run `yarn json-server` concurrently (see below).

### `yarn start`

Runs the app in production mode.
To start the app in production mode, you first need to create the production build with `yarn build`.

### `yarn test`

Launches the test runner in the interactive watch mode.<br />
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `yarn json-server`

Runs a local mock API using (`json-server`)[https://www.npmjs.com/package/json-server] on port 3004. This mock server can be used to ease local UI development.

The mock data for the server can be found in `db.js`. `json-server` uses the structure of this data to make assumptions about API endpoints and provides those with no extra configuration. Overrides and additional endpoints are configured in `routes.json`. See `json-server`'s documentation for more instructions.

### `yarn lint`

Checks the coding style using `eslint`.

### `yarn build`

Builds the app for production to the `build` folder.<br />
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br />
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `yarn prettier`

Formats code files using the [Prettier opinionated code formatter](https://prettier.io). This will make local changes to files that need it.

Prettier intentionally only provides a few configuration options, which can be defined in `.prettierrc`.

## Configuration

Service URLs are configured using environment variables. These are stored in `.env` (local and default config) and `.env.production` (production/build config).
These variables are solely referenced in `src/Services/RESTClient.ts`. They are referenced to populate a service URL map, which is used throughout the service TS files.

You can also override these by setting those variables in your build environment. Note that _all_ variables must be prefixed with `REACT_APP_` in order for it not to be ignored.

To add a completely new service, you _must_ also add it within `RESTClient.ts`.

More information can be found here: https://create-react-app.dev/docs/adding-custom-environment-variables

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

## Deploying to OpenShift

This application can be directly built using S2I image.

Use the following command:

    oc new-app https://github.com/RedHatTraining/DO378-apps.git --context-dir='quarkus-conference/web-app' --build-env REACT_APP_SESSION_SERVICE="..." --build-env REACT_APP_SPEAKER_SERVICE="..." --build-env REACT_APP_SCHEDULE_SERVICE="..." --build-env REACT_APP_VOTE_SERVICE="..." --name=frontend --strategy=source

