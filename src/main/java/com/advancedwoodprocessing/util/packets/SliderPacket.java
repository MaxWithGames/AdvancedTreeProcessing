package com.advancedwoodprocessing.util.packets;

import com.advancedwoodprocessing.util.entities.TileEntityBonfire;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SliderPacket implements IMessage {
    private BlockPos pos;
    private short count;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        count = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeShort(count);
    }
    
    public SliderPacket() {
    }
    
    public SliderPacket(BlockPos pos, short count) {
    	this.count = count;
    	this.pos = pos;
    }

    public static class Handler implements IMessageHandler<SliderPacket, IMessage> {
        @Override
        public IMessage onMessage(SliderPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SliderPacket message, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            
            if (world.isBlockLoaded(message.pos))
            	if (world.getTileEntity(message.pos) instanceof TileEntityBonfire)
            		((TileEntityBonfire) world.getTileEntity(message.pos)).setField(1, message.count);
        }
    }
}