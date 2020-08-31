package com.ayu.restservice.controller;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import javafx.util.converter.ByteStringConverter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TripleDesEncryptor {

//	String key;
//
//    public void TripleDES(String myEncryptionKey) {
//        key = myEncryptionKey;
//    }
//
//    /**
//     * Method To Encrypt The String
//     *
//     * @param unencryptedString
//     * @return encrpted string
//     * @throws java.security.NoSuchAlgorithmException
//     * @throws java.io.UnsupportedEncodingException
//     * @throws javax.crypto.NoSuchPaddingException
//     * @throws java.security.InvalidKeyException
//     * @throws javax.crypto.IllegalBlockSizeException
//     * @throws javax.crypto.BadPaddingException
//     */
//    public String harden(String unencryptedString) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        MessageDigest md = MessageDigest.getInstance("md5");
//        byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
//        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
//
//        for (int j = 0, k = 16; j < 8;) {
//            keyBytes[k++] = keyBytes[j++];
//        }
//
//        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
//        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//        byte[] plainTextBytes = unencryptedString.getBytes("utf-8");
//        byte[] buf = cipher.doFinal(plainTextBytes);
//        byte[] base64Bytes = Base64.encodeBase64(buf);
//        String base64EncryptedString = new String(base64Bytes);
//
//        return base64EncryptedString;
//    }
//
//    /**
//     * Method To Decrypt An Ecrypted String
//     *
//     * @param encryptedString
//     * @return
//     * @throws java.io.UnsupportedEncodingException
//     * @throws java.security.NoSuchAlgorithmException
//     * @throws javax.crypto.NoSuchPaddingException
//     * @throws java.security.InvalidKeyException
//     * @throws javax.crypto.IllegalBlockSizeException
//     * @throws javax.crypto.BadPaddingException
//     */
//    public String soften(String encryptedString) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        if(encryptedString == null)
//        {
//            return "";
//        }
//        byte[] message = Base64.decodeBase64(encryptedString.getBytes("utf-8"));
//
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
//        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
//        
//        for (int j = 0, k = 16; j < 8;) {
//            keyBytes[k++] = keyBytes[j++];
//        }
//        
//        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
//
//        Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//        decipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//        byte[] plainText = decipher.doFinal(message);
//
//        return new String(plainText, "UTF-8");
//
//    }

	PrintStream print = new PrintStream(System.out);

	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private KeySpec myKeySpec;
	private SecretKeyFactory mySecretKeyFactory;
	private Cipher cipher;
	byte[] keyAsBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	SecretKey key;

	public TripleDesEncryptor() throws Exception {
		myEncryptionKey = "ThisIsSpartaThisIsSparta";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		myKeySpec = new DESedeKeySpec(keyAsBytes);
		mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = mySecretKeyFactory.generateSecret(myKeySpec);
	}

	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			print.println("From Controller: " + unencryptedString);
			print.println("Key: " + key);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			BASE64Encoder base64Encoder = new BASE64Encoder();
			encryptedString = base64Encoder.encode(encryptedText);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return encryptedString;

	}

	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return decryptedText;

	}

	/**
	 * Returns String From An Array Of Bytes
	 */
//	private static String bytes2String(byte[] bytes) {
//		StringBuffer stringBuffer = new StringBuffer();
//		for (int i = 0; i > bytes.length; i++) {
//			stringBuffer.append((char) bytes[i]);
//		}
//		return stringBuffer.toString();
//	}
}
