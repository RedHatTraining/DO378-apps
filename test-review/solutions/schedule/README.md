
## Run derby database in local host

Go to `src/main/docker`

Build the Derby image ...

`podman build ...` or `make build`

Run the Derby image listening in 1527 port.

`podman run -p 1527:1527 db-derby` or `make run`
