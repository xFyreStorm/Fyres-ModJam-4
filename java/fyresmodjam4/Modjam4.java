package fyresmodjam4;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import fyresmodjam4.items.ItemWeapon;

@Mod(modid= "fyrestorm_modjam4", name = "Fyre's ModJam 4", version = "1.0.0a")
public class Modjam4 {
	
	//Hmm, where to begin...
	
	@SidedProxy(clientSide = "fyresmodjam4.ClientProxy", serverSide = "fyresmodjam4.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance("fyrestorm_modjam4")
	public static Modjam4 instance;
	
	public static enum AmmoType {
		
		PISTOL("Pistol Ammo", new int[] {200, 300, 400, 500, 600, 700, 800}),
		SHOTGUN("Shotgun Shells", new int[] {80, 100, 120, 140, 160, 180, 200}),
		SNIPER("Sniper Rifle Ammo", new int[] {48, 60, 72, 84, 96, 108, 120}),
		RIFLE("Assault Rifle Ammo", new int[] {280, 420, 560, 700, 840, 980, 1120}),
		SMG("Submachine Gun Ammo", new int[] {360, 540, 720, 900, 1080, 1260, 1440}),
		ROCKET("Rockets", new int[] {42, 32, 40, 48, 56, 64, 72}),
		GRENADE("Grenades", new int[] {3, 4, 5, 6, 7, 8, 9});
		
		public String name;
		public int[] maxAmmoByRank;
		
		AmmoType(String name, int[] maxAmmoByRank) {
			this.name = name;
			this.maxAmmoByRank = maxAmmoByRank;
		}
	}
	
	public static ItemWeapon 
		pistol = (ItemWeapon) new ItemWeapon("Pistol", AmmoType.PISTOL),
		smg,
		assaultRifle,
		sniperRifle,
		shotgun,
		rocketLauncher;
	
	public static ItemWeapon[] weapons = {pistol};
	
	private Field rightClickDelayTimer;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		try {
			rightClickDelayTimer = Minecraft.class.getDeclaredField("rightClickDelayTimer");
			rightClickDelayTimer.setAccessible(true);
		} catch (Exception e) {e.printStackTrace();}
		
		//Config Stuff
		
		//Register Items
		
		for(ItemWeapon weapon : weapons) {
			GameRegistry.registerItem(weapon, weapon.name.toLowerCase());
		}
		
		//Register Blocks
				
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		proxy.registerRenderInformation();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public void resetRightClickDelay() {
		try {
			rightClickDelayTimer.set(Minecraft.getMinecraft(), 0);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		NBTTagCompound tagCompound = player.getEntityData();
		
		if(!tagCompound.hasKey("ammoRanks")) {tagCompound.setTag("ammoRanks", new NBTTagCompound());}
		
		NBTTagCompound ammoRanks = tagCompound.getCompoundTag("ammoRanks");
		if(!ammoRanks.hasKey("Pistol")) {ammoRanks.setInteger("Pistol", 0);}
	}
}
