package org.tyic.tyicmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.Screen;

public class TyicModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        Util.hasShiftDown = Screen::hasShiftDown;
    }
}