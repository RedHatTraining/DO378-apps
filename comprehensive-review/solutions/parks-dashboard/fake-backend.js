const http = require("http");


const PORT = process.env.PORT || 3000;


http.createServer(requestHandler)
    .on("listening", () => { console.log(`Fake backend listening on port ${PORT}`); })
    .on("error", (err) => {
        console.log(err);
        process.exit(1);
    })
    .listen(PORT);


function requestHandler(req, res) {
    const routes = {
        "/sensor/measurements": sseGenerateMeasurements,
        "/garden/statuses": sseGenerateGardenStatuses,
        "/garden/events": sseGenerateEvents,
        "/q/health/live": getHealth,
    };

    console.log("Request:", req.url);
    res.setHeader('Access-Control-Allow-Origin', '*');
    const handler = routes[req.url] || notFound;

    handler(res);
}


function sseGenerateGardenStatuses(res) {
    writeSseHeaders(res);

    return setInterval(
        () => {
            const event = generateGardenStatus();
            res.write(toSSEFormat(event));
        },
        1000
    );
}

function sseGenerateMeasurements(res) {
    writeSseHeaders(res);

    return setInterval(
        () => {
            const measurement = generateMeasurement();
            res.write(toSSEFormat(measurement));
        },
        3000
    );
}

function sseGenerateEvents(res) {
    writeSseHeaders(res);

    return setInterval(
        () => {
            const event = generateEvent();
            res.write(toSSEFormat(event));
        },
        3000
    );
}

function writeSseHeaders(res) {
    res.writeHead(200, {
        "Content-Type": "text/event-stream",
        "Cache-Control": "no-cache",
        "Connection": "keep-alive",
    });
}

function getHealth(res) {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({
        checks: [
            { data: { state: "RUNNING" }}
        ]
    }));
}

function notFound(res) {
    res.writeHead(404);
    res.end("Not found");
}

function generateGardenStatus() {

    const names = [
        "Pablo's garden",
        "Aykut's garden",
        "Enol's garden",
        "Marek's garden"
    ]

    const id = Math.floor(Math.random() * 4);

    return {
        id,
        name: names[id],
        humidity: 56,
        temperature: 24,
        sunlight: 45,
        nutrients: "Good",
        lastUpdate: Date.now()
    }
}


function generateMeasurement() {
    return {
        type: "Temperature",
        value: 45,
        garden: "Pablo's garden",
        sensorId: 111,
        timestamp: Date.now()
    }
}

function generateEvent() {
    return {
        name: "LowTemperatureDetected",
        temperature: 0,
        garden: "Pablo's garden",
        sensorId: 34,
        timestamp: Date.now()
    };
}

function toSSEFormat(data) {
    return `data: ${JSON.stringify(data)}\n\n`;
}