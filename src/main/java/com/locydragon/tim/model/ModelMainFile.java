package com.locydragon.tim.model;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ModelMainFile {
	private FileConfiguration target;
	public ModelMainFile(File location) {
		this.target = YamlConfiguration.loadConfiguration(location);
		ModelCar.modelHash.put(getModelName(), this);
	}

	public List<String> getLoreNeedFormat() {
		return this.target.getStringList("ModelLore");
	}

	public List<String> getMessageFormat() {
		return this.target.getStringList("UsingMessage");
	}

	public String getModelName() {
		return this.target.getString("ModelName", "UNKNOWN");
	}

	public String getDisplayName() { return this.target.getString("ItemName", null); }
}
