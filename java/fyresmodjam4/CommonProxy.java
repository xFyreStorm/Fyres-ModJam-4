package fyresmodjam4;

import fyresmodjam4.handlers.CommonTickHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	public void registerRenderInformation() {
		MinecraftForge.EVENT_BUS.register(new CommonTickHandler());
	}
}
