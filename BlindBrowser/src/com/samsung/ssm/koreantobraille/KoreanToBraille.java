package com.samsung.ssm.koreantobraille;

/**
 * 한글을 저장해놓고 한글자씩 점자코드로 뽑아줌.
 * 
 * @author HoonKim
 * 
 */
public class KoreanToBraille {

	/**
	 * 유니코드 한글에서 시작하는 범위.
	 */
	private static final int UNICODE_KOREAN = 0XAC00;

	/**
	 * 한 글자에 담길 수 있는 최대 길이 + 1(\0으로 끝나야 하므로).
	 */
	private static final int MAX_LENGTH = 6;

	/**
	 * 종성을 포함한 한글의 최대 유니코드.
	 */
	private static final int MAX_KOREAN_UNICODE_WITH_FINAL = 11172;

	/**
	 * 초성 숫자.
	 */
	private static final int FIRST_CONSONENT_COUNT = 21;

	/**
	 * 중성(모음) 숫자.
	 */
	private static final int VOWEL_COUNT = 28;

	/**
	 * 종성 숫자.
	 */
	private static final int FINAL_CONSONENT_COUNT = 28;

	/* **********************************************
	 * 자음 모음 분리 설연수 -> ㅅㅓㄹㅇㅕㄴㅅㅜ, 바보 -> ㅂㅏㅂㅗ
	 * *********************************************
	 */
	/** 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ). */
	private static FirstConsonent[] firstConsonents = { FirstConsonent.R,
			FirstConsonent.RR, FirstConsonent.S, FirstConsonent.E,
			FirstConsonent.EE, FirstConsonent.F, FirstConsonent.A,
			FirstConsonent.Q, FirstConsonent.QQ, FirstConsonent.T,
			FirstConsonent.TT, FirstConsonent.D, FirstConsonent.W,
			FirstConsonent.WW, FirstConsonent.C, FirstConsonent.Z,
			FirstConsonent.X, FirstConsonent.V, FirstConsonent.G };

	/** 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ). */
	private static Vowel[] arrJungSung = { Vowel.K, Vowel.O, Vowel.I, Vowel.OO,
			Vowel.J, Vowel.P, Vowel.U, Vowel.PP, Vowel.H, Vowel.HK, Vowel.HO,
			Vowel.HL, Vowel.Y, Vowel.N, Vowel.NJ, Vowel.NP, Vowel.NL, Vowel.B,
			Vowel.M, Vowel.ML, Vowel.L };
	/** 종성 - 가(없음), 갈(ㄹ) 천(ㄴ). */
	//@formatter:off
	private static LastConsonent[] arrJongSung = { LastConsonent.NONE,
			LastConsonent.R, LastConsonent.RR, LastConsonent.RT,
			LastConsonent.S, LastConsonent.SW, LastConsonent.SG,
			LastConsonent.E, LastConsonent.F, LastConsonent.FR,
			LastConsonent.FA, LastConsonent.FQ, LastConsonent.FT,
			LastConsonent.FX, LastConsonent.A, LastConsonent.Q,
			LastConsonent.T, LastConsonent.D, LastConsonent.W, 
			LastConsonent.C, LastConsonent.Z, LastConsonent.X, 
			LastConsonent.V, LastConsonent.G };
	//@formatter:on

	/**
	 * 전체스트링.
	 */
	private String fullString;

	/**
	 * 현재 인덱스.
	 */
	private int index;

	/**
	 * 기본 생성자.
	 */
	public KoreanToBraille() {
		index = 0;
	}

	/**
	 * 새 문자열을 저장함. 인덱스는 0으로 초기화됨.
	 * 
	 * @param newString
	 *            새 문자열.
	 */
	public final void setString(final String newString) {
		fullString = newString;
		index = 0;
		return;
	}

	/**
	 * 한글자를 점자 코드로 쪼개서 리턴함.
	 * 
	 * @return 점자코드 배열.
	 */
	public final char[] popWord() {
		char returnChar = (char) (fullString.charAt(index++) - UNICODE_KOREAN);

		int i = returnChar;
		char[] brails = new char[MAX_LENGTH];
		i = 0;
		if (returnChar >= 0 && returnChar <= MAX_KOREAN_UNICODE_WITH_FINAL
				|| index != fullString.length()) {
			/* A. 자음과 모음이 합쳐진 글자인경우 */

			/* A-1. 초/중/종성 분리 */
			int chosung = returnChar
					/ (FIRST_CONSONENT_COUNT * FINAL_CONSONENT_COUNT);
			int jungsung = returnChar
					% (FIRST_CONSONENT_COUNT * FINAL_CONSONENT_COUNT)
					/ VOWEL_COUNT;
			int jongsung = returnChar
					% (FIRST_CONSONENT_COUNT * FINAL_CONSONENT_COUNT)
					% VOWEL_COUNT;

			/* A-2. result에 담기 */
			brails[i++] = firstConsonents[chosung].toChar();

			/* 중성중에 점자 조합으로 된 중성 처리.(ㅟ, ㅒ, ㅙ, ㅞ) */
			if (arrJungSung[jungsung] == Vowel.NL) {
				/* ㅟ는 ㅜ + ㅐ의 조합으로 점자가 구성된다. 이하 설명 생략 */
				brails[i++] = Vowel.N.toChar();
				brails[i++] = Vowel.O.toChar();
			} else if (arrJungSung[jungsung] == Vowel.OO) {
				brails[i++] = Vowel.I.toChar();
				brails[i++] = Vowel.O.toChar();
			} else if (arrJungSung[jungsung] == Vowel.HO) {
				brails[i++] = Vowel.HK.toChar();
				brails[i++] = Vowel.O.toChar();
			} else if (arrJungSung[jungsung] == Vowel.NP) {
				brails[i++] = Vowel.NJ.toChar();
				brails[i++] = Vowel.O.toChar();
			} else {
				/* 나머지 일반 모음들은 그냥 그대로 출력. */
				brails[i++] = arrJungSung[jungsung].toChar();
			}

			/* 자음분리 */
			if (jongsung != 0x0000) {
				/* A-3. 종성이 존재할경우 result에 담는다 */
				brails[i++] = arrJongSung[jongsung].toChar();
			}

		} else {
			brails[0] = '\0';
			return brails;
		}
		char[] toReturn = new char[i];
		for (int j = 0; j < i; j++) {
			toReturn[j] = brails[j];
		}
		return toReturn;
	}

}
