package one.devos.nautical.depths_desolation.util;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.jetbrains.annotations.NotNull;

public class TagBlockStateProvider extends BlockStateProvider {
	public static final Codec<TagBlockStateProvider> CODEC = TagCodec.of(Registries.BLOCK)
			.xmap(TagBlockStateProvider::new, provider -> provider.tag);

	public static final BlockStateProviderType<TagBlockStateProvider> TYPE = Registry.register(
			BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE,
			DepthsAndDesolation.id("tag"),
			new BlockStateProviderType<>(CODEC)
	);

	public final TagKey<Block> tag;

	public TagBlockStateProvider(TagKey<Block> tag) {
		this.tag = tag;
	}

	@Override
	@NotNull
	public BlockState getState(RandomSource random, BlockPos pos) {
		return BuiltInRegistries.BLOCK.getTag(this.tag)
				.flatMap(tag -> tag.getRandomElement(random))
				.map(Holder::value)
				.map(Block::defaultBlockState)
				.orElse(Blocks.AIR.defaultBlockState());
	}

	@Override
	@NotNull
	protected BlockStateProviderType<?> type() {
		return TYPE;
	}

	public static void init() {
	}
}
