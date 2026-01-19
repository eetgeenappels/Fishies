package nl.eetgeenappels.fishies;


import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class FishingRodInteraction extends SimpleInstantInteraction {

    @Override
    protected void firstRun(@NotNull InteractionType interactionType, @NotNull InteractionContext interactionContext, @NotNull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        if (commandBuffer == null) {
            interactionContext.getState().state = InteractionState.Failed;
            return;
        }

        World world = commandBuffer.getExternalData().getWorld();
        Ref<EntityStore> ref = interactionContext.getEntity();


        Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
        ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset("bobber");
        Model model = Model.createRandomScaleModel(modelAsset);

        holder.addComponent(ModelComponent.getComponentType(),new ModelComponent(model));

        TransformComponent playerTransform = ref.getStore().getComponent(ref ,TransformComponent.getComponentType());

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(playerTransform.getPosition());

        holder.addComponent(TransformComponent.getComponentType(), transformComponent);

        UUID uuid = UUID.randomUUID();
        holder.addComponent(UUIDComponent.getComponentType(), new UUIDComponent(uuid));
        holder.putComponent(NetworkId.getComponentType(), new NetworkId(ref.getStore().getExternalData().takeNextNetworkId()));
        holder.ensureComponent(PhysicsValues.getComponentType());

        holder.addComponent(BoundingBox.getComponentType(), new BoundingBox(model.getBoundingBox()));
        holder.addComponent(Velocity.getComponentType(), new Velocity());
        //holder.ensureAndGetComponent(BobberComponent.getComponentType());
        commandBuffer.addEntity(holder, AddReason.SPAWN);

        ItemStack itemstack = interactionContext.getHeldItem();
        if (itemstack == null) {
            interactionContext.getState().state = InteractionState.Failed;
            return;
        }

        
    }


}
