package com.tabsviewer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.ImageComponent;
import java.util.Map;
import static java.util.Map.entry;

public class EquipmentViewerOverlay extends OverlayPanel
{
	private static final int SLOT_WIDTH = Constants.ITEM_SPRITE_WIDTH;
	private static final int SLOT_HEIGHT = Constants.ITEM_SPRITE_HEIGHT;
	private static final int GRID_COLS = 3;
	private static final int GRID_ROWS = 5;
	private static final int TOTAL_SLOTS = GRID_COLS * GRID_ROWS;

	private final ItemManager itemManager;
	private final Client client;
	private final TabsViewerConfig config;


	private static final Map<Integer, Integer> SLOT_TO_GRID = Map.ofEntries(
			entry(0, 1),   // Head
			entry(1, 3),   // Cape
			entry(2, 4),   // Amulet
			entry(13, 5),  // Ammo
			entry(3, 6),   // Weapon
			entry(4, 7),   // Body
			entry(5, 8),   // Shield
			entry(7, 10),  // Legs
			entry(9, 12),  // Gloves
			entry(10, 13), // Boots
			entry(12, 14)  // Ring
	);

	@Inject
	private EquipmentViewerOverlay(Client client, ItemManager itemManager, TabsViewerConfig config)
	{
		setPosition(OverlayPosition.BOTTOM_RIGHT);
		panelComponent.setWrap(true);
		panelComponent.setGap(new Point(0, 0));
		panelComponent.setPreferredSize(new Dimension(GRID_COLS * SLOT_WIDTH, GRID_ROWS * SLOT_HEIGHT));
		panelComponent.setOrientation(ComponentOrientation.HORIZONTAL);

		this.itemManager = itemManager;
		this.client = client;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.setBackgroundColor(config.backgroundColor());

		final ItemContainer itemContainer = client.getItemContainer(InventoryID.EQUIPMENT);
		if (itemContainer == null)
		{
			return null;
		}

		panelComponent.getChildren().clear();
		for (int i = 0; i < TOTAL_SLOTS; i++)
		{
			panelComponent.getChildren().add(new ImageComponent(
					new BufferedImage(SLOT_WIDTH, SLOT_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR)
			));
		}

		final Item[] items = itemContainer.getItems();
		for (int containerSlot = 0; containerSlot < items.length; containerSlot++)
		{
			if (!SLOT_TO_GRID.containsKey(containerSlot))
			{
				continue;
			}

			final Item item = items[containerSlot];
			if (item == null || item.getId() == -1 || item.getQuantity() <= 0)
			{
				continue;
			}

			final BufferedImage image = getImage(item);
			if (image != null)
			{
				panelComponent.getChildren().set(SLOT_TO_GRID.get(containerSlot), new ImageComponent(image));
			}
		}

		return panelComponent.render(graphics);
	}

	private BufferedImage getImage(Item item)
	{
		return itemManager.getImage(item.getId(), item.getQuantity(), item.getQuantity() > 1);
	}
}