package com.thedeathlycow.thirstful.mixin.common.block;

import net.minecraft.block.cauldron.CauldronBehavior;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {
//    @WrapOperation(
//            method = "method_32220",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/item/ItemUsage;exchangeStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"
//            )
//    )
//    private static ItemStack polluteFilledWaterBottle(
//            ItemStack inputStack,
//            PlayerEntity player,
//            ItemStack outputStack,
//            Operation<ItemStack> original,
//            @Local(argsOnly = true) BlockPos pos
//    ) {
//        WaterCollection.pollutePlayerCollectedWater(outputStack, player, pos);
//        return original.call(inputStack, player, outputStack);
//    }
//
//    @ModifyExpressionValue(
//            method = "method_32222",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/component/type/PotionContentsComponent;matches(Lnet/minecraft/registry/entry/RegistryEntry;)Z"
//            )
//    )
//    private static boolean alwaysPlaceWaterCauldron(boolean original) {
//        return true;
//    }
}