import wretch from "wretch";

export enum ServiceName {
    PARKS_BACKEND,
    WEATHER_BACKEND
}

// these environment variables are only evaluated/available at build time
const serviceUrlMap: { [key in ServiceName]: string } = {
    [ServiceName.PARKS_BACKEND]: process.env.BACKEND ?? "http://localhost:8080/",
    [ServiceName.WEATHER_BACKEND]: process.env.WEATHER_BACKEND ?? "http://localhost:8090/",
};
console.log("Backend URL:", serviceUrlMap);


export function getRESTClient(serviceName: ServiceName) {
    return wretch(serviceUrlMap[serviceName]);
}


export function getSSEClient(serviceName: ServiceName) {

    const baseUrl = serviceUrlMap[serviceName];

    return {
        open<T>(path: string, onData: (data: T) => void) {
            const url = new URL(path, baseUrl);
            const eventSource = new EventSource(url.toString());
            eventSource.onmessage = (message) => {
                const data: T = JSON.parse(message.data)
                onData(data)
            }
        }
    };
}

// wretch().catcher(...) can't handle rejections due to no response from server
window.addEventListener("unhandledrejection", (event) => {
    const message = `caught error: ${event.reason}`;
    console.error(message);
});
