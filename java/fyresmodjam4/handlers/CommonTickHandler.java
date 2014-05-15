package fyresmodjam4.handlers;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import fyresmodjam4.items.ItemWeapon;

public class CommonTickHandler {
	
	public ArrayList<Entity> temp = new ArrayList<Entity>();
	
	@SubscribeEvent
	public void serverTick(ServerTickEvent event) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		
		if(server != null) {
			for(int i = 0; i < server.worldServers.length; i++) {
				WorldServer s = server.worldServers[i];
				
				if(s == null) {continue;}
				
				for(Object o : s.playerEntities) {
					if(o == null || !(o instanceof EntityPlayer)) {continue;}
					
					EntityPlayer player = (EntityPlayer) o;
					
					//Inefficient :P Need to find a better place to init stacks.
					for(ItemStack stack : player.inventory.mainInventory) {
						if(stack != null && stack.getItem() instanceof ItemWeapon) {ItemWeapon.giveStats(stack);}
					}
				}
				
				for(Object o : s.loadedEntityList) {
					if(o == null || !(o instanceof Entity)) {continue;}
					
					Entity entity = (Entity) o;
					NBTTagCompound compoundTag = entity.getEntityData();
					
					if(compoundTag.hasKey("explodeOnContact") && compoundTag.getBoolean("explodeOnContact")) {
						if(entity.onGround || entity.isCollided || entity.isCollidedHorizontally || entity.isCollidedVertically) {
							temp.add(entity);
							entity.setDead();
						}
					}
				}
				
				for(Entity entity : temp) {
					NBTTagCompound compoundTag = entity.getEntityData();
					s.createExplosion(entity, entity.posX, entity.posY, entity.posZ, compoundTag.hasKey("explosionSize") ? compoundTag.getFloat("explosionSize"): 3.0F, true);
				}
				
				temp.clear();
			}
		}
	}
}
