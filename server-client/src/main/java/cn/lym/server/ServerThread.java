package cn.lym.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import cn.lym.common.StreamUtil;

public class ServerThread implements Runnable {
	private Logger logger = Logger.getLogger(ServerThread.class);

	private Socket client;

	private DataInputStream in;
	private DataOutputStream out;

	public ServerThread(Socket client) throws Exception {
		this.client = client;

		this.in = new DataInputStream(this.client.getInputStream());
		this.out = new DataOutputStream(this.client.getOutputStream());
	}

	@Override
	public void run() {
		String message;
		String echo;
		while (true) {
			try {
				message = this.in.readUTF();
				logger.info("服务器接收：" + message);
				echo = "echo: " + message;
				logger.info("服务器发送：" + echo);
				this.out.writeUTF(echo);

				if ("q".equalsIgnoreCase(message)) {// 关闭客户端
					// 关闭各种输入、输出流
					StreamUtil.close(this.in, this.out);
					// 退出循环
					break;
				}
			} catch (Exception e) {
				StreamUtil.close(this.in, this.out);
				this.logger.error(null, e);
			}
		}
	}

}
