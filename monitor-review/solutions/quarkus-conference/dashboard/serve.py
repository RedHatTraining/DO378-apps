#!/usr/bin/env python3

import os
import signal
from pathlib import Path
from http.server import SimpleHTTPRequestHandler
import socketserver
from urllib.parse import urlparse

PORT = 8083

directory = (Path(__file__)
    .parent
    .joinpath("build")
    .resolve())

os.chdir(directory)

class RequestsHandler(SimpleHTTPRequestHandler):

    def do_GET(self):
        self.send_to_index_if_spa_route_requested()
        return super().do_GET()

    def send_to_index_if_spa_route_requested(self):
        url = urlparse(self.path)
        requested_file_path = url.path.strip('/')

        if not os.path.isfile(requested_file_path):
            self.path = "index.html"


with socketserver.TCPServer(("", PORT), RequestsHandler) as httpd:
    print(f"Serving '{directory}' at http://localhost:{PORT}/")

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        print(f"Interrupted by user. Shutting down server...")
        httpd.shutdown()
        exit(0)
