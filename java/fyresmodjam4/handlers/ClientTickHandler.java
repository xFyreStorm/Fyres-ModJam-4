package fyresmodjam4.handlers;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClientTickHandler {
	@SubscribeEvent
	public void clientTick(ClientTickEvent event) {}
	
	@SubscribeEvent
	public void guiRenderEvent(RenderGameOverlayEvent.Post event) {
		if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			
		}
	}
}
