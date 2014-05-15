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
