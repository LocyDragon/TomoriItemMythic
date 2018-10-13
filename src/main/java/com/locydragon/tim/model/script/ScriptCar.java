package com.locydragon.tim.model.script;

import java.util.concurrent.ConcurrentHashMap;

public class ScriptCar {
	/**
	 * key: pattern value: script object
	 */
	public static ConcurrentHashMap<String,TomoriScript> carAsync = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<String,TomoriScript> carSync = new ConcurrentHashMap<>();
}
