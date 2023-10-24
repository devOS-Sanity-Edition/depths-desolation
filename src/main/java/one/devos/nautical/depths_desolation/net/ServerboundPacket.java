package one.devos.nautical.depths_desolation.net;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.level.ServerPlayer;

public interface ServerboundPacket extends FabricPacket {
	void handle(ServerPlayer player, PacketSender responder);
}
