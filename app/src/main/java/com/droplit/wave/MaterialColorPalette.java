package com.droplit.wave;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Random color generator from the new Material Design Color Palette,
 * http://www.google.com/design/spec/style/color.html#color-ui-color-palette
 *
 * @author Mostafa Gazar <eng.mostafa.gazar@gmail.com>
 */
public class MaterialColorPalette {

    private static List<String> mMaterialColors = new ArrayList<String>();

    // TODO :: Seprate the colors.
    static {
        // Red
        // mMaterialColors.add("#e51c23");
        mMaterialColors.add("#f36c60");
        mMaterialColors.add("#e84e40");
        mMaterialColors.add("#e51c23");
        mMaterialColors.add("#dd191d");
        mMaterialColors.add("#d01716");
        mMaterialColors.add("#c41411");
        mMaterialColors.add("#b0120a");
        mMaterialColors.add("#ff7997");
        mMaterialColors.add("#ff5177");
        mMaterialColors.add("#ff2d6f");
        mMaterialColors.add("#e00032");

        // Pink
        // mMaterialColors.add("#e91e63");
        mMaterialColors.add("#f06292");
        mMaterialColors.add("#ec407a");
        mMaterialColors.add("#e91e63");
        mMaterialColors.add("#d81b60");
        mMaterialColors.add("#c2185b");
        mMaterialColors.add("#ad1457");
        mMaterialColors.add("#880e4f");
        mMaterialColors.add("#ff80ab");
        mMaterialColors.add("#ff4081");
        mMaterialColors.add("#f50057");
        mMaterialColors.add("#c51162");

        // Purple
        // mMaterialColors.add("#9c27b0");
        mMaterialColors.add("#ba68c8");
        mMaterialColors.add("#ab47bc");
        mMaterialColors.add("#9c27b0");
        mMaterialColors.add("#8e24aa");
        mMaterialColors.add("#7b1fa2");
        mMaterialColors.add("#6a1b9a");
        mMaterialColors.add("#4a148c");
        mMaterialColors.add("#ea80fc");
        mMaterialColors.add("#e040fb");
        mMaterialColors.add("#d500f9");
        mMaterialColors.add("#aa00ff");

        // Deep Purple
        // mMaterialColors.add("#673ab7");
        mMaterialColors.add("#9575cd");
        mMaterialColors.add("#7e57c2");
        mMaterialColors.add("#673ab7");
        mMaterialColors.add("#5e35b1");
        mMaterialColors.add("#512da8");
        mMaterialColors.add("#4527a0");
        mMaterialColors.add("#311b92");
        mMaterialColors.add("#b388ff");
        mMaterialColors.add("#7c4dff");
        mMaterialColors.add("#651fff");
        mMaterialColors.add("#6200ea");

        // Indigo
        // mMaterialColors.add("#3f51b5");
        mMaterialColors.add("#7986cb");
        mMaterialColors.add("#5c6bc0");
        mMaterialColors.add("#3f51b5");
        mMaterialColors.add("#3949ab");
        mMaterialColors.add("#303f9f");
        mMaterialColors.add("#283593");
        mMaterialColors.add("#1a237e");
        mMaterialColors.add("#8c9eff");
        mMaterialColors.add("#536dfe");
        mMaterialColors.add("#3d5afe");
        mMaterialColors.add("#304ffe");

        // Blue
        // mMaterialColors.add("#5677fc");
        mMaterialColors.add("#91a7ff");
        mMaterialColors.add("#738ffe");
        mMaterialColors.add("#5677fc");
        mMaterialColors.add("#4e6cef");
        mMaterialColors.add("#455ede");
        mMaterialColors.add("#3b50ce");
        mMaterialColors.add("#2a36b1");
        mMaterialColors.add("#a6baff");
        mMaterialColors.add("#6889ff");
        mMaterialColors.add("#4d73ff");
        mMaterialColors.add("#4d69ff");

        // Light Blue
        // mMaterialColors.add("#03a9f4");
        mMaterialColors.add("#4fc3f7");
        mMaterialColors.add("#29b6f6");
        mMaterialColors.add("#03a9f4");
        mMaterialColors.add("#039be5");
        mMaterialColors.add("#0288d1");
        mMaterialColors.add("#0277bd");
        mMaterialColors.add("#01579b");
        mMaterialColors.add("#80d8ff");
        mMaterialColors.add("#40c4ff");
        mMaterialColors.add("#00b0ff");
        mMaterialColors.add("#0091ea");

        // Cyan
        // mMaterialColors.add("#00bcd4");
        mMaterialColors.add("#4dd0e1");
        mMaterialColors.add("#26c6da");
        mMaterialColors.add("#00bcd4");
        mMaterialColors.add("#00acc1");
        mMaterialColors.add("#0097a7");
        mMaterialColors.add("#00838f");
        mMaterialColors.add("#006064");
        mMaterialColors.add("#84ffff");
        mMaterialColors.add("#18ffff");
        mMaterialColors.add("#00e5ff");
        mMaterialColors.add("#00b8d4");

        // Teal
        // mMaterialColors.add("#009688");
        mMaterialColors.add("#4db6ac");
        mMaterialColors.add("#26a69a");
        mMaterialColors.add("#009688");
        mMaterialColors.add("#00897b");
        mMaterialColors.add("#00796b");
        mMaterialColors.add("#00695c");
        mMaterialColors.add("#004d40");
        mMaterialColors.add("#a7ffeb");
        mMaterialColors.add("#64ffda");
        mMaterialColors.add("#1de9b6");
        mMaterialColors.add("#00bfa5");

        // Green
        // mMaterialColors.add("#259b24");
        mMaterialColors.add("#42bd41");
        mMaterialColors.add("#2baf2b");
        mMaterialColors.add("#259b24");
        mMaterialColors.add("#0a8f08");
        mMaterialColors.add("#0a7e07");
        mMaterialColors.add("#056f00");
        mMaterialColors.add("#0d5302");
        mMaterialColors.add("#a2f78d");
        mMaterialColors.add("#5af158");
        mMaterialColors.add("#14e715");
        mMaterialColors.add("#12c700");

        // Light Green
        // mMaterialColors.add("#8bc34a");
        mMaterialColors.add("#aed581");
        mMaterialColors.add("#9ccc65");
        mMaterialColors.add("#8bc34a");
        mMaterialColors.add("#7cb342");
        mMaterialColors.add("#689f38");
        mMaterialColors.add("#558b2f");
        mMaterialColors.add("#33691e");
        mMaterialColors.add("#ccff90");
        mMaterialColors.add("#b2ff59");
        mMaterialColors.add("#76ff03");
        mMaterialColors.add("#64dd17");

        // Lime
        // mMaterialColors.add("#cddc39");
        mMaterialColors.add("#dce775");
        mMaterialColors.add("#d4e157");
        mMaterialColors.add("#cddc39");
        mMaterialColors.add("#c0ca33");
        mMaterialColors.add("#afb42b");
        mMaterialColors.add("#9e9d24");
        mMaterialColors.add("#827717");
        mMaterialColors.add("#f4ff81");
        mMaterialColors.add("#eeff41");
        mMaterialColors.add("#c6ff00");
        mMaterialColors.add("#aeea00");

        // Yellow
        // mMaterialColors.add("#ffeb3b");
        mMaterialColors.add("#fff176");
        mMaterialColors.add("#ffee58");
        mMaterialColors.add("#ffeb3b");
        mMaterialColors.add("#fdd835");
        mMaterialColors.add("#fbc02d");
        mMaterialColors.add("#f9a825");
        mMaterialColors.add("#f57f17");
        mMaterialColors.add("#ffff8d");
        mMaterialColors.add("#ffff00");
        mMaterialColors.add("#ffea00");
        mMaterialColors.add("#ffd600");

        // Amber
        // mMaterialColors.add("#ffc107");
        mMaterialColors.add("#ffd54f");
        mMaterialColors.add("#ffca28");
        mMaterialColors.add("#ffc107");
        mMaterialColors.add("#ffb300");
        mMaterialColors.add("#ffa000");
        mMaterialColors.add("#ff8f00");
        mMaterialColors.add("#ff6f00");
        mMaterialColors.add("#ffe57f");
        mMaterialColors.add("#ffd740");
        mMaterialColors.add("#ffc400");
        mMaterialColors.add("#ffab00");

        // Orange
        // mMaterialColors.add("#ff9800");
        mMaterialColors.add("#ffb74d");
        mMaterialColors.add("#ffa726");
        mMaterialColors.add("#ff9800");
        mMaterialColors.add("#fb8c00");
        mMaterialColors.add("#f57c00");
        mMaterialColors.add("#ef6c00");
        mMaterialColors.add("#e65100");
        mMaterialColors.add("#ffd180");
        mMaterialColors.add("#ffab40");
        mMaterialColors.add("#ff9100");
        mMaterialColors.add("#ff6d00");

        // Deep Orange
        // mMaterialColors.add("#ff5722");
        mMaterialColors.add("#ff8a65");
        mMaterialColors.add("#ff7043");
        mMaterialColors.add("#ff5722");
        mMaterialColors.add("#f4511e");
        mMaterialColors.add("#e64a19");
        mMaterialColors.add("#d84315");
        mMaterialColors.add("#bf360c");
        mMaterialColors.add("#ff9e80");
        mMaterialColors.add("#ff6e40");
        mMaterialColors.add("#ff3d00");
        mMaterialColors.add("#dd2c00");

        // Brown
        // mMaterialColors.add("#795548");
        mMaterialColors.add("#a1887f");
        mMaterialColors.add("#8d6e63");
        mMaterialColors.add("#795548");
        mMaterialColors.add("#6d4c41");
        mMaterialColors.add("#5d4037");
        mMaterialColors.add("#4e342e");
        mMaterialColors.add("#3e2723");

        // Grey
        // mMaterialColors.add("#9e9e9e");
        mMaterialColors.add("#bdbdbd");
        mMaterialColors.add("#9e9e9e");
        mMaterialColors.add("#757575");
        mMaterialColors.add("#616161");
        mMaterialColors.add("#424242");
        mMaterialColors.add("#212121");
        mMaterialColors.add("#000000");

        // Blue Grey
        // mMaterialColors.add("#607d8b");
        mMaterialColors.add("#90a4ae");
        mMaterialColors.add("#78909c");
        mMaterialColors.add("#607d8b");
        mMaterialColors.add("#546e7a");
        mMaterialColors.add("#455a64");
        mMaterialColors.add("#37474f");
        mMaterialColors.add("#263238");

    }

    public static int randomColor() {
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(mMaterialColors.size());

        return Color.parseColor(mMaterialColors.get(randomIndex));
    }

}