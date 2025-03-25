package com.example.maintenece_motorcycle_management.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JSON {
	private static ObjectMapper mapper = new ObjectMapper();
	private static TypeFactory typeFactory = TypeFactory.defaultInstance();
	
	public static String stringify(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T parseAsObject(String json, Class<T> objectClass)  {
		JavaType type = typeFactory.constructType(objectClass);
		return JSON.parse(json, type);
	}
	
	public static <T> List<T> parseAsList(String json, Class<T> elementClass)  {
		JavaType type = typeFactory.constructCollectionType(List.class, elementClass);
		return JSON.parse(json, type);
	}
	
	public static <T> Map<String, T> parseAsMap(String json, Class<T> valueClass)  {
		JavaType type = typeFactory.constructMapType(Map.class, String.class, valueClass);
		return JSON.parse(json, type);
	}
	
	private static <T> T parse(String json, JavaType type)  {
		try {
			return mapper.readValue(json, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}