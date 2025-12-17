# Saturn RC
Meteor addon to communicated via Saturn RC (Relay Chat). It allows for Minecraft chat communication privately and over multiple servers.

<img width="482" height="187" alt="image" src="https://github.com/user-attachments/assets/7305cdf5-053a-4459-a50f-6009b240cc44" />

*funny retard usage above*


> Disclaimer: This isn't meant to and shouldn't be used to make larger-scale public chat rooms as it really isn't sufficient enough to do so. But feel free to contribute to maybe bring it to that stage!

## Usage

1. `./gradlew build` or download the [latest build](https://github.com/SaturnHosting/rc/releases/tag/snapshot).
2. Put it in your `.minecraft/mods` folder along with Meteor 1.21.4.
3. Configure the RC module and enable it.
5. The module will auto-connect to the server when enabled.

## Configuration
- **Host**: Server addres (ip/domain) *(default: 127.0.0.1)*
- **Port**: Server port *(default: 3000)*
- **Token**: Authentication token *(must match server)*
- *and other aesthetic settings...*

## Server Setup
Run your own server: [rc-server](https://github.com/SaturnHosting/rc-server)

## Commands
- `.rc toggle` - Toggle the module on/off
- `.rc online` - list all online people
