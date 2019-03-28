package fr.mrcraftcod.scheduler.utils;

import javafx.scene.paint.Color;

/**
 * GymnasiumColor
 * Author : Coleau Victor
 **/

public class GymnasiumColor {
    private final Color backgroundColor;
    private final Color textColor;
    
    public GymnasiumColor(){
        this(Color.rgb(0, 0, 0), Color.rgb(255, 255, 255));
    }
    
    public GymnasiumColor(final Color backgroundColor, final Color textColor){
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getTextColor() {
        return textColor;
    }
}
