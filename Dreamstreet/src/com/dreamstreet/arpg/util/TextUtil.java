package com.dreamstreet.arpg.util;

import javax.xml.soap.Text;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.image.BufferedImage;

/**
 * Created on 5/5/2015.
 */
public class TextUtil {

    private FontRenderContext context;

    public TextUtil() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        context = g.getFontRenderContext();
    }

    public Rectangle2D getBounds(String message, Font font) {

        return font.getStringBounds(message, context);
    }


}
