package com.locydragon.tim.util;

public class InScriptUtils {
	public int getNumber(String x) {
		StringBuilder builder = new StringBuilder();
		for (char each : x.toCharArray()) {
			if (Character.isDigit(each)) {
				builder.append(each);
			}
		}
		if (builder.length() == 0) {
			builder.append("0");
		}
		return Integer.valueOf(builder.toString());
	}

	public boolean odds(int chance) {
		if (chance + 1> (int) (Math.random() * 101)) {
			return true;
		}
		return false;
	}
}
