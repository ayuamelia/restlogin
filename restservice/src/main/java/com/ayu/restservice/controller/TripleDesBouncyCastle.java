package com.ayu.restservice.controller;

import java.io.UnsupportedEncodingException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class TripleDesBouncyCastle {

	private static void init() {
        Security.addProvider(new BouncyCastleProvider());
    }
	
	public static String cryptBC(String data, String key) {
		try {
			Security.addProvider(new BouncyCastleProvider());
			Hex Hex = new Hex();
			byte[] input = data.getBytes();
			byte[] keyBytes = key.getBytes();
			SecretKeySpec skey = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");

			if (input.length % 8 != 0) {
				byte[] padded = new byte[input.length + 8 - (input.length % 8)];
				System.arraycopy(input, 0, padded, 0, input.length);
				input = padded;
			}
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
			int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
			ctLength += cipher.doFinal(cipherText, ctLength);

			return charToString(Hex.encodeHex(cipherText));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptBC(String data, String key) {

		try {
			Security.addProvider(new BouncyCastleProvider());
			Hex Hex = new Hex();
			byte[] input = h2b(data);
			byte[] keyBytes = key.getBytes();
			SecretKeySpec skey = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");

			cipher.init(Cipher.DECRYPT_MODE, skey);
			byte[] plaintext = cipher.doFinal(input);
			cipher.doFinal(plaintext);

			return fromHex(Hex.encodeHexString(plaintext)).trim();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static String fromHex(String hex) throws UnsupportedEncodingException {
		hex = hex.replaceAll("^(00)+", "");
		byte[] bytes = DatatypeConverter.parseHexBinary(hex);
		return new String(bytes, "UTF-8");
	}

	public static String charToString(char[] chr) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < chr.length; i++) {
			buff.append(Character.toUpperCase(chr[i]));
		}

		return buff.toString();
	}

	private static byte[] h2b(String hex) {
		if ((hex.length() & 0x01) == 0x01)
			throw new IllegalArgumentException();
		byte[] bytes = new byte[hex.length() / 2];
		for (int idx = 0; idx < bytes.length; ++idx) {
			int hi = Character.digit((int) hex.charAt(idx * 2), 16);
			int lo = Character.digit((int) hex.charAt(idx * 2 + 1), 16);
			if ((hi < 0) || (lo < 0))
				throw new IllegalArgumentException();
			bytes[idx] = (byte) ((hi << 4) | lo);
		}
		return bytes;
	}
}
