package com.chd.base.InternetBank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

	public static String getrevFromBASE64(byte[] s) {
		if (s == null)
			return null;
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		try {
			return encoder.encode(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] getbyteFromBASE64(String s) {
		if (s == null)
			return null;
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			return decoder.decodeBuffer(s);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * 压缩字符串数组为Zip后的Base64格式
	 * 
	 * @param src 源BYTE数组
	 * 
	 * @return 输出的BASE64字符串
	 */
	public static String ZipandBase64(byte[] src) {
		if (src == null)
			return null;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip;

		try {
			gzip = new GZIPOutputStream(out);

			gzip.write(src);

			gzip.close();

			BASE64Encoder encoder = new BASE64Encoder();

			return encoder.encode(out.toByteArray());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 反base64编码并解压
	 * 
	 * @param src 源字符串
	 * 
	 * @return 输出解码后的数组
	 */
	public static byte[] UnZipandUnbase64(String src) {
		if (src == null || src.length() == 0)
			return null;

		try {
			BASE64Decoder decoder = new BASE64Decoder();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in;

			in = new ByteArrayInputStream(decoder.decodeBuffer(src));

			GZIPInputStream gzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int bSize = 0;
			while ((bSize = gzip.read(buffer)) >= 0) {
				out.write(buffer, 0, bSize);
			}
			gzip.close();
			return out.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
