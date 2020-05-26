package org.loocsij.util;

import java.util.StringTokenizer;

/**
 * Number utility. Provide following functions:
 * @author wengm
 *
 */
public class NumberUtil extends Number implements Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8901960769003888053L;

	public static final int DOUBLE = 0;

	public static final int LONG = 1;

	public static final int FLOAT = 2;

	public static final int INT = 3;

	public static final int SHORT = 4;

	public static final int BYTE = 5;

	private Number value = null;

	private int type = -1;

	public NumberUtil(double d) {
		value = new Double(d);
		type = DOUBLE;
	}

	public NumberUtil(long d) {
		value = new Long(d);
		type = LONG;
	}

	public NumberUtil(float d) {
		value = new Float(d);
		type = FLOAT;
	}

	public NumberUtil(int d) {
		value = new Integer(d);
		type = INT;
	}

	public NumberUtil(short d) {
		value = new Short(d);
		type = SHORT;
	}

	public NumberUtil(byte d) {
		value = new Byte(d);
		type = BYTE;
	}

	public int compareTo(Object arg0) {
		if (!(arg0 instanceof NumberUtil)) {
			return -1;
		}
		NumberUtil nu = (NumberUtil) arg0;
		switch (type) {
		case DOUBLE:
			return ((Double) value).compareTo((Double) nu.value);
		case LONG:
			return ((Long) value).compareTo((Long) nu.value);
		case FLOAT:
			return ((Float) value).compareTo((Float) nu.value);
		case INT:
			return ((Integer) value).compareTo((Integer) nu.value);
		case SHORT:
			return ((Short) value).compareTo((Short) nu.value);
		case BYTE:
			return ((Byte) value).compareTo((Byte) nu.value);
		default:
			throw new RuntimeException("Invalid number type:" + type);
		}
	}

	private static NumberUtil[] convert(String[] strNums, int type) {
		if (strNums == null) {
			return null;
		}
		int l = strNums.length;
		if (l == 0) {
			return new NumberUtil[0];
		}
		NumberUtil[] ns = new NumberUtil[l];
		NumberUtil n = null;
		for (int i = 0; i < l; i++) {
			switch (type) {
			case DOUBLE:
				n = new NumberUtil(Double.parseDouble(strNums[i]));
				break;
			case LONG:
				n = new NumberUtil(Long.parseLong(strNums[i]));
				break;
			case FLOAT:
				n = new NumberUtil(Float.parseFloat(strNums[i]));
				break;
			case INT:
				n = new NumberUtil(Integer.parseInt(strNums[i]));
				break;
			case SHORT:
				n = new NumberUtil(Short.parseShort(strNums[i]));
				break;
			case BYTE:
				n = new NumberUtil(Byte.parseByte(strNums[i]));
				break;
			default:
				throw new RuntimeException("Invalid number type:" + type);
			}
			ns[i] = n;
		}
		return ns;
	}

	static NumberUtil[] convert(double[] ds) {
		if (ds == null) {
			return null;
		}
		int l = ds.length;
		if (l == 0) {
			return new NumberUtil[0];
		}
		String[] strs = new String[l];
		for (int i = 0; i < l; i++) {
			strs[i] = String.valueOf(ds[i]);
		}
		return convert(strs, DOUBLE);
	}

	static NumberUtil[] convert(long[] ls) {
		if (ls == null) {
			return null;
		}
		int l = ls.length;
		if (l == 0) {
			return new NumberUtil[0];
		}
		String[] strs = new String[l];
		for (int i = 0; i < l; i++) {
			strs[i] = String.valueOf(ls[i]);
		}
		return convert(strs, LONG);
	}

	static NumberUtil[] convert(float[] fs) {
		if (fs == null) {
			return null;
		}
		int l = fs.length;
		if (l == 0) {
			return new NumberUtil[0];
		}
		String[] strs = new String[l];
		for (int i = 0; i < l; i++) {
			strs[i] = String.valueOf(fs[i]);
		}
		return convert(strs, FLOAT);
	}

	static NumberUtil[] convert(int[] is) {
		if (is == null) {
			return null;
		}
		int l = is.length;
		if (l == 0) {
			return new NumberUtil[0];
		}
		String[] strs = new String[l];
		for (int i = 0; i < l; i++) {
			strs[i] = String.valueOf(is[i]);
		}
		return convert(strs, INT);
	}

	static NumberUtil[] convert(short[] ss) {
		if (ss == null) {
			return null;
		}
		int l = ss.length;
		if (l == 0) {
			return new NumberUtil[0];
		}
		String[] strs = new String[l];
		for (int i = 0; i < l; i++) {
			strs[i] = String.valueOf(ss[i]);
		}
		return convert(strs, SHORT);
	}

	static NumberUtil[] convert(byte[] bs) {
		if (bs == null) {
			return null;
		}
		int l = bs.length;
		if (l == 0) {
			return new NumberUtil[0];
		}
		String[] strs = new String[l];
		for (int i = 0; i < l; i++) {
			strs[i] = String.valueOf(bs[i]);
		}
		return convert(strs, BYTE);
	}

	public static double[] doubleArray(Number[] ns) {
		if (ns == null) {
			return null;
		}
		int l = ns.length;
		if (l == 0) {
			return new double[0];
		}
		double[] ds = new double[l];
		for (int i = 0; i < l; i++) {
			ds[i] = ns[i].doubleValue();
		}
		return ds;
	}

	public static long[] longArray(Number[] ns) {
		if (ns == null) {
			return null;
		}
		int l = ns.length;
		if (l == 0) {
			return new long[0];
		}
		long[] ls = new long[l];
		for (int i = 0; i < l; i++) {
			ls[i] = ns[i].longValue();
		}
		return ls;
	}

	public static float[] floatArray(Number[] ns) {
		if (ns == null) {
			return null;
		}
		int l = ns.length;
		if (l == 0) {
			return new float[0];
		}
		float[] fs = new float[l];
		for (int i = 0; i < l; i++) {
			fs[i] = ns[i].floatValue();
		}
		return fs;
	}

	public static int[] intArray(Number[] ns) {
		if (ns == null) {
			return null;
		}
		int l = ns.length;
		if (l == 0) {
			return new int[0];
		}
		int[] is = new int[l];
		for (int i = 0; i < l; i++) {
			is[i] = ns[i].intValue();
		}
		return is;
	}

	public static short[] shortArray(Number[] ns) {
		if (ns == null) {
			return null;
		}
		int l = ns.length;
		if (l == 0) {
			return new short[0];
		}
		short[] ss = new short[l];
		for (int i = 0; i < l; i++) {
			ss[i] = ns[i].shortValue();
		}
		return ss;
	}

	public static byte[] byteArray(Number[] ns) {
		if (ns == null) {
			return null;
		}
		int l = ns.length;
		if (l == 0) {
			return new byte[0];
		}
		byte[] bs = new byte[l];
		for (int i = 0; i < l; i++) {
			bs[i] = ns[i].byteValue();
		}
		return bs;
	}

	public double doubleValue() {
		switch (type) {
		case DOUBLE:
			return ((Double) value).doubleValue();
		case LONG:
			return ((Long) value).doubleValue();
		case FLOAT:
			return ((Float) value).doubleValue();
		case INT:
			return ((Integer) value).doubleValue();
		case SHORT:
			return ((Short) value).doubleValue();
		case BYTE:
			return ((Byte) value).doubleValue();
		default:
			throw new RuntimeException("Invalid number type:" + type);
		}
	}

	public float floatValue() {
		switch (type) {
		case DOUBLE:
			return ((Double) value).floatValue();
		case LONG:
			return ((Long) value).floatValue();
		case FLOAT:
			return ((Float) value).floatValue();
		case INT:
			return ((Integer) value).floatValue();
		case SHORT:
			return ((Short) value).floatValue();
		case BYTE:
			return ((Byte) value).floatValue();
		default:
			throw new RuntimeException("Invalid number type:" + type);
		}
	}

	public int intValue() {
		switch (type) {
		case DOUBLE:
			return ((Double) value).intValue();
		case LONG:
			return ((Long) value).intValue();
		case FLOAT:
			return ((Float) value).intValue();
		case INT:
			return ((Integer) value).intValue();
		case SHORT:
			return ((Short) value).intValue();
		case BYTE:
			return ((Byte) value).intValue();
		default:
			throw new RuntimeException("Invalid number type:" + type);
		}
	}

	public long longValue() {
		switch (type) {
		case DOUBLE:
			return ((Double) value).longValue();
		case LONG:
			return ((Long) value).longValue();
		case FLOAT:
			return ((Float) value).longValue();
		case INT:
			return ((Integer) value).longValue();
		case SHORT:
			return ((Short) value).longValue();
		case BYTE:
			return ((Byte) value).longValue();
		default:
			throw new RuntimeException("Invalid number type:" + type);
		}
	}

	private static java.util.Random random = new java.util.Random(
			new java.util.Date().getTime());

	public static double[] randomDoubleArray(int number) {
		if (number <= 0) {
			return new double[0];
		}
		double[] ds = new double[number];
		for (int i = 0; i < ds.length; i++) {
			ds[i] = random.nextDouble();
		}
		return ds;
	}

	public static long[] randomLongArray(int number) {
		if (number <= 0) {
			return new long[0];
		}
		long[] ls = new long[number];
		for (int i = 0; i < ls.length; i++) {
			ls[i] = random.nextLong();
		}
		return ls;
	}

	public static float[] randomFloatArray(int number) {
		if (number <= 0) {
			return new float[0];
		}
		float[] fs = new float[number];
		for (int i = 0; i < fs.length; i++) {
			fs[i] = random.nextLong();
		}
		return fs;
	}

	public static int[] randomIntArray(int number) {
		if (number <= 0) {
			return new int[0];
		}
		int[] is = new int[number];
		for (int i = 0; i < is.length; i++) {
			is[i] = random.nextInt();
		}
		return is;
	}

	public static short[] randomShortArray(int number) {
		if (number <= 0) {
			return new short[0];
		}
		short[] is = new short[number];
		for (int i = 0; i < is.length; i++) {
			is[i] = (short) (random.nextInt() & 0x0000ffff);
		}
		return is;
	}

	public static byte[] randomByteArray(int number) {
		if (number <= 0) {
			return new byte[0];
		}
		byte[] ls = new byte[number];
		random.nextBytes(ls);
		return ls;
	}

	public static int randomInt(){
		return random.nextInt();
	}
	
	public static String numberFormat(String number, String kilobit,
			String decimal) {
		StringTokenizer to = new StringTokenizer(number, ".");

		String integer = null;
		String decimalFraction = null;
		StringBuffer buf = new StringBuffer();
		if (to.hasMoreElements()) {
			integer = to.nextToken();
			int count = 0;
			int length = integer.length();
			String p = null;
			for (int j = length; j >= 0; j--) {
				if (count == 3) {
					p = integer.substring(j);
					buf.insert(0, p);
					if (j > 0) {
						buf.insert(0, kilobit);
					}
					integer = integer.substring(0, j);
					count = 0;
				}
				count++;
			}
			if (integer.length() > 0) {
				buf.insert(0, integer);
			}
		}
		if (to.hasMoreElements()) {
			decimalFraction = to.nextToken();
		}
		if (integer == null) {
			integer = "";
		}
		if (decimalFraction == null) {
			decimalFraction = "";
		} else {
			buf.append(decimal).append(decimalFraction);
		}
		if (integer == null && decimalFraction == null) {
			return "";
		}
		return buf.toString();
	}
	
	public static boolean isPrime(int i){
		for(int k=2;k<i;k++){
			if(i%k==0){
				return false;
			}
		}
		return true;
	}
	
	
	public static void main(String[] strs) {
		int i=0;
		while(i++<1000){
			if(isPrime(i)){
				System.out.println(i);
			}
		}
	}
}
