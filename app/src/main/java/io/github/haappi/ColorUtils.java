package io.github.haappi;

import android.graphics.Color;

public class ColorUtils {
    /* gpt prompt:
    how do i make a method to autmatically constract the text color depending on the background on the position of the text in android studio
     */

    // Calculate the contrast ratio between two colors
    public static double calculateContrastRatio(int foregroundColor, int backgroundColor) {
        // Calculate the luminance of the colors using the relative luminance formula
        double foregroundLuminance = calculateLuminance(foregroundColor);
        double backgroundLuminance = calculateLuminance(backgroundColor);

        // Calculate the contrast ratio
        double contrastRatio =
                (Math.max(foregroundLuminance, backgroundLuminance) + 0.05)
                        / (Math.min(foregroundLuminance, backgroundLuminance) + 0.05);

        return contrastRatio;
    }

    // Calculate the luminance of a color
    private static double calculateLuminance(int color) {
        double red = Color.red(color) / 255.0;
        double green = Color.green(color) / 255.0;
        double blue = Color.blue(color) / 255.0;

        red = (red <= 0.03928) ? red / 12.92 : Math.pow((red + 0.055) / 1.055, 2.4);
        green = (green <= 0.03928) ? green / 12.92 : Math.pow((green + 0.055) / 1.055, 2.4);
        blue = (blue <= 0.03928) ? blue / 12.92 : Math.pow((blue + 0.055) / 1.055, 2.4);

        return 0.2126 * red + 0.7152 * green + 0.0722 * blue;
    }

    // Determine the recommended text color based on the background color
    public static int getRecommendedTextColor(int backgroundColor) {
        // Calculate the contrast ratio between white and the background color
        double contrastWithWhite = calculateContrastRatio(Color.WHITE, backgroundColor);

        // Calculate the contrast ratio between black and the background color
        double contrastWithBlack = calculateContrastRatio(Color.BLACK, backgroundColor);

        // Use white text color if it has higher contrast, otherwise use black
        if (contrastWithWhite > contrastWithBlack) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
}
