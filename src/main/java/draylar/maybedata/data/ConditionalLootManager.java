package draylar.maybedata.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import draylar.maybedata.mixin.LootManagerAccessor;
import net.minecraft.loot.LootGsons;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class ConditionalLootManager extends LootManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = LootGsons.getTableGsonBuilder().create();

    private final ServerResourceManager manager;

    public ConditionalLootManager(ServerResourceManager manager) {
        super(manager.getLootConditionManager());
        this.manager = manager;
    }

    @Override
    public void apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler) {
        System.out.println("conditional loot manager!");

        Map<Identifier, JsonElement> valid = new HashMap<>();

        map.forEach((identifier, jsonElement) -> {
            if (jsonElement instanceof JsonObject) {
                JsonObject obj = (JsonObject) jsonElement;

                Condition condition = GSON.fromJson(obj.get("condition"), Condition.class);
                if (condition.verify()) {
                    valid.put(identifier, obj.get("loot_table"));
                }
            }
        });

        Map<Identifier, LootTable> existing = ((LootManagerAccessor) this.manager.getLootManager()).getTable();
        ImmutableMap<Identifier, LootTable> parsed = parse(map);

        System.out.println(parsed);

        ((LootManagerAccessor) this.manager.getLootManager())
                .setTable(ImmutableMap.<Identifier, LootTable>builder().putAll(existing).putAll(parsed).build());
    }

    public ImmutableMap<Identifier, LootTable> parse(Map<Identifier, JsonElement> map) {
        ImmutableMap.Builder<Identifier, LootTable> builder = ImmutableMap.builder();

        for (Map.Entry<Identifier, JsonElement> entry : map.entrySet()) {
            Identifier identifier = entry.getKey();

            try {
                LootTable lootTable = (LootTable) GSON.fromJson(entry.getValue(), LootTable.class);
                builder.put(identifier, lootTable);
            } catch (IllegalArgumentException | JsonParseException exception) {
                LOGGER.error("Parsing error loading conditional loot table {}", identifier, exception);
            }
        }
        ImmutableMap<Identifier, LootTable> tables = builder.build();
        LOGGER.info("Loaded {} conditional loot tables", tables.size());
        return tables;
    }
}
