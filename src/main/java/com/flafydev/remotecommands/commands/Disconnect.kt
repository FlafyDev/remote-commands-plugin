package com.flafydev.remotecommands.commands

import com.flafydev.remotecommands.RemoteCommands
import io.socket.client.IO
import io.socket.client.Socket
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Disconnect(private val remoteCommands: RemoteCommands) : CommandExecutor {
	
	// This method is called, when somebody uses our command
	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		if (args.isNotEmpty()) {
			return false
		}
		if (remoteCommands.socket != null) {
			remoteCommands.disconnect();
			sender.sendMessage("Disconnected.")
		} else {
			sender.sendMessage("Isn't connected.")
		}
		
		return true
	}
}