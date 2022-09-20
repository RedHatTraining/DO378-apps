# Garden Dashboard

This is a simple React frontend app from the gardens streaming application of AD482 comprehensive review.

Because students might not have Node.js installed, we need to provide them with the _built_ web application.

Run `npm run build` before pushing changes to generate the `dist` folder.
This folder is included in the repository.

Students can therefore serve the application by just running `python scripts/serve-frontend.py`.

## Development scripts
```sh
# Install development/build dependencies
npm install

# Start the fake backend (only if no real backend is available)
node fake-backend.js

# Start the development server
npm run start:dev

# Run a production build (outputs to "dist" dir)
npm run build

# Run the linter
npm run lint

# Run the code formatter
npm run format

# Launch a tool to inspect the bundle size
npm run bundle-profile:analyze

# Start the express server (run a production build first)
npm run start

```

## Configurations
* [TypeScript Config](./tsconfig.json)
* [Webpack Config](./webpack.common.js)
* [Editor Config](./.editorconfig)
