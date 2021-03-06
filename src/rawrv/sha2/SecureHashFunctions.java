package rawrv.sha2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SecureHashFunctions implements SecureHashInterface {
	
	public boolean testing = false;

	public List<Boolean> convertToList(String input) {
		byte[] bitVector = input.getBytes();
		List<Boolean> bitVect = new ArrayList<Boolean>();
		for (byte b : bitVector) {
			ArrayList<Boolean> bytes = new ArrayList<Boolean>();
			for (int i = 0; i < 8; i++) {
				if ((b & 1) == 1)
					bytes.add(0, true);
				else
					bytes.add(0, false);
				b = (byte) (b >> 1);
			}
			bitVect.addAll(bytes);
		}
		return bitVect;
	}

	public List<Boolean> convertToList(BigInteger result) {
		// System.out.print("Input: " + Integer.toHexString(result.intValue())
		// +" ");
		List<Boolean> resultList = new ArrayList<Boolean>();
		int count = 0;
		while (result.compareTo(BigInteger.ZERO) != 0) {
			if (result.and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0) {
				resultList.add(0, true);
			} else {
				resultList.add(0, false);
			}
			result = result.shiftRight(1);
			count++;
		}

		if (count == 0) {
			for (int i = 0; i < 4; i++) {
				resultList.add(0, false);
			}
		}
		for (int i = count; i % 4 != 0; i++) {
			resultList.add(0, false);
		}
		// System.out.println(resultList.size());
		// System.out.println(resultList);
		return resultList;
	}

	public List<Boolean> convertToList(int res) {
		int result = res;
		if (result < 0)
			return convertToListHexString(Integer.toHexString(result));
		int count = 0;
		List<Boolean> resultList = new ArrayList<Boolean>();
		while (result != 0) {
			if ((result & 1) == 1) {
				resultList.add(0, true);
			} else {
				resultList.add(0, false);
			}
			result = result >> 1;
			count++;
		}

		if (count == 0) {
			for (int i = 0; i < 4; i++) {
				resultList.add(0, false);
			}
		}
		for (int i = count; i % 4 != 0; i++) {
			resultList.add(0, false);
		}
		// System.out.println("Input: " + Integer.toHexString(res) + " " + " " +
		// resultList);

		return resultList;
	}

	public List<Boolean> convertToListHexString(String hexString) {
		char[] numbers = hexString.toCharArray();
		List<Boolean> result = new ArrayList<Boolean>();
		for (char c : numbers) {
			result.addAll(convertToList(getNumber(c)));
			// System.out.println("result: " + result + " " + c);
		}
		return result;
	}

	public int getNumber(char c) {
		switch (c) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		default:
			return 15;
		}
	}

	@Override
	public List<Boolean> RotR(List<Boolean> A, int n) {
		List<Boolean> newA = new ArrayList<Boolean>(A);
		n = n % newA.size();
		for (int i = 0; i < n; i++) {
			Boolean temp = newA.remove(newA.size() - 1);
			newA.add(0, temp);
		}
		return newA;
	}

	@Override
	public List<Boolean> ShR(List<Boolean> A, int n) {
		List<Boolean> newA = new ArrayList<Boolean>(A);
		n = n % newA.size();
		for (int i = 0; i < n; i++) {
			newA.remove(newA.size() - 1);
			newA.add(0, false);
		}
		return newA;
	}

	@Override
	public List<Boolean> concat(List<Boolean> A, List<Boolean> B) {
		List<Boolean> newA = new ArrayList<Boolean>(A);
		List<Boolean> newB = new ArrayList<Boolean>(B);
		newA.addAll(newB);
		return newA;
	}

	@Override
	public List<Boolean> Ch(List<Boolean> X, List<Boolean> Y, List<Boolean> Z) {
		return xor(and(new ArrayList<Boolean>(X), new ArrayList<Boolean>(Y)),
				and(not(new ArrayList<Boolean>(X)), new ArrayList<Boolean>(Z)));
	}

	@Override
	public List<Boolean> Maj(List<Boolean> X, List<Boolean> Y, List<Boolean> Z) {
		return xor(
				xor(and(new ArrayList<Boolean>(X), new ArrayList<Boolean>(Y)),
						and(new ArrayList<Boolean>(X),
								new ArrayList<Boolean>(Z))),
				and(new ArrayList<Boolean>(Y), new ArrayList<Boolean>(Z)));
	}

	@Override
	public List<Boolean> SIGMA0(List<Boolean> X) {
		return xor(
				xor(RotR(new ArrayList<Boolean>(X), 2),
						RotR(new ArrayList<Boolean>(X), 13)),
				RotR(new ArrayList<Boolean>(X), 22));
	}

	@Override
	public List<Boolean> SIGMA1(List<Boolean> X) {
		return xor(
				xor(RotR(new ArrayList<Boolean>(X), 6),
						RotR(new ArrayList<Boolean>(X), 11)),
				RotR(new ArrayList<Boolean>(X), 25));
	}

	@Override
	public List<Boolean> sigma0(List<Boolean> X) {
		return xor(
				xor(RotR(new ArrayList<Boolean>(X), 7),
						RotR(new ArrayList<Boolean>(X), 18)),
				ShR(new ArrayList<Boolean>(X), 3));
	}

	@Override
	public List<Boolean> sigma1(List<Boolean> X) {
		return xor(
				xor(RotR(new ArrayList<Boolean>(X), 17),
						RotR(new ArrayList<Boolean>(X), 19)),
				ShR(new ArrayList<Boolean>(X), 10));
	}

	@Override
	public List<Boolean> xor(List<Boolean> A, List<Boolean> B) {
		List<Boolean> newA = new ArrayList<Boolean>(A);
		List<Boolean> newB = new ArrayList<Boolean>(B);
		while (newA.size() < newB.size()) {
			newA.add(false);
		}
		while (newA.size() > newB.size()) {
			newB.add(false);
		}
		for (int i = 0; i < newA.size(); i++) {
			Boolean temp = newA.get(i) ^ newB.get(i);
			newA.set(i, temp);
		}
		return newA;
	}

	@Override
	public List<Boolean> and(List<Boolean> A, List<Boolean> B) {
		List<Boolean> newA = new ArrayList<Boolean>(A);
		List<Boolean> newB = new ArrayList<Boolean>(B);
		while (newA.size() < newB.size()) {
			newA.add(false);
		}
		while (newA.size() > newB.size()) {
			newB.add(false);
		}
		for (int i = 0; i < newA.size(); i++) {
			Boolean temp = newA.get(i) & newB.get(i);
			newA.set(i, temp);
		}
		return newA;
	}

	@Override
	public List<Boolean> or(List<Boolean> A, List<Boolean> B) {
		List<Boolean> newA = new ArrayList<Boolean>(A);
		List<Boolean> newB = new ArrayList<Boolean>(B);
		while (newA.size() < newB.size()) {
			newA.add(false);
		}
		while (newA.size() > newB.size()) {
			newB.add(false);
		}
		for (int i = 0; i < newA.size(); i++) {
			Boolean temp = newA.get(i) | newB.get(i);
			newA.set(i, temp);
		}
		return newA;
	}

	private List<Boolean> not(List<Boolean> A) {
		List<Boolean> newA = new ArrayList<Boolean>(A);
		for (int i = 0; i < newA.size(); i++) {
			Boolean temp = !newA.get(i);
			newA.set(i, temp);
		}
		return newA;
	}

	@Override
	public void padMessage(List<Boolean> input) {
		int origLength = input.size();
		input.add(true);
		// a = b mod m => (a-b)/m is an integer
		int padLength = 1;
		while ((1 + origLength + padLength - 448) < 0)
			padLength++;
		while ((1 + origLength + padLength - 448) % 512 != 0) {
			padLength++;
		}
		// System.out.println(padLength);
		// find k
		for (int j = 0; j < padLength; j++) {
			input.add(false);
		}
		List<Boolean> lastByte = new ArrayList<Boolean>();
		for (int i = 0; i < 64; i++) {
			if ((origLength & 1) == 1)
				lastByte.add(0, true);
			else
				lastByte.add(0, false);
			origLength = origLength >> 1;
		}
		input.addAll(lastByte);
	}

	public List<Boolean> modularAddList(List<Boolean> A, List<Boolean> B) {
		BigInteger result = modularAdd(A, B, 2, 32);
		List<Boolean> resultList = convertToList(result);
		int i = resultList.size();
		while (i < 32) {
			resultList.add(0, false);
			i++;
		}
		// System.out.println(convertToString(resultList)
		// +" "+convertToString(A)+" "+convertToString(B)
		// +" "+resultList.size());
		return resultList;

	}

	public BigInteger modularAdd(List<Boolean> A, List<Boolean> B, int base,
			int expo) {
		BigInteger A1 = listToBigInteger(A);
		BigInteger B1 = listToBigInteger(B);
		BigInteger m = BigInteger.valueOf(base);
		// System.out.println(A1 + " " + B1);
		return (A1.mod(m.pow(expo)).add(B1.mod(m.pow(expo)))).mod(m.pow(expo));
	}

	public BigInteger listToBigInteger(List<Boolean> input) {
		BigInteger bg = BigInteger.ZERO;
		int position = 0;
		int count = 0;
		// System.out.println(input);
		// System.out.println(input+" "+input.size());
		while (position < input.size()) {
			int number = getHalfByte(new ArrayList<Boolean>(input.subList(
					position, position + 4)));
			// System.out.println(number);

			bg = bg.shiftLeft(4);
			bg = bg.or(BigInteger.valueOf(number));
			position += 4;

			// System.out.print(number);
			if (count == 7) {
				// System.out.println();
				count = -1;
			}
			count++;
			// break;
		}
		return bg;
	}

	public static int getHalfByte(List<Boolean> input) {
		int number = 0;
		for (int j = 0; j < 4; j++) {
			number = number << 1;
			if (input.get(j)) {
				number = number | 1;
			} else {
				number = number | 0;
			}

		}
		return number;
	}

	public String convertToString(List<Boolean> input) {
		int position = 0;
		String hexVal = "";
		while (position < input.size()) {
			int number = getHalfByte(input.subList(position, position + 4));
			hexVal += getHexVal(number);
			position += 4;
		}
		return hexVal;
	}

	public String getHexVal(int number) {
		switch (number) {
		case 10:
			return "a";
		case 11:
			return "b";
		case 12:
			return "c";
		case 13:
			return "d";
		case 14:
			return "e";
		case 15:
			return "f";
		default:
			return number + "";
		}
	}

}
