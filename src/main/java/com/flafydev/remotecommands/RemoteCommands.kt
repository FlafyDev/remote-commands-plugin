package com.flafydev.remotecommands

import com.flafydev.remotecommands.commands.Connect
import com.flafydev.remotecommands.commands.Disconnect
import io.socket.client.Socket
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class RemoteCommands : JavaPlugin() {
	var socket: Socket? = null;
	var uri: String? = null;
	
	
	override fun onEnable() {
		config.addDefault("printCommandsInConsole", true);
		config.options().copyDefaults(true);
		saveConfig();
		
		getCommand("rmconnect")!!.setExecutor(Connect(this));
		getCommand("rmdisconnect")!!.setExecutor(Disconnect(this));
	}
	
	fun listen(sender: CommandSender) {
		socket!!.on("command") {
			object : BukkitRunnable() {
				override fun run() {
					if (config.getBoolean("printCommandsInConsole")) {
						Bukkit.getConsoleSender().sendMessage("Running command \"${it[0]}\"..."); // TODO config: print or not print
					}
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), it[0] as String);
				}
			}.runTask(this);
		}
		
		socket!!.on(Socket.EVENT_CONNECT) {
			sender.sendMessage("RemoteCommands connected");
		}
		
		socket!!.on(Socket.EVENT_DISCONNECT) {
			sender.sendMessage("RemoteCommands disconnected");
			disconnect();
		}
		
		socket!!.on(Socket.EVENT_CONNECT_ERROR) {
			sender.sendMessage("RemoteCommands connection error. ${it.joinToString()}");
			disconnect();
		}
	}
	
	fun disconnect() {
		socket?.disconnect();
		socket = null;
		uri = null;
	}
	
	override fun onDisable() {
		disconnect();
	}
	
}



