package fyresmodjam4.items;

import fyresmodjam4.Modjam4;
import fyresmodjam4.Modjam4.AmmoType;
import fyresmodjam4.handlers.CommonTickHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWeapon extends Item {

	public String name;
	public IIcon[] icons = new IIcon[16];
	public AmmoType ammoType;
	
	public ItemWeapon(String name, AmmoType ammoType) {
		this.ammoType = ammoType;
		this.name = name;
		this.setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName(name.toLowerCase());
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
				
				NBTTagCompound itemCompoundTag = itemStack.getTagCompound();
				
				long time = System.currentTimeMillis();
				if(time - itemCompoundTag.getLong("lastShot") >= itemCompoundTag.getInteger("shotDelay")) {
					itemCompoundTag.setLong("lastShot", time);
					shouldFire = true;
				}
				
				if(shouldFire) {
					Entity entity = EntityList.createEntityByID(itemCompoundTag.getInteger("firedEntity"), world);
					
					if(entity != null) {
						entity.setLocationAndAngles(player.posX, player.posY + player.eyeHeight, player.posZ, player.rotationYaw, player.rotationPitch);
						
						double projectileSpeed = 4.0D;
						double motionX = -MathHelper.sin((float) (entity.rotationYaw / 180.0F * Math.PI)) * MathHelper.cos((float) (entity.rotationPitch / 180.0F * Math.PI));
						double motionZ = MathHelper.cos((float) (entity.rotationYaw / 180.0F * Math.PI)) * MathHelper.cos((float) (entity.rotationPitch / 180.0F * Math.PI));
						double motionY = -MathHelper.sin((float) (entity.rotationPitch / 180.0F * Math.PI));
						
						double d = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
						
						motionX = (motionX/d) * projectileSpeed;
						motionY = (motionY/d) * projectileSpeed;
						motionZ = (motionZ/d) * projectileSpeed;
						
						double d2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
						
						entity.setAngles((float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI), (float) (Math.atan2(motionY, d2) * 180.0D / Math.PI));
						
						entity.setVelocity(motionX, motionY, motionZ);
						
						NBTTagCompound entityCompoundTag = entity.getEntityData();
						
						entityCompoundTag.setInteger("spawnedBy", player.getEntityId());
						
						if(itemCompoundTag.hasKey("explosiveShots") && itemCompoundTag.getBoolean("explosiveShots")) {
							entityCompoundTag.setBoolean("explodeOnContact", true);
							entityCompoundTag.setFloat("explosionSize", 3.0F);
						}
						
						world.spawnEntityInWorld(entity);
						
						CommonTickHandler.tracking.add(entity);
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
			
			tagCompound.setInteger("firedEntity", EntityList.getEntityID(new EntityArrow(null)));
			tagCompound.setBoolean("explosiveShots", true);
		}
	}
}
