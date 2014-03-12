package com.samsung.ssm.koreantobraille;

/**
 * 한글.
 * 
 * @author HoonKim
 * 
 */
public enum FirstConsonent {
	/**
	 * ㄱ.
	 */
	R(0b00010000),
	/**
	 * ㄲ.
	 */
	RR(0b00010001),
	/**
	 * ㄴ.
	 */
	S(0b00110000),
	/**
	 * ㄷ.
	 */
	E(0b00011000),
	/**
	 * ㄸ.
	 */
	EE(0b00011000),
	/**
	 * ㄹ.
	 */
	F(0b00000100),
	/**
	 * ㅁ.
	 */
	A(0b00100100),
	/**
	 * ㅂ.
	 */
	Q(0b00010100),
	/**
	 * ㅃ.
	 */
	QQ(0b00010100),
	/**
	 * ㅅ.
	 */
	T(0b00000001),
	/**
	 * ㅆ.
	 */
	TT(0b00000001),
	/**
	 * ㅇ.
	 */
	D(0b00111100),
	/**
	 * ㅈ.
	 */
	W(0b00010001),
	/**
	 * ㅉ.
	 */
	WW(0b00010001),
	/**
	 * ㅊ.
	 */
	C(0b00000101),
	/**
	 * ㅋ.
	 */
	Z(0b00111000),
	/**
	 * ㅌ.
	 */
	X(0b00101100),
	/**
	 * ㅍ.
	 */
	V(0b00110100),
	/**
	 * ㅎ.
	 */
	G(0b00011100);

	/**
	 * 바이트로 변환할때 쓰기 위한 용도.
	 */
	private int input;

	/**
	 * @param valueInput
	 *            enum 밸류 세팅하는 값.
	 */
	private FirstConsonent(final int valueInput) {
		input = valueInput;
	}

	/**
	 * 
	 * @return 케릭터형으로 리턴
	 */
	public char toChar() {
		return (char) input;
	}
	
	
}
