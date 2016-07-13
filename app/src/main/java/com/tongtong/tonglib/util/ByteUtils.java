package com.tongtong.tonglib.util;


public class ByteUtils {

	public static Byte DEFAULT_BYTE = new Byte((byte) 0);

	public static byte toByte(String value, byte defaultValue) {
		try {
			return Byte.parseByte(value);
		} catch(Exception e) {
			return defaultValue;
		}
	}

	/**
	 ByteUtils.toByteObject("1", *)    = 0x01
	 ByteUtils.toByteObject("-1", *)   = 0xff
	 ByteUtils.toByteObject("a", 0x00) = 0x00

	 **/
	public static Byte toByteObject(String value, Byte defaultValue) {
		try {
			return new Byte(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}


	public static int unsignedByte(byte b) {
		return  b & 0xFF;
	}

	public static int toInt(byte[] src, int srcPos) {
		int dword = 0;
		for (int i = 0; i < 4; i++) {
			dword = (dword << 8) + (src[i + srcPos] & 0xFF);
		}
		return dword;
	}

	public static int toInt(byte[] src) {
		return toInt(src, 0);
	}

	public static long toLong(byte[] src, int srcPos) {
		long qword = 0;
		for (int i = 0; i < 8; i++) {
			qword = (qword << 8) + (src[i + srcPos] & 0xFF);
		}
		return qword;
	}

	public static long toLong(byte[] src) {
		return toLong(src, 0);
	}

	public static void toBytes(int value, byte[] dest, int destPos) {
		for (int i = 0; i < 4; i++) {
			dest[i + destPos] = (byte)(value >> ((7 - i) * 8));
		}
	}

	public static byte[] toBytes(int value) {
		byte[] dest = new byte[4];
		toBytes(value, dest, 0);
		return dest;
	}

	public static void toBytes(long value, byte[] dest, int destPos) {
		for (int i = 0; i < 8; i++) {
			dest[i + destPos] = (byte)(value >> ((7 - i) * 8));
		}
	}


	public static byte[] toBytes(long value) {
		byte[] dest = new byte[8];
		toBytes(value, dest, 0);
		return dest;
	}

	/**
	 ByteUtils.toBytes(null)     = null
	 ByteUtils.toBytes("0E1F4E", 16) = [0x0e, 0xf4, 0x4e]
	 ByteUtils.toBytes("48414e", 16) = [0x48, 0x41, 0x4e]

	 **/
	public static byte[] toBytes(String digits, int radix) throws IllegalArgumentException, NumberFormatException {
		if (digits == null) {
			return null;
		}
		if (radix != 16 && radix != 10 && radix != 8) {
			throw new IllegalArgumentException("For input radix: \"" + radix + "\"");
		}
		int divLen = (radix == 16) ? 2 : 3;
		int length = digits.length();
		if (length % divLen == 1) {
			throw new IllegalArgumentException("For input string: \"" + digits + "\"");
		}
		length = length / divLen;
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			int index = i * divLen;
			bytes[i] = (byte)(Short.parseShort(digits.substring(index, index+divLen), radix));
		}
		return bytes;
	}

	/**
	 ByteUtils.toBytesFromHexString(null)     = null
	 ByteUtils.toBytesFromHexString("0E1F4E") = [0x0e, 0xf4, 0x4e]
	 ByteUtils.toBytesFromHexString("48414e") = [0x48, 0x41, 0x4e]
	 **/
	public static byte[] toBytesFromHexString(String digits) throws IllegalArgumentException, NumberFormatException {
		if (digits == null) {
			return null;
		}
		int length = digits.length();
		if (length % 2 == 1) {
			throw new IllegalArgumentException("For input string: \"" + digits + "\"");
		}
		length = length / 2;
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			int index = i * 2;
			bytes[i] = (byte)(Short.parseShort(digits.substring(index, index+2), 16));
		}
		return bytes;
	}

	/**
	 ByteUtils.toHexString((byte)1)   = "01"
	 ByteUtils.toHexString((byte)255) = "ff"

	 **/
	public static String toHexString(byte b) {
		StringBuffer result = new StringBuffer(3);
		result.append(Integer.toString((b & 0xF0) >> 4, 16));
		result.append(Integer.toString(b & 0x0F, 16));
		return result.toString();
	}

	/**
	 ByteUtils.toHexString(null)                   = null
	 ByteUtils.toHexString([(byte)1, (byte)255])   = "01ff"
	 **/
	public static String toHexString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		StringBuffer result = new StringBuffer();
		for (byte b : bytes) {
			result.append(Integer.toString((b & 0xF0) >> 4, 16));
			result.append(Integer.toString(b & 0x0F, 16));
		}
		return result.toString();
	}

	/**
	 ByteUtils.toHexString(null, *, *)                   = null
	 ByteUtils.toHexString([(byte)1, (byte)255], 0, 2)   = "01ff"
	 ByteUtils.toHexString([(byte)1, (byte)255], 0, 1)   = "01"
	 ByteUtils.toHexString([(byte)1, (byte)255], 1, 2)   = "ff"
	 **/
	public static String toHexString(byte[] bytes, int offset, int length) {
		if (bytes == null) {
			return null;
		}

		StringBuffer result = new StringBuffer();
		for (int i = offset; i < offset + length; i++) {
			result.append(Integer.toString((bytes[i] & 0xF0) >> 4, 16));
			result.append(Integer.toString(bytes[i] & 0x0F, 16));
		}
		return result.toString();
	}

	/**
	 ArrayUtils.equals(null, null)                        = true
	 ArrayUtils.equals(["one", "two"], ["one", "two"])    = true
	 ArrayUtils.equals(["one", "two"], ["three", "four"]) = false
	 **/
	public static boolean equals(byte[] array1, byte[] array2) {
		if (array1 == array2) {
			return true;
		}

		if (array1 == null || array2 == null) {
			return false;
		}

		if (array1.length != array2.length) {
			return false;
		}

		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}

		return true;
	}
}
