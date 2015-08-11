package cn.lym.common;

import java.io.Closeable;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StreamUtil {
	private static final Logger logger = LogManager.getLogger(StreamUtil.class);

	/**
	 * 关闭流操作
	 * 
	 * @param closeables
	 */
	public static void close(Closeable... closeables) {
		if (closeables != null) {
			for (Closeable closeable : closeables) {
				if (closeable != null) {
					try {
						closeable.close();
					} catch (IOException e) {
						logger.error(null, e);
					} finally {
						closeable = null;
					}
				}
			}
		}
	}
}
