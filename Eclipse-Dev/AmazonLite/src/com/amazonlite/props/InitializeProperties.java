package com.amazonlite.props;

import com.amazonlite.model.ItemType;
import com.amazonlite.model.Model;

public class InitializeProperties {
	
	private static Model model;
	
	/**
	 * Initialization method that creates default properties files if they are missing
	 */
	public static void init() {
		model = new Model();
		for (ItemType itemType : ItemType.values()) {
			InitializeProperties.initializeDefaultProperites(itemType);
		}
	}

	/**
	 * Method to initialize default property files if they are missing
	 * @param itemType ItemType to be used when initializing properties files
	 */
	private static void initializeDefaultProperites(ItemType itemType) {
		if (!model.checkPropertiesFileExists(itemType)) { 
			model.createDummyProperties(itemType);
		}
	}
	
	
}
