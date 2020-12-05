package maybemoredata.data;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Condition {
    private String modid = "";
    private String item = "";
    private String block = "";

    public Condition(String modid, String item, String block) {
        this.modid = modid;
        this.item = item;
        this.block = block;
    }

    public boolean verify() {
        if (!modid.isEmpty()) {
            return FabricLoader.getInstance().isModLoaded(modid);
        }

        if (!item.isEmpty()) {
            return Registry.ITEM.getIds().contains(new Identifier(item));
        }

        if (!block.isEmpty()) {
            return Registry.BLOCK.getIds().contains(new Identifier(item));
        }

        return false;
    }
}
