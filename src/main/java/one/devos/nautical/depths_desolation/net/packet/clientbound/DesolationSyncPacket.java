package one.devos.nautical.depths_desolation.net.packet.clientbound;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.duck.LevelExt;
import one.devos.nautical.depths_desolation.net.ClientboundPacket;
import one.devos.nautical.depths_desolation.net.DdNetworking;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.entity.event.api.EntityWorldChangeEvents;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;

public class DesolationSyncPacket implements ClientboundPacket {
	private final boolean isDesolate;

	public DesolationSyncPacket(ServerLevel level) {
		this.isDesolate = DepthsAndDesolation.isDesolate(level);
	}

	public DesolationSyncPacket(FriendlyByteBuf buf) {
		this.isDesolate = buf.readBoolean();
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeBoolean(isDesolate);
	}

	@Override
	@ClientOnly
	public void handle(LocalPlayer player, PacketSender responder) {
		((LevelExt) player.clientLevel).dd$setDesolate(this.isDesolate);
	}

	@Override
	public PacketType<?> getType() {
		return DdNetworking.DESOLATION_SYNC;
	}

	public static void registerEvents() {
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayNetworking.send(handler.player, new DesolationSyncPacket(handler.player.serverLevel()));
		});
		EntityWorldChangeEvents.AFTER_PLAYER_WORLD_CHANGE.register((player, origin, destination) -> {
			ServerPlayNetworking.send(player, new DesolationSyncPacket(destination));
		});
		ServerPlayerEntityCopyCallback.EVENT.register((copy, original, wasDeath) -> {
			ServerPlayNetworking.send(copy, new DesolationSyncPacket(copy.serverLevel()));
		});
	}
}
