package com.green.common;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * 对称加密
 * 
 * @author jlege
 *
 */
public class DESUtils {
	private static String key = "4f04fbbabc2f8ff1";
	
	public static void main(String[] args) {
		System.out.println(jdkDESEncode("172"));
	}

	/**
	 * 生成KEY
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String genKey() throws NoSuchAlgorithmException {
		// 生成KEY
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(56);
		// 产生密钥
		SecretKey secretKey = keyGenerator.generateKey();
		// 获取密钥
		byte[] bytesKey = secretKey.getEncoded();
		return Hex.encodeHexString(bytesKey);
	}

	/**
	 * 加密
	 * 
	 * @param src
	 * @return
	 */
	public static String jdkDESEncode(String src) {
		try {
			// KEY转换
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
			Key convertSecretKey = factory.generateSecret(desKeySpec);

			// 加密（加解密方式：..工作模式/填充方式）
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
			byte[] result = cipher.doFinal(src.getBytes());
			return Hex.encodeHexString(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static String jdkDESDecode(String target) throws Exception {
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
		Key convertSecretKey = factory.generateSecret(desKeySpec);
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
		byte[] result = cipher.doFinal(Hex.decodeHex(target.toCharArray()));
		return new String(result);

	}

}