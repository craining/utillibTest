package com.zgy.utiltest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * des加解密
 * 
 * @Author zhuanggy
 * @Date:2016-1-25
 * @Copyright (c) 2016, 方正电子 All Rights Reserved.
 * @version
 * @since
 */
public class DesUtil {

	private static final String password = "{*^h2*:-";
//	private static final String password = "20120401";
	private static       byte[] iv       = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * 加密
	 * 
	 * @param @param str
	 * @param @return 
	 * @author zhuanggy
	 * @date 2016-1-25
	 */
	public static String des(String str) {

		try {
			return new String(encryptDES(str, password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 解密
	 * 
	 * @param @param str
	 * @param @return 
	 * @author zhuanggy
	 * @date 2016-1-25
	 */
	public static String dec(String str) {
		try {
			return new String(decryptDES(str, password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encryptDES(String encryptString, String encryptKey) throws Exception {
//		IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
	 
//		Debug.e("", "encryptString=" + encryptString);
//		Debug.e("", "encryptedData=" + new String(encryptedData));
		
		return android.util.Base64.encodeToString(encryptedData, android.util.Base64.DEFAULT);
	}
	public static String decryptDES(String decryptString, String decryptKey) throws Exception {
		byte[] byteMi =  android.util.Base64.decode(decryptString.getBytes(), android.util.Base64.DEFAULT);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
//		IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);
	 
		return new String(decryptedData);
	}

	// /**
	// *
	// * <对字符串进行Des加密，将字符串转化为字节数组解密>
	// */
	//
	// private static byte[] desCrypto(byte[] datasource, String password)
	// throws Exception{
	//
	// SecureRandom random = new SecureRandom();
	// DESKeySpec desKey = new DESKeySpec(password.getBytes());
	// // 创建一个密匙工厂，然后用它把DESKeySpec转换成
	// SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	// SecretKey securekey = keyFactory.generateSecret(desKey);
	// // Cipher对象实际完成加密操作
	// Cipher cipher = Cipher.getInstance("DES");
	// // 用密匙初始化Cipher对象
	// cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
	// // 现在，获取数据并加密
	// // 正式执行加密操作
	// return cipher.doFinal(datasource);
	//
	// }
	//
	// /**
	// *
	// * <将加密的密文字节数组转化为明文字节数组>
	// */
	//
	// private static byte[] decrypt(byte[] src, String password) throws
	// Exception {
	//
	// // DES算法要求有一个可信任的随机数源
	// SecureRandom random = new SecureRandom();
	// // 创建一个DESKeySpec对象
	// DESKeySpec desKey = new DESKeySpec(password.getBytes());
	// // 创建一个密匙工厂
	// SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	// // 将DESKeySpec对象转换成SecretKey对象
	// SecretKey securekey = keyFactory.generateSecret(desKey);
	// // Cipher对象实际完成解密操作
	// Cipher cipher = Cipher.getInstance("DES");
	// // 用密匙初始化Cipher对象
	// cipher.init(Cipher.DECRYPT_MODE, securekey, random);
	// // 真正开始解密操作
	// return cipher.doFinal(src);
	// }
}
