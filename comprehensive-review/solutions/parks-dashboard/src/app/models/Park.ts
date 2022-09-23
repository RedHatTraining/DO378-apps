export interface Park {
    uuid: string;
    name: string;
    size: number;
    status: string;

    // TODO: delete
    humidity: number;
    humidityTrend: string;
    wind: number;
    windTrend: string;
}
