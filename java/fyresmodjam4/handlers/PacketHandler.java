package fyresmodjam4.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketHandler {

	public static interface IPacket {
		
		public void readBytes(ByteBuf bytes);
		public void writeBytes(ByteBuf bytes);
		
		public void executeClient(EntityPlayer player);
		public void executeServer(EntityPlayer player);
		public void executeBoth(EntityPlayer player);
		
	}
	
	public static class BasicPacket implements IPacket {

		@Override
		public void readBytes(ByteBuf bytes) {
			
		}

		@Override
		public void writeBytes(ByteBuf bytes) {
			
		}

		@Override
		public void executeClient(EntityPlayer player) {
			
		}

		@Override
		public void executeServer(EntityPlayer player) {
			
		}

		@Override
		public void executeBoth(EntityPlayer player) {
			
		}
		
	}
	
	public static class ChannelHandler extends FMLIndexedMessageToMessageCodec<IPacket> {
		
		public ChannelHandler() {
			addDiscriminator(0, BasicPacket.class);
		}

		@Override
		public void encodeInto(ChannelHandlerContext ctx, IPacket msg, ByteBuf target) throws Exception {
			msg.writeBytes(target);
		}

		@Override
		public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, IPacket msg) {
			msg.readBytes(source);
			
			EntityPlayer player = null;
			
			switch (FMLCommonHandler.instance().getEffectiveSide()) {
				case CLIENT:
					player = getClientPlayer();
					if(player != null) {msg.executeClient(player);}
				case SERVER:
					INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
					player = ((NetHandlerPlayServer) netHandler).playerEntity;
					if(player != null) {msg.executeServer(player);}
				default: break;
			}
			
			if(player != null) {msg.executeBoth(player);}
		}
		
		@SideOnly(Side.CLIENT)
		public static EntityPlayer getClientPlayer() {return Minecraft.getMinecraft().thePlayer;}
		
	}

}
