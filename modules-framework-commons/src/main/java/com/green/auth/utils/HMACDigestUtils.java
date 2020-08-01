package com.green.auth.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * 加密工具
 * 
 * @author yuanhualiang
 */
public class HMACDigestUtils {

	private static final String ALGORITHM_HMACSHA512 = "HmacSHA512";
	private static final String ALGORITHM_HMACSHA256 = "HmacSHA256";
	private static final String ALGORITHM_HMACMD5 = "HmacMD5";

	public HMACDigestUtils() {
	}

	public static String simpleSign(String key, String data) {
		int hash = 5381;
		String sKey = (new StringBuilder()).append(key).append(data).toString();
		if (StringUtils.isNotBlank(sKey)) {
			for (int i = 0; i < sKey.length(); i++)
				hash += (hash << 5) + sKey.charAt(i);

		}
		return String.valueOf(hash & 2147483647);
	}

	public static String sha512Hex(String key, String data) {
		return shaHex(key, data, ALGORITHM_HMACSHA512);
	}

	public static String sha256Hex(String key, String data) {
		return shaHex(key, data, ALGORITHM_HMACSHA256);
	}

	public static String md5Hex(String key, String data) {
		return shaHex(key, data, ALGORITHM_HMACMD5);
	}

	public static void main(String args[]) {
		System.out.println(md5Hex("1", "2"));
	}

	private static String shaHex(String key, String data, String algorithm) {
		Mac mac = getMac(algorithm);
		SecretKeySpec secret_key = new SecretKeySpec(
				org.apache.commons.codec.binary.StringUtils.getBytesUtf8(key),
				algorithm);
		try {
			mac.init(secret_key);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e);
		}
		return Hex.encodeHexString(mac
				.doFinal(org.apache.commons.codec.binary.StringUtils
						.getBytesUtf8(data)));
	}

	public static Mac getMac(String algorithm) {
		try {
			Mac a = Mac.getInstance(algorithm);
			return a;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}
}