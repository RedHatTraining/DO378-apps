// explicitly disable linting for a mock data config file
/* eslint-disable */
module.exports = () => ({
    sessions: [
        {
            id: "se1",
            schedule: 1500,
            speakers: [
                {
                    id: 1,
                    uuid: "s1",
                    name: "Jon",
                },
            ],
        },
        {
            id: "se2",
            schedule: 1600,
            speakers: [
                {
                    id: 2,
                    uuid: "s2",
                    name: "Lisbeth",
                },
            ],
        },
        {
            id: "se4",
            schedule: 1600,
            speakers: [
                {
                    id: 1,
                    uuid: "s1",
                    name: "Jon",
                },
                {
                    id: 2,
                    uuid: "s2",
                    name: "Lisbeth",
                },
            ],
        },
    ],
    speakers: [
        {
            id: "s1",
            uuid: "s1",
            nameFirst: "Jon",
            nameLast: "Snow",
            organization: "Org1",
            biography:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            picture: "path/to/picture",
            twitterHandle: "@RedHat",
        },
        {
            id: "s2",
            uuid: "s2",
            nameFirst: "Lisbeth",
            nameLast: "Salander",
            organization: "Org12",
            biography:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            picture: "path/to/picture",
            twitterHandle: "@RedHat",
        },
    ],
    ratings: [
        {
            id: 1,
            value: 3,
        },
    ],
    schedules: [
        {
            id: 1500,
            venueId: 2,
            startTime: "12:34",
            date: "2020-09-03T19:31:57Z",
        },
        {
            id: 1600,
            venueId: 2,
            startTime: "12:34",
            date: "2020-09-03T19:31:57Z",
        },
    ],
});
