package com.gzp1124.gutils.utils;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密解密工具类
 * @author gzp
 * 2015-6-17  上午10:02:12
 */
public class GAESUtil {

	private static String AESKEY ="WOQNIENTOSKJLEXB";
	private static String ivKey = "0102030405060708";

	/**
	 * 加密
	 * 
	 * @param strIn 要加密的文本
	 * @return
	 */
	public static String encode(String strIn) {
		if (TextUtils.isEmpty(strIn)) {
			return null;
		}
		try {
			SecretKeySpec skeySpec = getKey(AESKEY);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(strIn.getBytes());
			//AES结合 base64 使用
//			char[] encode = Base64Coder.encode(encrypted);
			return new String(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param strIn 待解密的文本
	 * @return
	 */
	public static String decode(String strIn) {
		if (TextUtils.isEmpty(strIn)) {
			return null;
		}
		try {
			SecretKeySpec skeySpec = getKey(AESKEY);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec,iv);
			//AES结合 base64 使用
//			byte[] encrypted1 = Base64Coder.decode(strIn);
			byte[] original = cipher.doFinal(strIn.getBytes());
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取适配密钥
	 * 
	 * @param strKey
	 * @return
	 * @throws Exception
	 */
	private static SecretKeySpec getKey(String strKey) throws Exception {
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

		return skeySpec;
	}

}
