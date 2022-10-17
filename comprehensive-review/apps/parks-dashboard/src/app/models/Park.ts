export enum ParkStatus {
    OPEN = "OPEN",
    CLOSED = "CLOSED"
}

export interface Park {
    id: number;
    name: string;
    city: string;
    size: number;
    status: ParkStatus;
}

