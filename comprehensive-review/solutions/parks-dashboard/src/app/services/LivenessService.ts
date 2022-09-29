import { getRESTClient, ServiceName } from "./API";

interface LivenessResponse {
    status: string,
    checks: {
        status?: string
    }[]
}

const API = getRESTClient(ServiceName.PARKS_BACKEND);

function kafkaStreamsIsReady(data: LivenessResponse) {
    return data.status === "UP";
}

async function isAppReady() {
    const response = await API.url("q/health/live")
        .get().json<LivenessResponse>()
        .catch(error => {
            console.log(error);
            return { checks: [] };
        });

    return kafkaStreamsIsReady(response);
}

export async function waitForLiveness(): Promise<boolean> {
    const ready = await isAppReady();
    if (ready) {
        console.log("Parks service is ready!");
        return ready;
    } else {
        console.log("Parks service not ready. Waiting...");
        return new Promise(function(resolve) {
            setTimeout(function() {
                resolve(waitForLiveness());
            }, 1000);
        });
    }
}