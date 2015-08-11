package cn.lym.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.lym.common.Const;
import cn.lym.common.StreamUtil;

public class Server {
	private static final Logger logger = LogManager.getLogger(Server.class);

	public static void main(String[] args) {
		ServerSocket server = null;
		DataInputStream in = null;
		DataOutputStream out = null;

		try {
			logger.error("正在启动服务器...");
			server = new ServerSocket(Const.port);
			logger.info("服务器启动成功，等待客户端连接...");

			Socket client = server.accept();
			logger.info("客户端" + client.getLocalAddress() + "连接成功");

			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());

			String message;
			while (true) {
				message = in.readUTF();
				logger.info("服务器接收客户端" + client.getLocalAddress() + "的消息：" + message);
				out.writeUTF("echo: " + message);
				if ("q".equalsIgnoreCase(message)) {
					StreamUtil.close(in, out, server);
					break;
				}
			}
		} catch (Exception e) {
			StreamUtil.close(in, out, server);
			logger.error(null, e);
		}
	}
}
