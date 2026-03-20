package org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.BlockItem;
import org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type.ItemCodecs;
import org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type.WithItemCodec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockItem.class)
public abstract class BlockItemCodecMixin implements WithItemCodec {

    @Unique
    @Override
    public MapCodec<? extends BlockItem> codec() {
        return ItemCodecs.BLOCK_ITEM_CODEC;
    }

}
