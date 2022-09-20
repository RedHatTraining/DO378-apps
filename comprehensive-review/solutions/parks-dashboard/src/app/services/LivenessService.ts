import { getRESTClient, ServiceName } from "./API";

interface LivenessResponse {
    checks: {
        status?: string
    }[]
}

const API = getRESTClient(ServiceName.BACKEND);

function kafkaStreamsIsReady(data: LivenessResponse) {
    return data.checks.some(check => check.status && check.status === "UP");
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
        console.log("Kafka Streams app is ready!");
        return ready;
    } else {
        console.log("Kafka Streams app not ready. Waiting...");
        return new Promise(function(resolve) {
            setTimeout(function() {
                resolve(waitForLiveness());
            }, 1000);
        });
    }
}