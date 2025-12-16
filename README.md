# Saturn RC

Meteor addon to communicated via Saturn RC (Relay Chat). It allows for Minecraft chat communication privately and over multiple servers.

## Setup

1. **Build**: `./gradlew runClient`
2. **Enable**: In Meteor, enable the RC module.
3. **Configure**: Set host, port, and token in module settings.
4. **Connect**: The module will auto-connect when enabled.

## Configuration
- **Host**: Server address *(default: 127.0.0.1)*
- **Port**: Server port *(default: 3000)*
- **Token**: Authentication token *(must match server)*
- *+ Other aesthetic settings...*

## Server Setup
Run your own server: [rc-server](https://github.com/SaturnHosting/rc-server)

## Usage
Enable the module, ensure your server is running, and send commands via TCP connection.

## Commands
- `.rc` - Toggle the module on/off
