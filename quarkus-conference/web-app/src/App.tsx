import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import "./App.css";
import { Layout } from "./Layout";
import { SpeakersPage } from "./Pages/SpeakersPage";
import { SpeakerDetailPage } from "./Pages/SpeakerDetailPage";
import { SessionsPage } from "./Pages/SessionsPage";
import { SessionDetailPage } from "./Pages/SessionDetailPage";
import { SchedulesPage } from "./Pages/SchedulesPage";
import { ScheduleDetailPage } from "./Pages/ScheduleDetailPage";

export default function App() {
    return (
        <Router basename="/">
            <Switch>
                <Layout>
                    <Route path="/" exact>
                        <div>Welcome to the Quarkus conference app</div>
                    </Route>

                    <Route path="/schedules" exact component={SchedulesPage} />
                    <Route
                        path="/schedules/:scheduleId"
                        component={ScheduleDetailPage}
                    />

                    <Route path="/speakers" exact component={SpeakersPage} />
                    <Route
                        path={"/speakers/:speakerId"}
                        component={SpeakerDetailPage}
                    />

                    <Route path="/sessions" exact component={SessionsPage} />
                    <Route
                        path={"/sessions/:sessionId"}
                        component={SessionDetailPage}
                    />
                </Layout>
            </Switch>
        </Router>
    );
}
