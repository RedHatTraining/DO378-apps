# Conference Dashboard

This is a React front-end app for the conference app of the DO378 `monitor-review` lab.

Because Node.js is not installed in the workstation, we need to provide them with the _built_ web application (the `build` directory).

Run `npm run build` before pushing changes to generate the `build` folder.
This folder is included in the repository so that students can serve the built app.
To serve the app, use the `serve.py` script.

When performing the comprehensive review lab, students can serve the application by running `python serve.py` (no Node.js needed).

## Development scripts
```sh
# Install development/build dependencies
npm i

# Start the development server (pointing to the fake backend)
npm start

# Run a production build (outputs to "build" dir)
npm run build
```
