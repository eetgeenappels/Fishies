package nl.eetgeenappels.fishies;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends AbstractPlayerCommand {
    public TestCommand(@NotNull String name, @NotNull String description, boolean requiresConfirmation) {
        super(name, description, requiresConfirmation);
    }

    @Override
    protected void execute(@NotNull CommandContext commandContext, @NotNull Store<EntityStore> store, @NotNull Ref<EntityStore> ref, @NotNull PlayerRef playerRef, @NotNull World world) {
        EventTitleUtil.showEventTitleToPlayer(
                playerRef,
                Message.raw("skibiddddi"),
                Message.raw("bop yes"),
                true
        );
    }
}
