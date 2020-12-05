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
        boolean matched = true;

        matched &= this.modid.isEmpty() || FabricLoader.getInstance().isModLoaded(this.modid);
        matched &= this.item.isEmpty() || Registry.ITEM.getIds().contains(new Identifier(this.item));
        matched &= this.block.isEmpty() || Registry.BLOCK.getIds().contains(new Identifier(this.block));

        return matched;
    }
}
