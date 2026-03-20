package org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type.mixin;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.Properties.class)
public interface ItemPropertiesAccessor {

    @Accessor("components")
    DataComponentMap.Builder getComponents();

    @Accessor("requiredFeatures")
    FeatureFlagSet getRequiredFeatures();

    @Accessor("requiredFeatures")
    void setRequiredFeatures(FeatureFlagSet requiredFeatures);

    @Accessor("id")
    ResourceKey<Item> getId();

}
