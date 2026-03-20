package org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.Item;

public interface WithItemCodec {

    MapCodec<? extends Item> codec();

}
