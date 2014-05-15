package fyresmodjam4.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import fyresmodjam4.items.ItemWeapon;

public class ClientTickHandler {
	@SubscribeEvent
	public void clientTick(ClientTickEvent event) {}
	
	@SubscribeEvent
	public void guiRenderEvent(RenderGameOverlayEvent.Post event) {
		if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			Minecraft minecraft = Minecraft.getMinecraft();
			EntityPlayer player = minecraft != null ? minecraft.thePlayer : null;
			
			if(minecraft != null && player != null) {
				if(player.getHeldItem().getItem() instanceof ItemWeapon) {
					FontRenderer fontRenderer = minecraft.fontRenderer;
				}
			}
		}
	}
}
