package com.gmail.antcoreservices.acclans.data;

import com.gmail.antcoreservices.acclans.clans.Clan;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
	private ArrayList<Player> players = new ArrayList<>();
	private HashMap<UUID, PlayerFile> playerFiles = new HashMap<>();

	private Plugin plugin;
	public PlayerData(Plugin plugin) {
		this.plugin = plugin;
	}


	public void savePlayers() {
		Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
		for (Player player : players) {
			String json = gson.toJson(playerFiles.get(player.getUniqueId()));
			StringBuilder builder = new StringBuilder();
			for (String s : json.split("§")){
				if (builder.length() == json.length()) {
					continue;
				}
				builder.append(s).append("&");
			}
			json = builder.toString().replaceAll("}&", "}");
			System.out.println(json);
			try {
				File file = new File(plugin.getDataFolder().getPath() + "/players/", player.getUniqueId()+ ".json");
				if (!file.exists()) {
					file.createNewFile();
				}
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				out.write(json);
				out.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void savePlayer(UUID uuid) {
		Gson gson;
		if (playerFiles.get(uuid).isPrettyPrinting()){
			gson = new Gson().newBuilder().setPrettyPrinting().create();
		} else {
			gson = new Gson().newBuilder().create();
		}
		String json = gson.toJson(playerFiles.get(uuid));
		StringBuilder builder = new StringBuilder();
		for (String s : json.split("§")){
			if (builder.length() == json.length()) {
				continue;
			}
			builder.append(s).append("&");
		}
		json = builder.toString().replaceAll("}&", "}");
		System.out.println(json);
		try {
			File file = new File(plugin.getDataFolder().getPath() + "/players/", uuid+ ".json");
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(json);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public boolean deletePlayer(UUID uuid) {
		int i = -999;
		playerFiles.remove(uuid);
		File file = new File(plugin.getDataFolder().getPath() + "/players/", uuid + ".json");
		if (file.exists()){
			file.delete();
		}
		return file.exists();
	}

	public boolean loadPlayers() {
		final File file = new File(plugin.getDataFolder() + "/clans/");
		if (file.listFiles() == null){
			return true;
		}
		int filesFound = file.listFiles().length;
		int filesLoaded = 0;
		if (!file.exists()){
			file.mkdirs();
		}
		if (file.listFiles() == null) {
			System.out.println("Players Found: " + filesFound + "\n" + "Players Loaded: " + filesLoaded);
			return false;
		}
		for (final File fileEntry : file.listFiles()){
			UUID uuid = UUID.fromString(fileEntry.getName().replaceAll(".json", ""));
			if (uuid.toString().equals("")) {
				System.out.println("Unknown file found while loading clans! (" + fileEntry.getName() + ")!");
				continue;
			}
			Gson gson = new Gson();
			try {
				JsonReader reader = new JsonReader(new FileReader(fileEntry));
				playerFiles.put(uuid, gson.fromJson(reader, Clan.class));
				filesLoaded++;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Players Found: " + filesFound + "\n" + "Players Loaded: " + filesLoaded);
		return true;
	}

	public boolean loadPlayer(UUID uuid) {
		File file = new File(plugin.getDataFolder().getPath() + "/players/", uuid + ".json");
		if (file.exists()) {
			Gson gson = new Gson();
			try {
				JsonReader reader = new JsonReader(new FileReader(file));
				playerFiles.put(uuid, gson.fromJson(reader, PlayerFile.class));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return file.exists();
	}

	public HashMap<UUID, PlayerFile> getPlayerFiles() {
		return playerFiles;
	}
}
