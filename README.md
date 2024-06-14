# Spotify Listening History Exporter

This Java application allows you to authenticate with Spotify and export your recent listening history to a JSON file. It utilizes a Python authorization server to facilitate the OAuth 2.0 authentication process with Spotify.

## Prerequisites

- Java 17 or higher
- Python 3.12.3 or higher

## Setup

**1. Clone the repository**

```bash
git clone https://github.com/yourusername/spotify-listening-history.git
cd spotify-listening-history
```

**2. Set up Spotify API credentials**

Open the `SpotifyListeningHistory.java` file and replace the placeholders for `clientId` and `clientSecret` with your Spotify API credentials:

```java
private static final String clientId = "your_client_id_here";
private static final String clientSecret = "your_client_secret_here";
```

## Usage

**1. Start the Python authorization server**

In the `auth_server` directory, start the Python server:

```bash
python oauth_server.py
```

**2. Compile and run the Java application**

From the project root directory, compile and run the Java application:

```bash
javac SpotifyListeningHistory.java
java SpotifyListeningHistory
```

**3. Authorize the application**

The Java application will prompt you to open a URL in your web browser. Follow the URL and log in to your Spotify account to authorize the application.

**4. Copy the authorization code**

After authorizing the application, spotify will redirect you you to a page with an authorization code. This code can be found in the URL of the page, or it will be printed to the python terminal.

**5. Enter the Authorization code**

Return to the terminal where the java application is running and paste the authorization code when prompted.

**6. Retrieve listening history**

The Java application will exchange the authorization code for an access token via the Python server, fetch your recently played tracks from Spotify's API, and save them to a `listeningHistory.json` file in the project directory.

**7. Extract Recent Track IDS**
THe jave application will extract the track ids for each song stored in `listeningHistory.json` and dump them into the new `recentTrackIds.json` file.


## Features

- **Authorization**: Uses OAuth 2.0 to authenticate with Spotify via a Python authorization server.
- **Listening History Retrieval**: Fetches the user's recently played tracks from the Spotify Web API.
- **Export to JSON**: Saves the fetched listening history to a `listeningHistory.json` file in the project directory.

## Dependencies

- Java 17
- Python 3.12.3

## Troubleshooting

- If you encounter authorization issues, ensure your Spotify API credentials (`clientId` and `clientSecret`) are correctly set in `SpotifyListeningHistory.java`.
- Check your internet connection and ensure the Python authorization server (`app.py`) is running and accessible at `http://localhost:8888`.