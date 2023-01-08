package fr.ludovicans.lanslib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ColoredText {

    @NotNull private final String coloredText;

    /**
     * ColoredText is simple Builder. Give it a string and call {@link ColoredText#buildComponent()}.
     *
     * @param coloredText content.
     */
    public ColoredText(@NotNull final String coloredText) {
        this.coloredText = coloredText;
    }

    /**
     * Build the Component for this {@link ColoredText#coloredText}.
     *
     * @return Component of {@link ColoredText#coloredText} content.
     */
    @NotNull
    public Component buildComponent() {
        return LegacyComponentSerializer.legacy('&').deserialize(this.coloredText);
    }

}
