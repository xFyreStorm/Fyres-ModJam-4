package fyresmodjam4;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid= "fyrestorm_modjam4", name = "Fyre's ModJam 4", version = "1.0.0a")
public class Modjam4 {
	
	//Hmm, where to begin...
	
	@SidedProxy(clientSide = "fyresmodjam4.ClientProxy")
	public static CommonProxy proxy;
	
	@Instance("fyrestorm_modjam4")
	public static Modjam4 instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
