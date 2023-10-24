package one.devos.nautical.depths_desolation.net.packet;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.duck.LevelExt;
import one.devos.nautical.depths_desolation.net.ClientboundPacket;
import one.devos.nautical.depths_desolation.net.DdNetworking;

import org.quiltmc.loader.api.minecraft.ClientOnly;

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
}
