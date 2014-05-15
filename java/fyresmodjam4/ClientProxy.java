package fyresmodjam4;

import net.minecraftforge.common.MinecraftForge;
import fyresmodjam4.handlers.ClientTickHandler;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderInformation() {
		super.registerRenderInformation();
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
	}
}
