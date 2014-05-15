package fyresmodjam4;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import fyresmodjam4.items.ItemWeapon;

@Mod(modid= "fyrestorm_modjam4", name = "Fyre's ModJam 4", version = "1.0.0a")
public class Modjam4 {
	
	//Hmm, where to begin...
	
	@SidedProxy(clientSide = "fyresmodjam4.ClientProxy", serverSide = "fyresmodjam4.CommonProxy")
	public static CommonProxy proxy;
	
	public Item pistol;
	
	@Instance("fyrestorm_modjam4")
	public static Modjam4 instance;
	
	public Field rightClickDelayTimer;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		try {
			rightClickDelayTimer = Minecraft.class.getDeclaredField("rightClickDelayTimer");
			rightClickDelayTimer.setAccessible(true);
		} catch (Exception e) {}
		
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
}
