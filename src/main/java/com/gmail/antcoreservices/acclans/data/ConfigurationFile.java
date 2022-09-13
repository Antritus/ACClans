package com.gmail.antcoreservices.acclans.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigurationFile {

	private File file;
	private FileConfiguration customFile;
	private String name;
	private String filePath;

	Plugin plugin;
	public ConfigurationFile(Plugin plugin, File filePath, String configName) {
		this.plugin = plugin;
		this.filePath = filePath.getPath();
		this.name = configName;
	}
	public ConfigurationFile(Plugin plugin, File file) {
		this.plugin = plugin;
		this.filePath = file.getPath();
		this.name = file.getName();
	}

	public void load(){
		file = new File(filePath, name);

		if (!file.exists()){
			try{
				file.createNewFile();
			}catch (IOException e){

			}
		}
		customFile = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration get(){
		return customFile;
	}

	public void save(){
		try{
			customFile.save(file);
		}catch (IOException e){
			System.out.println("Couldn't save file " + filePath + File.separator+name);
		}
	}

	public void reload(){
		customFile = YamlConfiguration.loadConfiguration(file);
	}

}