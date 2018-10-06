package com.locydragon.tim.io;

import java.io.File;

public class FileConstantURLs {
	public static final String MODEL_LOCATION = ".//plugins//TomoriItemMythic//model//";

	static {
		new File(MODEL_LOCATION).getParentFile().mkdirs();
	}

	public static void init() {}
}
