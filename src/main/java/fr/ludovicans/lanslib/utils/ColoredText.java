package fr.ludovicans.lanslib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ColoredText {

    private final String coloredText;

    public ColoredText(String coloredText) {
        this.coloredText = coloredText;
    }

    public Component buildComponent() {
        return LegacyComponentSerializer.legacy('&').deserialize(this.coloredText);
    }

}
