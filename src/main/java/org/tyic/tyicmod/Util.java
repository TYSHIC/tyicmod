package org.tyic.tyicmod;

import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.util.function.Supplier;

public abstract class Util {
    public static Supplier<Boolean> hasShiftDown = () -> false;
    public static final Text PRESS_SHIFT =
            Text.translatable("tooltip.tyicmod.press_shift").withColor(Colors.CYAN);
}
