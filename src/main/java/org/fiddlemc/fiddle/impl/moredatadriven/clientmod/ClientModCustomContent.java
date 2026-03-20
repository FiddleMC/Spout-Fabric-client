package org.fiddlemc.fiddle.impl.moredatadriven.clientmod;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockTypes;
import org.fiddlemc.fiddle.impl.moredatadriven.minecraft.type.ItemTypes;
import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * An encodable object containing all custom content sent to the client.
 */
public final class ClientModCustomContent {

    /**
     * The block JSONs.
     */
    private final List<JsonElement> blockJSONs;

    /**
     * The item JSONs.
     */
    private final List<JsonElement> itemJSONs;

    /**
     * The parsed blocks,
     * of null if not initialized yet.
     */
    private @Nullable List<Block> parsedBlocks;

    /**
     * The parsed blocks,
     * of null if not initialized yet.
     */
    private @Nullable List<Item> parsedItems;

    ClientModCustomContent(List<JsonElement> blockJSONs, List<JsonElement> itemJSONs) {
        this.blockJSONs = blockJSONs;
        this.itemJSONs = itemJSONs;
    }

    public List<Block> getParsedBlocks() {
        if (this.parsedBlocks == null) {
            this.parsedBlocks = this.blockJSONs.stream().map(ClientModCustomContent::parseBlock).toList();
        }
        return this.parsedBlocks;
    }

    public List<Item> getParsedItems() {
        if (this.parsedItems == null) {
            this.parsedItems = this.itemJSONs.stream().map(ClientModCustomContent::parseItem).toList();
        }
        return this.parsedItems;
    }

    private static Block parseBlock(JsonElement json) {
        return BlockTypes.CODEC.codec().decode(JsonOps.INSTANCE, json).getOrThrow().getFirst();
    }

    private static Item parseItem(JsonElement json) {
        return ItemTypes.CODEC.codec().decode(JsonOps.INSTANCE, json).getOrThrow().getFirst();
    }

    private static final Codec<JsonElement> JSON_CODEC = Codec.PASSTHROUGH.xmap(
        dynamic -> dynamic.convert(JsonOps.INSTANCE).getValue(),
        json -> new Dynamic<>(JsonOps.INSTANCE, json)
    );

    public static final MapCodec<ClientModCustomContent> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        JSON_CODEC.listOf().fieldOf("blocks").forGetter(content -> content.blockJSONs),
        JSON_CODEC.listOf().fieldOf("items").forGetter(content -> content.itemJSONs)
    ).apply(instance, ClientModCustomContent::new));

    public static final Codec<ClientModCustomContent> CODEC = MAP_CODEC.codec();

}
