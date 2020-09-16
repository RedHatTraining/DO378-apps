export interface Session {
    id: string;
    schedule: number;
    speakers: { id: number; name: string; uuid: string }[];
}
