package fyresmodjam4.items;

import fyresmodjam4.Modjam4;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWeapon extends Item {

	public String name;
	public IIcon[] icons = new IIcon[16];
	
	public ItemWeapon(String name) {
		this.name = name;
		this.setMaxStackSize(1);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		for(int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon("fyresmodjam4:" + name.toLowerCase() + "_" + (i + 1));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {return name;}
	
	@Override
	public IIcon getIconFromDamage(int damage) {return icons[damage % icons.length];}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			if(itemStack.hasTagCompound()) {
				boolean shouldFire = false;
				
				//TODO Check player ammo first.
				
				NBTTagCompound tagCompound = itemStack.getTagCompound();
				
				long time = System.currentTimeMillis();
				if(time - tagCompound.getLong("lastShot") >= tagCompound.getInteger("shotDelay")) {
					tagCompound.setLong("lastShot", time);
					shouldFire = true;
				}
				
				if(shouldFire) {
					Entity entity = EntityList.createEntityByID(tagCompound.getInteger("firedEntity"), world);
					
					if(entity != null) {
						entity.setLocationAndAngles(player.posX, player.posY, player.posZ, player.cameraPitch, player.cameraYaw);
						
						double projectileSpeed = 2.0D;
						double motionX = -MathHelper.sin((float) (player.rotationYaw / 180.0F * Math.PI)) * MathHelper.cos((float) (player.rotationPitch / 180.0F * Math.PI));
						double motionY = MathHelper.cos((float) (player.rotationYaw / 180.0F * Math.PI)) * MathHelper.cos((float) (player.rotationPitch / 180.0F * Math.PI));
						double motionZ = -MathHelper.sin((float) (player.rotationPitch / 180.0F * Math.PI));
						
						double d = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
						
						motionX = (motionX/d) * projectileSpeed;
						motionY = (motionY/d) * projectileSpeed;
						motionZ = (motionZ/d) * projectileSpeed;
						
						double d2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
						
						entity.setAngles((float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI), (float) (Math.atan2(motionY, d2) * 180.0D / Math.PI));
						
						entity.setVelocity(motionX, motionY, motionZ);
						
						world.spawnEntityInWorld(entity);
					}
					
					world.playSoundAtEntity(player, "fyresmodjam4:bullet_shot", 1.0F, 1.0F);
				}
			}
		} else {
			Modjam4.instance.resetRightClickDelay();
		}
		
		return itemStack;
	}

	public static void giveStats(ItemStack stack) {
		if(!stack.hasTagCompound()) {stack.setTagCompound(new NBTTagCompound());}
		NBTTagCompound tagCompound = stack.getTagCompound();
		
		if(tagCompound != null && !tagCompound.hasKey("givenStats")) {
			tagCompound.setBoolean("givenStats", true);
			
			tagCompound.setInteger("shotDelay", 200); //Randomized, or preset?
			tagCompound.setLong("lastShot", (int) System.currentTimeMillis());
			
			tagCompound.setInteger("firedEntity", EntityList.getEntityID(new EntityCow(null)));
		}
	}
}
