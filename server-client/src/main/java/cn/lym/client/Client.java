package cn.lym.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.lym.common.Const;
import cn.lym.common.StreamUtil;

public class Client {
	private static final Logger logger = LogManager.getLogger(Client.class);

	public static void main(String[] args) {
		Socket socket = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		Scanner scanner = null;

		try {
			logger.info("正在连接服务器...");
			socket = new Socket(Const.host, Const.port);
			logger.info("连接服务器成功");

			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());

			scanner = new Scanner(System.in);
			String message;
			String echo;
			while (true) {
				message = scanner.nextLine();
				logger.info("客户端发送：" + message);
				out.writeUTF(message);
				echo = in.readUTF();
				logger.info("客户端接收：" + echo);
				if ("q".equalsIgnoreCase(message)) {// 退出操作
					// 关闭流
					StreamUtil.close(scanner, out, in, socket);
					break;
				}
			}
		} catch (Exception e) {
			logger.error(null, e);
			StreamUtil.close(scanner, in, out, socket);
		}
	}
}
