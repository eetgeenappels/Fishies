package nl.eetgeenappels.fishies;

import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.jetbrains.annotations.NotNull;

public class FishiesPlugin extends JavaPlugin {
    public FishiesPlugin(@NotNull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();
        this.getCommandRegistry().registerCommand(new TestCommand(
                "testcommand",
                "A test command that shows a title to the player.",
                false
        ));
    }
}
