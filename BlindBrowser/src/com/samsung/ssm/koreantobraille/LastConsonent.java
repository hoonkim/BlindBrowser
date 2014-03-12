package com.samsung.ssm.koreantobraille;

/**
 * 종성.
 * 
 * @author HoonKim
 * 
 */
public enum LastConsonent {
	/**
	 * None.
	 */
	NONE(0b00000000),
	/**
	 * ㄱ.
	 */
	R(0b00100000),
	/**
	 * ㄲ.
	 */
	RR(0b00100001),
	/**
	 * ㄳ.
	 */
	RT(0b01100000),
	/**
	 * ㄴ.
	 */
	S(0b00001100),
	/**
	 * ㄵ.
	 */
	SW(0b01001100),
	/**
	 * ㄶ.
	 */
	SG(0b10001100),
	/**
	 * ㄷ.
	 */
	E(0b00000110),
	/**
	 * ㄹ.
	 */
	F(0b00001000),
	/**
	 * ㄺ.
	 */
	FR(0b01001000),
	/**
	 * ㄻ.
	 */
	FA(0b10001000),
	/**
	 * ㄼ.
	 */
	FQ(0b11001000),
	/**
	 * ㄽ.
	 */
	FT(0b01001001),
	/**
	 * ㄾ.
	 */
	FX(0b10001001),
	/**
	 * ㅁ.
	 */
	A(0b00001001),
	/**
	 * ㅂ.
	 */
	Q(0b00101000),
	/**
	 * ㅅ.
	 */
	T(0b00000010),
	/**
	 * ㅇ.
	 */
	D(0b00001111),
	/**
	 * ㅈ.
	 */
	W(0b00100010),
	/**
	 * ㅊ.
	 */
	C(0b00001010),
	/**
	 * ㅋ.
	 */
	Z(0b00001110),
	/**
	 * ㅌ.
	 */
	X(0b00001011),
	/**
	 * ㅍ.
	 */
	V(0b00001101),
	/**
	 * ㅎ.
	 */
	G(0b00000111);

	/**
	 * 바이트로 변환할때 쓰기 위한 용도.
	 */
	private int input;

	/**
	 * 기본 생성자.
	 * 
	 * @param inputValue
	 *            기본 생성자 요구사항.
	 */
	private LastConsonent(final int inputValue) {
		input = inputValue;
	}

	/**
	 * 
	 * @return 케릭터형으로 리턴
	 */
	public char toChar() {
		return (char) input;
	}

}
