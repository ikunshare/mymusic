package me.sukimon.utils;

import java.security.SecureRandom;

public class RandomUtils {
	private static final SecureRandom sr = new SecureRandom();

	public static String randomHex(int i2) {
		StringBuilder sb = new StringBuilder(i2);
		while (sb.length() < i2) {
			sb.append(Integer.toHexString(sr.nextInt()));
		}
		return sb.toString().substring(0, i2);
	}
}
