import http.server
import socketserver
import urllib.parse

PORT = 8888

class OAuthHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        query_components = urllib.parse.parse_qs(urllib.parse.urlparse(self.path).query)
        if "code" in query_components:
            authorization_code = query_components["code"][0]
            print(f"Authorization Code: {authorization_code}")
            self.send_response(200)
            self.send_header('Content-type', 'text/html')
            self.end_headers()
            self.wfile.write(b"Authorization successful! You can close this window.")
        else:
            self.send_response(400)
            self.end_headers()
            self.wfile.write(b"Missing authorization code.")

with socketserver.TCPServer(("", PORT), OAuthHandler) as httpd:
    print(f"Serving on port {PORT}")
    httpd.serve_forever()
