# Speaker Dashboard

This is a React front-end application for the `speaker` microservice of the Quarkus Conference application used in the DO378 secure review lab.

This application integrates with a Keycloak server.
See the [Keycloak Config](./src/app/services/keycloak.js) for more information about SSO configuration.

## Development scripts
```sh
# Install development/build dependencies
npm install

# Run the linter
npm run lint

# Run the code formatter
npm run format

# Launch a tool to inspect the bundle size
npm run bundle-profile:analyze

# Run a production build (outputs to "dist" dir)
npm run build

# Start the express server (uses the dist folder so you must run "npm run build" first)
npm run start
```

## Configurations
* [TypeScript Config](./tsconfig.json)
* [Webpack Config](./webpack.common.js)
* [Editor Config](./.editorconfig)
