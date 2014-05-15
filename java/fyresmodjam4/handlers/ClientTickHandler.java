package fyresmodjam4.handlers;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import fyresmodjam4.items.ItemWeapon;

public class ClientTickHandler {
	@SubscribeEvent
	public void clientTick(ClientTickEvent event) {}
	
	@SubscribeEvent
	public void guiRenderEvent(RenderGameOverlayEvent.Post event) {
		if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
			Minecraft minecraft = Minecraft.getMinecraft();
			EntityPlayer player = minecraft != null ? minecraft.thePlayer : null;
			
			if(minecraft != null && player != null) {
				NBTTagCompound tagCompound = player.getEntityData();
				ItemStack held = player.getHeldItem();
				
				if(tagCompound.hasKey("ammoInfo") && held != null && held.getItem() instanceof ItemWeapon) {
					NBTTagCompound ammoInfo = tagCompound.getCompoundTag("ammoInfo");
					
					ItemWeapon weapon = (ItemWeapon) held.getItem();
					String name = weapon.name.replace(" ", "");
					
					if(ammoInfo.hasKey(name) && ammoInfo.hasKey(name + "Rank")) {
						FontRenderer fontRenderer = minecraft.fontRenderer;
						fontRenderer.drawString(ammoInfo.getInteger(name + "Rank") + "/" + weapon.validAmmoTypes[tagCompound.hasKey("selectedAmmoType") ? tagCompound.getInteger("selectedAmmoType") : 0].maxAmmoByRank[ammoInfo.getInteger(name)], 0, 0, Color.WHITE.getRGB());
					}
				}
			}
		}
	}
}
