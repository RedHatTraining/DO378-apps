import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import "./App.css";
import { Layout } from "./Layout";
import { SpeakersPage } from "./Pages/SpeakersPage";
import { SpeakerDetailPage } from "./Pages/SpeakerDetailPage";
import { SessionsPage } from "./Pages/SessionsPage";
import { SessionDetailPage } from "./Pages/SessionDetailPage";

export default function App() {
    return (
        <Router basename="/">
            <Switch>
                <Layout>
                    <Route path="/" exact>
                        <div style={{display: 'flex', justifyContent:'center', alignItems:'center', height: '100vh'}}><h1>Welcome to the Quarkus conference app</h1></div>
                    </Route>

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
