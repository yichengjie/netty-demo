package com.yicj.netty.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
	    this.echoMsg(ctx, msg);
	}
	
	private void echoMsg(ChannelHandlerContext ctx, Object msg) {
		ctx.writeAndFlush(msg) ;
	}
	
	private void printMsg(Object msg) {
		try {
			ByteBuf in = (ByteBuf) msg;
			while (in.isReadable()) { // (1)
	            System.out.print((char) in.readByte());
	            System.out.flush();
	        }
		} finally {
	        ReferenceCountUtil.release(msg); // (2)
	    }
	}
	
	

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        cause.printStackTrace();
        ctx.close();
    }
}
