package maybemoredata.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import maybemoredata.data.ConditionalLootManager;
import maybemoredata.data.ConditionalRecipeManager;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;

@Mixin(ServerResourceManager.class)
public class ServerResourceManagerMixin {
    @Shadow
    @Final
    private ReloadableResourceManager resourceManager;

    @Unique
    private ConditionalRecipeManager conditionalRecipeManager;
    @Unique
    private ConditionalLootManager conditionalLootManager;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initcRecipeManager(CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci) {
        this.conditionalRecipeManager = new ConditionalRecipeManager((ServerResourceManager) (Object) this);
        this.conditionalLootManager = new ConditionalLootManager((ServerResourceManager) (Object) this);
        this.resourceManager.registerListener(this.conditionalRecipeManager);
        this.resourceManager.registerListener(this.conditionalLootManager);
    }
}
