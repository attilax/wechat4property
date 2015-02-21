package com.focustar.util;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;

public class MD5 {
	
	public static void main(String[] args) {
		System.out.println(MD5.getMD5("汉字123abc"));  //2533FFE650694518A9A3800729906410
		//onli e 2533FFE650694518A9A3800729906410
		System.out.println(MD5.getMD5("汉字123abc","gbk"));
		System.out.println(MD5.getMD5("汉字123abc","utf-8"));
		//B9A5BE4F21341F2A927D9390EF5788DB
		//2533FFE650694518A9A3800729906410

	}
	

	private MD5(){
		
	}
	public static String getMD5(String str,String enc) {
		try {
			return bytestoHex(MessageDigest("MD5", str.getBytes(enc)));
		} catch (InvalidAlgorithmParameterException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

		public static String getMD5(String str) {
			try {
				return bytestoHex(MessageDigest("MD5", str.getBytes()));
			} catch (InvalidAlgorithmParameterException e) {
				return null;
			} catch (Exception e) {
				return null;
			}
		}


		private static String bytestoHex(byte input[]) {
			String hs = "";
			String stmp = "";
			for (int i = 0; i < input.length; i++) {
				stmp = Integer.toHexString(input[i] & 255);
				if (stmp.length() == 1)
					hs = hs + "0" + stmp;
				else
					hs = hs + stmp;
				if (i < input.length - 1)
					hs = hs + "";
			}

			return hs.toUpperCase();
		}

		public static byte[] hextoBytes(String sHexString) {
			byte output[] = new byte[sHexString.length() / 2];
			int j = 0;
			for (int i = 0; i < sHexString.length(); i += 2) {
				output[j] = (byte) (Byte.parseByte(sHexString.substring(i, i + 1),
						16) << 4);
				output[j] = (byte) (output[j] | Byte.parseByte(sHexString
						.substring(i + 1, i + 2), 16));
				j++;
			}
			return output;
		}

		private static byte[] MessageDigest(String algorithm, byte input[])
				throws InvalidAlgorithmParameterException, Exception {
			if (algorithm == null || algorithm.length() == 0)
				algorithm = "MD5";
			MessageDigest alg = java.security.MessageDigest.getInstance(algorithm);
			alg.update(input);
			return alg.digest();
		}

}
