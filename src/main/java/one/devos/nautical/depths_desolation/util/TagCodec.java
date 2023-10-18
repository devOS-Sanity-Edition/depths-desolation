package one.devos.nautical.depths_desolation.util;

import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class TagCodec {
	public static <T> Codec<TagKey<T>> of(ResourceKey<Registry<T>> registry) {
		return ResourceLocation.CODEC.xmap(
				id -> TagKey.create(registry, id),
				TagKey::location
		);
	}
}
