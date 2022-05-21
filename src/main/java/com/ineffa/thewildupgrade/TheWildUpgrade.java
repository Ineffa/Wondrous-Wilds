package com.ineffa.thewildupgrade;

import com.ineffa.thewildupgrade.registry.TheWildUpgradeEntities;
import com.ineffa.thewildupgrade.registry.TheWildUpgradeItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

public class TheWildUpgrade implements ModInitializer {
	public static final String MOD_ID = "thewildupgrade";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("The Wild Upgrade initializing!");

		GeckoLib.initialize();
		GeckoLibMod.DISABLE_IN_DEV = true;

		TheWildUpgradeEntities.initialize();
		TheWildUpgradeItems.initialize();
	}
}
