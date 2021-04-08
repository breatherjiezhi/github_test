package com.dhc.rad.modbus.handler;

import java.util.TimerTask;

import com.dhc.rad.modbus.ModbusConstants;
import com.dhc.rad.modbus.entity.ModbusFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 *
 * @author
 */
public class ModbusChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SimpleChannelInboundHandler handler;

    public ModbusChannelInitializer(ModbusRequestHandler handler) {
        this.handler = handler;
    }

    public ModbusChannelInitializer(ModbusResponseHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /*
         * Modbus TCP Frame Description
         *  - max. 260 Byte (ADU = 7 Byte MBAP + 253 Byte PDU)
         *  - Length field includes Unit Identifier + PDU
         *
         * <----------------------------------------------- ADU -------------------------------------------------------->
         * <---------------------------- MBAP -----------------------------------------><------------- PDU ------------->
         * +------------------------+---------------------+----------+-----------------++---------------+---------------+
         * | Transaction Identifier | Protocol Identifier | Length   | Unit Identifier || Function Code | Data          |
         * | (2 Byte)               | (2 Byte)            | (2 Byte) | (1 Byte)        || (1 Byte)      | (1 - 252 Byte |
         * +------------------------+---------------------+----------+-----------------++---------------+---------------+
         */
        pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(ModbusConstants.ADU_MAX_LENGTH, 4, 2));

        //Modbus encoder, decoder
        pipeline.addLast("encoder", new ModbusEncoder());
        pipeline.addLast("decoder", new ModbusDecoder(handler instanceof ModbusRequestHandler));
        
        
        // 2018-10-18 添加 WebSocketServerHandler 
        
//        pipeline.addLast("http-codec",new HttpServerCodec())
//		.addLast("aggregator",new HttpObjectAggregator(65535))
//		.addLast("http-chunked",new ChunkedWriteHandler())
//		.addLast("handler",new WebSocketServerHandler());
        
        
        
        
        

        if (handler instanceof ModbusRequestHandler) {
            //server
            pipeline.addLast("requestHandler", handler);
        } else if (handler instanceof ModbusResponseHandler) {
            //async client
            pipeline.addLast("responseHandler", handler);
        } else {
            //sync client
            pipeline.addLast("responseHandler", new ModbusResponseHandler() {

                @Override
                public void newResponse(ModbusFrame frame) {
                    //discard in sync mode
                }
            });
        }
    }
    
    
    private static class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>{
		private WebSocketServerHandshaker handshaker;
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
			if (msg instanceof FullHttpRequest) {//建立连接的请求
				handleHttpRequest(ctx,(FullHttpRequest)msg);
			}else if (msg instanceof WebSocketFrame){//WebSocket
				handleWebsocketFrame(ctx,(WebSocketFrame)msg);
			}
		}
		
		private void handleWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
			if (frame instanceof CloseWebSocketFrame) {//关闭
				handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame.retain());
			}else if (frame instanceof PingWebSocketFrame) {//ping消息
				ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			}else if (frame instanceof TextWebSocketFrame) {//文本消息
				String request = ((TextWebSocketFrame)frame).text();
				ctx.channel().write(new TextWebSocketFrame("websocket return:"+request));
			}
		}
		private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
			if(request.getDecoderResult().isSuccess()&&"websocket".equals(request.headers().get("Upgrade"))) {
				System.out.println("create WebSocket connection");
				WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
				handshaker = factory.newHandshaker(request);//通过创建请求生成一个握手对象
				if(handshaker != null) {
					handshaker.handshake(ctx.channel(),request);
				}
			}
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}
		@Override
		public void channelActive(final ChannelHandlerContext ctx) throws Exception {
			new java.util.Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					if(handshaker !=null) {
						ctx.channel().write(new TextWebSocketFrame("server:主动给客户端发消息"));
						ctx.flush();
					}
				}
			}, 1000,1000);
		}
		
		
		
	}
    
    
}
