# Parks Dashboard

This is a React front-end app for the smart parks application of the DO378 comprehensive review.

Because students might not have Node.js installed, we need to provide them with the _built_ web application (the `dist` directory).

Run `npm run build` before pushing changes to generate the `dist` folder.
This folder is included in the repository.

When performing the comprehensive review lab, students can serve the application by running `python serve.py` (no Node.js needed).

## Development scripts
```sh
# Install development/build dependencies
npm install

# Start the fake backend on port 3000 (only if no Quarkus backend is available)
node fake-backend.js

# Start the development server (pointing to the fake backend)
BACKEND="http://localhost:3000/" npm run dev

# Start the development server (pointing to the Quarkus backend)
npm run dev

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
