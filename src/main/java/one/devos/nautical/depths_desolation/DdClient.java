package one.devos.nautical.depths_desolation;

import one.devos.nautical.depths_desolation.client.FogManager;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;

public class DdClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientTickEvents.START.register(FogManager::tick);
	}
}
