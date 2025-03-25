package com.example.maintenece_motorcycle_management.util;

import java.util.Base64;

public class B64 {
	public static String encode(String text) {
		byte[] data = text.getBytes();
		return Base64.getEncoder().encodeToString(data);
	}
	public static String decode(String encodedText) {
		byte[] data = Base64.getDecoder().decode(encodedText);
		return new String(data);
	}
}