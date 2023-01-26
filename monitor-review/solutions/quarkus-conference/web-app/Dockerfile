FROM node:14 as builder
# Install dependencies
WORKDIR /app
COPY package.json yarn.lock /app/
RUN yarn install --frozen-lockfile
# Copy source
COPY . /app
# Create a production build
RUN npm run build


FROM registry.access.redhat.com/ubi8/nodejs-12 as server
COPY --from=builder /app/build ./build
COPY --from=builder /app/package.json .
# Install a basic web server
RUN npm i serve@11
# Serve the app
EXPOSE 8080
CMD [ "npm", "start" ]