package com.tabsviewer;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("tabsviewer")
public interface TabsViewerConfig extends Config
{
    @Alpha
    @ConfigItem(
            keyName = "backgroundColor",
            name = "Background Color",
            description = "The background color of the equipment overlay"
    )
    default Color backgroundColor()
    {
        return new Color(70, 61, 50, 156);
    }
}