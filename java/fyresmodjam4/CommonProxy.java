package fyresmodjam4;

import cpw.mods.fml.common.FMLCommonHandler;
import fyresmodjam4.handlers.CommonTickHandler;

public class CommonProxy {
	public void registerRenderInformation() {
		FMLCommonHandler.instance().bus().register(new CommonTickHandler());
	}
}
