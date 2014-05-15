package fyresmodjam4;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
	
	public static int[][] maxAmmoByRank = {
		{200, 300, 400, 500, 600, 700, 800}, //pistol ammo
		{80, 100, 120, 140, 160, 180, 200}, //shotgun ammo
		{48, 60, 72, 84, 96, 108, 120}, //sniper ammo
		{280, 420, 560, 700, 840, 980, 1120}, //rifle ammo
		{360, 540, 720, 900, 1080, 1260, 1440}, //smg ammo
		{42, 32, 40, 48, 56, 64, 72}, //rocket ammo
		{3, 4, 5, 6, 7, 8, 9}  //grenade ammo
	};
	
	public static Item pistol, smg, assaultRifle, sniperRifle, shotgun, rocketLauncher;
	
	private Field rightClickDelayTimer;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		try {
			rightClickDelayTimer = Minecraft.class.getDeclaredField("rightClickDelayTimer");
			rightClickDelayTimer.setAccessible(true);
		} catch (Exception e) {e.printStackTrace();}
		
		//Config Stuff
		
		//Register Items
		
		pistol = new ItemWeapon("Pistol").setUnlocalizedName("pistol").setCreativeTab(CreativeTabs.tabCombat);
		GameRegistry.registerItem(pistol, "pistol");

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
	}
}
