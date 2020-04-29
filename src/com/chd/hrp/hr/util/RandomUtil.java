package com.chd.hrp.hr.util;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机工具类
 */
public class RandomUtil {

	/**
	 * 用于随机选的数字
	 */
	public static final String BASE_NUMBER = "0123456789";
	/**
	 * 用于随机选的字符
	 */
	public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
	/**
	 * 用于随机选的字符和数字
	 */
	public static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;

	/**
	 * 获取随机数生成器对象<br>
	 * ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
	 *
	 * @return {@link ThreadLocalRandom}
	 * @since 3.1.2
	 */
	public static ThreadLocalRandom getRandom() {
		return ThreadLocalRandom.current();
	}

	/**
	 * 创建{@link SecureRandom}，类提供加密的强随机数生成器 (RNG)<br>
	 *
	 * @param seed
	 *            自定义随机种子
	 * @return {@link SecureRandom}
	 * @since 4.6.5
	 */
	public static SecureRandom createSecureRandom(byte[] seed) {
		return (null == seed) ? new SecureRandom() : new SecureRandom(seed);
	}

	/**
	 * 获得随机Boolean值
	 *
	 * @return true or false
	 * @since 4.5.9
	 */
	public static boolean randomBoolean() {
		return 0 == randomInt(2);
	}

	/**
	 * 获得指定范围内的随机数
	 *
	 * @param min
	 *            最小数（包含）
	 * @param max
	 *            最大数（不包含）
	 * @return 随机数
	 */
	public static int randomInt(int min, int max) {
		return getRandom().nextInt(min, max);
	}

	/**
	 * 获得随机数[0, 2^32)
	 *
	 * @return 随机数
	 */
	public static int randomInt() {
		return getRandom().nextInt();
	}

	/**
	 * 获得指定范围内的随机数 [0,limit)
	 *
	 * @param limit
	 *            限制随机数的范围，不包括这个数
	 * @return 随机数
	 */
	public static int randomInt(int limit) {
		return getRandom().nextInt(limit);
	}

	/**
	 * 获得指定范围内的随机数[min, max)
	 *
	 * @param min
	 *            最小数（包含）
	 * @param max
	 *            最大数（不包含）
	 * @return 随机数
	 * @since 3.3.0
	 */
	public static long randomLong(long min, long max) {
		return getRandom().nextLong(min, max);
	}

	/**
	 * 获得随机数
	 *
	 * @return 随机数
	 * @since 3.3.0
	 */
	public static long randomLong() {
		return getRandom().nextLong();
	}

	/**
	 * 获得指定范围内的随机数 [0,limit)
	 *
	 * @param limit
	 *            限制随机数的范围，不包括这个数
	 * @return 随机数
	 */
	public static long randomLong(long limit) {
		return getRandom().nextLong(limit);
	}

	/**
	 * 获得指定范围内的随机数
	 *
	 * @param min
	 *            最小数（包含）
	 * @param max
	 *            最大数（不包含）
	 * @return 随机数
	 * @since 3.3.0
	 */
	public static double randomDouble(double min, double max) {
		return getRandom().nextDouble(min, max);
	}

	/**
	 * 获得随机数[0, 1)
	 *
	 * @return 随机数
	 * @since 3.3.0
	 */
	public static double randomDouble() {
		return getRandom().nextDouble();
	}

	/**
	 * 获得指定范围内的随机数 [0,limit)
	 *
	 * @param limit
	 *            限制随机数的范围，不包括这个数
	 * @return 随机数
	 * @since 3.3.0
	 */
	public static double randomDouble(double limit) {
		return getRandom().nextDouble(limit);
	}

	/**
	 * 随机bytes
	 *
	 * @param length
	 *            长度
	 * @return bytes
	 */
	public static byte[] randomBytes(int length) {
		byte[] bytes = new byte[length];
		getRandom().nextBytes(bytes);
		return bytes;
	}

	/**
	 * 随机获得列表中的元素
	 *
	 * @param <T>
	 *            元素类型
	 * @param list
	 *            列表
	 * @return 随机元素
	 */
	public static <T> T randomEle(List<T> list) {
		return randomEle(list, list.size());
	}

	/**
	 * 随机获得列表中的元素
	 *
	 * @param <T>
	 *            元素类型
	 * @param list
	 *            列表
	 * @param limit
	 *            限制列表的前N项
	 * @return 随机元素
	 */
	public static <T> T randomEle(List<T> list, int limit) {
		return list.get(randomInt(limit));
	}

	/**
	 * 随机获得数组中的元素
	 *
	 * @param <T>
	 *            元素类型
	 * @param array
	 *            列表
	 * @return 随机元素
	 * @since 3.3.0
	 */
	public static <T> T randomEle(T[] array) {
		return randomEle(array, array.length);
	}

	/**
	 * 随机获得数组中的元素
	 *
	 * @param <T>
	 *            元素类型
	 * @param array
	 *            列表
	 * @param limit
	 *            限制列表的前N项
	 * @return 随机元素
	 * @since 3.3.0
	 */
	public static <T> T randomEle(T[] array, int limit) {
		return array[randomInt(limit)];
	}

	/**
	 * 随机获得列表中的一定量元素
	 *
	 * @param <T>
	 *            元素类型
	 * @param list
	 *            列表
	 * @param count
	 *            随机取出的个数
	 * @return 随机元素
	 */
	public static <T> List<T> randomEles(List<T> list, int count) {
		final List<T> result = new ArrayList<>(count);
		int limit = list.size();
		while (result.size() < count) {
			result.add(randomEle(list, limit));
		}

		return result;
	}

	/**
	 * 获得一个随机的字符串（只包含数字和字符）
	 *
	 * @param length
	 *            字符串的长度
	 * @return 随机字符串
	 */
	public static String randomString(int length) {
		return randomString(BASE_CHAR, length);
	}

	/**
	 * 获得一个随机的字符串（只包含数字和大写字符）
	 *
	 * @param length
	 *            字符串的长度
	 * @return 随机字符串
	 * @since 4.0.13
	 */
	public static String randomStringUpper(int length) {
		return randomString(BASE_CHAR_NUMBER, length).toUpperCase();
	}

	/**
	 * 获得一个只包含数字的字符串
	 *
	 * @param length
	 *            字符串的长度
	 * @return 随机字符串
	 */
	public static String randomNumbers(int length) {
		return randomString(BASE_NUMBER, length);
	}

	/**
	 * 获得一个随机的字符串
	 *
	 * @param baseString
	 *            随机字符选取的样本
	 * @param length
	 *            字符串的长度
	 * @return 随机字符串
	 */
	public static String randomString(String baseString, int length) {
		if (StringUtils.isEmpty(baseString)) {
			return StringUtils.EMPTY;
		}
		final StringBuilder sb = new StringBuilder(length);

		if (length < 1) {
			length = 1;
		}
		int baseLength = baseString.length();
		for (int i = 0; i < length; i++) {
			int number = randomInt(baseLength);
			sb.append(baseString.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 随机数字，数字为0~9单个数字
	 *
	 * @return 随机数字字符
	 * @since 3.1.2
	 */
	public static int randomNumber() {
		return randomChar(BASE_NUMBER);
	}

	/**
	 * 随机字母或数字，小写
	 *
	 * @return 随机字符
	 * @since 3.1.2
	 */
	public static char randomChar() {
		return randomChar(BASE_CHAR_NUMBER);
	}

	/**
	 * 随机字符
	 *
	 * @param baseString
	 *            随机字符选取的样本
	 * @return 随机字符
	 * @since 3.1.2
	 */
	public static char randomChar(String baseString) {
		return baseString.charAt(randomInt(baseString.length()));
	}

	/**
	 * 生成随机颜色
	 *
	 * @return 随机颜色
	 * @since 4.1.5
	 */
	public static Color randomColor() {
		final Random random = getRandom();
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

}
