package one.devos.nautical.depths_desolation.net;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.net.packet.DesolationSyncPacket;

import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;

import java.util.function.Function;

public class DdNetworking {
	public static final PacketType<DesolationSyncPacket> DESOLATION_SYNC = clientbound("desolation_sync", DesolationSyncPacket::new);

	public static void init() {
	}

	private static <T extends ClientboundPacket> PacketType<T> clientbound(String name, Function<FriendlyByteBuf, T> factory) {
		PacketType<T> type = PacketType.create(DepthsAndDesolation.id(name), factory);
		if (MinecraftQuiltLoader.getEnvironmentType() == EnvType.CLIENT) {
			registerClientReceiver(type);
		}
		return type;
	}

	private static <T extends ServerboundPacket> PacketType<T> serverbound(String name, Function<FriendlyByteBuf, T> factory) {
		PacketType<T> type = PacketType.create(DepthsAndDesolation.id(name), factory);
		ServerPlayNetworking.registerGlobalReceiver(type, ServerboundPacket::handle);
		return type;
	}

	private static <T extends ClientboundPacket> void registerClientReceiver(PacketType<T> type) {
		ClientPlayNetworking.registerGlobalReceiver(type, ClientboundPacket::handle);
	}
}
