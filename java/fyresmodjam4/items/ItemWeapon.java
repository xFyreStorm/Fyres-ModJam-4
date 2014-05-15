package fyresmodjam4.items;

import fyresmodjam4.Modjam4;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemWeapon extends Item {

	public String name;
	public IIcon[] icons = new IIcon[16];
	
	public ItemWeapon(String name) {
		this.name = name;
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
}
