
package com.ningcui.mylibrary.utiils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info DES加解密算法类
 */
public class AbDes {

	private byte[] iv;

	public AbDes(byte[] iv) {
		super();
		this.iv = iv;
	}

	public static AbDes newInstance(byte[] iv) {
	  AbDes des = new AbDes(iv);
		return des;
	}

	public String encrypt(byte[] encryptByte, String encryptKey) {
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] encryptedData = cipher.doFinal(encryptByte);
			return AbBase64.encode(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] decrypt(String encryptString, String encryptKey) {
		try {
			byte[] encryptByte = AbBase64.decode(encryptString);
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			return cipher.doFinal(encryptByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	AbDes des = AbDes.newInstance("yxs!1sdf".getBytes());
    	String a = des.encrypt("||||||863920023221158||9c:a9:e4:3b:a1:8a".getBytes(),"bywhjgpt");
    	System.out.println(a);
		System.out.println(des.decrypt(a,"bywhjgpt"));
	}
}