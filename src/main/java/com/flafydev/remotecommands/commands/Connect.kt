package com.flafydev.remotecommands.commands

import com.flafydev.remotecommands.RemoteCommands
import io.socket.client.IO
import io.socket.client.Socket
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Connect(private val remoteCommands: RemoteCommands) : CommandExecutor {
	
	// This method is called, when somebody uses our command
	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		if (args.size != 1) {
			return false
		}
		if (remoteCommands.socket == null) {
			remoteCommands.socket = IO.socket(args[0]);
			remoteCommands.uri = args[0];
			remoteCommands.socket?.connect();
			remoteCommands.listen(sender);
		} else {
			sender.sendMessage("Already connected to \"${remoteCommands.uri}\".")
		}
		
		return true
	}
}