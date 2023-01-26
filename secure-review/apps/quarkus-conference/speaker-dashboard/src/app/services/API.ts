import wretch from "wretch";

export enum ServiceName {
    SPEAKER_BACKEND
}

// these environment variables are only evaluated/available at build time
const serviceUrlMap: { [key in ServiceName]: string } = {
    [ServiceName.SPEAKER_BACKEND]: process.env.BACKEND ?? "http://localhost:8082/",
};

export function getRESTClient(serviceName: ServiceName) {
    return wretch(serviceUrlMap[serviceName]);
}


// wretch().catcher(...) can't handle rejections due to no response from server
window.addEventListener("unhandledrejection", (event) => {
    const message = `caught error: ${event.reason}`;
    console.error(message);
    console.error(event)
});
