package one.devos.nautical.depths_desolation.data;

import java.util.function.IntFunction;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.DdBlocks;
import one.devos.nautical.depths_desolation.content.blocks.LightBulbBlock;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.resources.ResourceLocation;

public class DdBlockstateGen extends FabricModelProvider {
	public DdBlockstateGen(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators gen) {
		IntFunction<ResourceLocation> modelFunc = age -> DepthsAndDesolation.id("block/light_bulb_" + age);
		gen.skipAutoItemBlock(DdBlocks.LIGHT_BULB);
		gen.blockStateOutput.accept(
				MultiVariantGenerator.multiVariant(DdBlocks.LIGHT_BULB)
						.with(BlockModelGenerators.createFacingDispatch())
						.with(PropertyDispatch.property(LightBulbBlock.AGE).generate(
								age -> Variant.variant().with(VariantProperties.MODEL, modelFunc.apply(age))
						))
		);
	}

	@Override
	public void generateItemModels(ItemModelGenerators gen) {
	}
}
