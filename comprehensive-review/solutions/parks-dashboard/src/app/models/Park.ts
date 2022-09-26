enum ParkStatus {
    OPEN = "OPEN",
    CLOSED = "OPEN",
    CLOSING = "CLOSING"
}

export interface Park {
    uuid: string;
    name: string;
    city: string;
    size: number;
    status: ParkStatus;
}

