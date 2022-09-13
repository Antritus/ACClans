package com.gmail.antcoreservices.acclans.data;

import com.gmail.antcoreservices.acclans.utils.StringUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class MessageConfig extends ConfigurationFile{
	private HashMap<String, String> messages = new HashMap<>();
	public MessageConfig(Plugin plugin, File filePath, String configName) {
		super(plugin, filePath, configName);
	}

	public MessageConfig(Plugin plugin, File file) {
		super(plugin, file);
	}

	public String getMessage(String messagePath) {
		return super.get().getString(messagePath);
	}
	public String getFormattedMessage(String messagePath, List<String> placeholders, List<String> values) {
		return getMessage(messagePath, placeholders, values);
	}

	public String getMessage(String messagePath, List<String> placeholders, List<String> values) {
		String string = StringUtils.translate(super.get().getString(messagePath));
		if (placeholders.size() == 0) {
			string = string.replaceAll(placeholders.get(0), values.get(0).toString());
		} else {
			for (int i = 0; i < placeholders.size(); i++){
				string = string.replaceAll(placeholders.get(i), values.get(i).toString());
			}
		}
		return StringUtils.translate(string);
	}

	public String getFormattedMessage(String messagePath) {
		return StringUtils.translate(super.get().getString(messagePath));
	}

	public void save() {
		for (String key : messages.keySet()){
			super.get().set(key, messages.get(key));
		}
		messages.clear();
		reload(false);
		super.save();
	}

	public void reload(boolean reloadConfig) {
		if (reloadConfig) {
			super.reload();
		}
		messages.clear();
		for (String key : super.get().getKeys(true)){
			messages.put(key, super.get().get(key).toString());
		}
	}
	public void reload() {
		reload(true);
	}
}
