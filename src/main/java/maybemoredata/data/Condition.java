package maybemoredata.data;

import com.google.common.base.Strings;

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

        matched &= Strings.isNullOrEmpty(this.modid) || FabricLoader.getInstance().isModLoaded(this.modid);
        matched &= Strings.isNullOrEmpty(this.item) || Registry.ITEM.getIds().contains(new Identifier(this.item));
        matched &= Strings.isNullOrEmpty(this.block) || Registry.BLOCK.getIds().contains(new Identifier(this.block));

        return matched;
    }
}
