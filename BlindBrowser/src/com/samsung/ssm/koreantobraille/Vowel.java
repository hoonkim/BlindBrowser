package com.samsung.ssm.koreantobraille;

/**
 * 모음.
 * 
 * @author HoonKim
 * 
 */
public enum Vowel {
	/**
	 * .
	 */
	K(0b00101001),
	/**
	 * .
	 */
	O(0b00101110),
	/**
	 * .
	 */
	I(0b00010110),
	/**
	 * .
	 */
	OO(0b01101110),
	/**
	 * .
	 */
	J(0b00011010),
	/**
	 * .
	 */
	P(0b00110110),
	/**
	 * .
	 */
	U(0b00100101),
	/**
	 * .
	 */
	PP(0b01110110),
	/**
	 * .
	 */
	H(0b00100011),
	/**
	 * .
	 */
	HK(0b00101011),
	/**
	 * .
	 */
	HO(0b01100011),
	/**
	 * .
	 */
	HL(0b00110111),
	/**
	 * .
	 */
	Y(0b00010011),
	/**
	 * .
	 */
	N(0b00110010),
	/**
	 * .
	 */
	NJ(0b00111010),
	/**
	 * .
	 */
	NP(0b01100011),
	/**
	 * .
	 */
	NL(0b10111010),
	/**
	 * .
	 */
	B(0b00110001),
	/**
	 * .
	 */
	M(0b00011001),
	/**
	 * .
	 */
	ML(0b00011101),
	/**
	 * .
	 */
	L(0b00100110);

	/**
	 * 바이트로 변환할때 쓰기 위한 용도.
	 */
	private int input;

	/**
	 * @param valueInput
	 *            enum 밸류 세팅하는 값.
	 */
	/**
	 * Default constructor.
	 * 
	 * @param valueInput
	 *            Enum 필수요소.
	 */
	private Vowel(final int valueInput) {
		input = valueInput;
	};

	/**
	 * 
	 * @return 케릭터형으로 리턴
	 */
	public char toChar() {
		return (char) input;
	}
}
