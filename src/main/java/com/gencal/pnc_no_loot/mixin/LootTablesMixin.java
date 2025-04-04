package com.gencal.pnc_no_loot.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootTables.class)
public abstract class LootTablesMixin {
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void onGetLootTable(ResourceLocation location, CallbackInfoReturnable<LootTable> cir) {
        // Check if the requested loot table matches one of the targeted ones
        if (location.equals(new ResourceLocation("pneumaticcraft", "custom/common_dungeon_loot")) ||
                location.equals(new ResourceLocation("pneumaticcraft", "custom/uncommon_dungeon_loot")) ||
                location.equals(new ResourceLocation("pneumaticcraft", "custom/rare_dungeon_loot"))) {
            // Return an empty loot table
            LootTable emptyLootTable = new LootTable.Builder()
                    .setParamSet(LootContextParamSets.CHEST)
                    .build();
            cir.setReturnValue(emptyLootTable);
            System.out.println("[PNC_No_Loot] Intercepted and replaced loot table: " + location);
        }
    }
}
