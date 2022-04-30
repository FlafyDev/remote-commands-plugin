# remote-commands-plugin

Launch commands in a Minecraft Server remotely without opening another port.  
Works by running a server and making the MC server connect to it.
Communication is done with Socket.IO.

## Using the plugin

Add the plugin to your server and use the following commands to connect/disconnect to the server:

```
/rmconnect [address]
```

```
/rmdisconnect
```

## Hosting the server (Uses Socket.IO in Typescript)

#### the following code was not tested

```typescript
import { Server } from "socket.io";

const remoteCommandsServer = new Server({});
remoteCommandsServer.listen(PORT);

// Waits for the MC server to connect.
await new Promise(async (resolve) => {
  if ((await remoteCommandsServer.allSockets()).size > 0) {
    resolve();
  }
  remoteCommandsServer.on("connection", (socket) => {
    resolve();
  });
});

// Sends Minecraft's `/reload` command to reload Datapacks.
remoteCommandsServer.emit("command", "minecraft:reload");
```
