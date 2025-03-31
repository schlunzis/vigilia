package org.schlunzis.vigilia.cli.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorSelector {

    // https://picocli.info/#_more_colors
    private static final String[] ansiColors = new String[]{"fg(196)", "fg(202)", "fg(208)", "fg(214)", "fg(220)", "fg(226)", "fg(190)", "fg(154)", "fg(118)", "fg(82)", "fg(46)"};

    public static String getColor(double min, double max, double index) {
        double r = (index - min) / (max - min) * ansiColors.length;

        int colorIndex = (int) Math.floor(r);
        if (colorIndex >= ansiColors.length) {
            colorIndex = ansiColors.length - 1;
        }
        return ansiColors[colorIndex];
    }

}
