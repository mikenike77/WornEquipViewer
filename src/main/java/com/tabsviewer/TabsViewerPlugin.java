package com.tabsviewer;

import javax.inject.Inject;

import com.google.inject.Provides;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
		name = "Worn Equipment Viewer",
		description = "Add an overlay showing the contents of your equipped tab",
		tags = {"equipment", "items", "overlay", "viewer", "gear"}
)
public class TabsViewerPlugin extends Plugin
{
	@Inject
	private EquipmentViewerOverlay equipmentOverlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private SpriteManager spriteManager;



	@Override
	public void startUp()
	{
		overlayManager.add(equipmentOverlay);
	}

	@Override
	public void shutDown()
	{
		overlayManager.remove(equipmentOverlay);
	}
	@Provides
	TabsViewerConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TabsViewerConfig.class);
	}
}





