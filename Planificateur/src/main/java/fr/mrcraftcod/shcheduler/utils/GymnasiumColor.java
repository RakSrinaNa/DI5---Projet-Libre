package fr.mrcraftcod.shcheduler.utils;

import javafx.scene.paint.Color;

/**
 * GymnasiumColor
 * Author : Coleau Victor
 **/

public class GymnasiumColor {
    private final Color backgroundColor;
    private final Color textColor;

    public GymnasiumColor(Color backgroundColor, Color textColor) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public GymnasiumColor() {
        this.backgroundColor = Color.rgb(0, 0, 0);
        this.textColor = Color.rgb(255, 255, 255);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getTextColor() {
        return textColor;
    }
}
