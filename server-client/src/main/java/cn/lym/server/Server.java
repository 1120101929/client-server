package cn.lym.server;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.lym.common.Const;
import cn.lym.common.StreamUtil;

public class Server {
	private static final Logger logger = LogManager.getLogger(Server.class);

	public static void main(String[] args) {
		ServerSocket server;
		try {
			server = new ServerSocket(Const.port);
			while (true) {
				try {
					logger.error("正在启动服务器...");
					logger.info("服务器启动成功，等待客户端连接...");

					Socket client = server.accept();
					logger.info("客户端" + client.getLocalAddress() + "连接成功");

					new Thread(new ServerThread(client)).start();
				} catch (Exception e) {
					StreamUtil.close(server);
					logger.error(null, e);
				}
			}
		} catch (Exception e1) {
		}
	}
}
