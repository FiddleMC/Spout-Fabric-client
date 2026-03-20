package org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.Item;
import org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type.ItemCodecs;
import org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type.WithItemCodec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public abstract class ItemCodecMixin implements WithItemCodec {

    @Unique
    @Override
    public MapCodec<? extends Item> codec() {
        return ItemCodecs.ITEM_CODEC;
    }

}
