package draylar.maybedata.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.Gson;

import draylar.maybedata.data.ConditionalLootManager;
import draylar.maybedata.data.ConditionalRecipeManager;
import net.minecraft.resource.JsonDataLoader;

@Mixin(JsonDataLoader.class)
public class JsonDataLoaderMixin {
    @Shadow
    @Final
    @Mutable
    private String dataType;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyS(Gson gson, String dataType, CallbackInfo ci) {
        if ((Object) this instanceof ConditionalRecipeManager) {
            this.dataType = "mayberecipes";
        }
        if ((Object) this instanceof ConditionalLootManager) {
            this.dataType = "maybeloot_tables";
        }
    }
}
