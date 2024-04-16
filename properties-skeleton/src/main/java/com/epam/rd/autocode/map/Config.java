package com.epam.rd.autocode.map;

import java.io.*;
import java.util.Properties;


public class Config {
	private Properties config;

	public Config() {
		config = new Properties();
		try {
			config.load(new FileReader("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Properties defaultProperties = defaultProperties();
		config = new Properties(defaultProperties);
		try {
			config.load(new FileReader("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String get(String key) {
		return config.getProperty(key);
	}

	public void remove(String key) {
		config.remove(key);
	}

	public void save() {
		try {
			config.store(new FileWriter("config.properties"), "No comments");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void set(String key, String value) {
		config.setProperty(key, value);
	}

	public void reset() {
		config.clear();
		try {
			config.load(new FileReader("config.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		config = new Properties(defaultProperties());
		try {
			config.load(new FileReader("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Properties defaultProperties() {
		Properties propertiesToReturn = new Properties();

		String defaultProperties = config.getProperty("default.filenames");
		if (defaultProperties != null && !defaultProperties.isEmpty()) {
			String[] defaultPropertiesArray = defaultProperties.split(",");
			for (int i = defaultPropertiesArray.length - 1; i >= 0; i--) {
				try {
					propertiesToReturn.load(new FileReader(defaultPropertiesArray[i].trim() + ".properties"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return propertiesToReturn;
	}
}
