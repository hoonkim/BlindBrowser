package com.samsung.ssm.blindbrowser.device;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.samsung.ssm.koreantobraille.FirstConsonent;
import com.samsung.ssm.koreantobraille.KoreanToBraille;
import com.samsung.ssm.koreantobraille.Vowel;

/**
 * 장치를 관리한다.
 * 
 * @author HoonKim
 * 
 */
public class DeviceControl implements Runnable {

	/**
	 * 버퍼 사이즈.
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * 다음 글자 요청.
	 */
	private static final int NEXT_TOKEN = 0b11111111;

	/**
	 * 파일 리더.
	 */
	private FileReader reader = null;
	/**
	 * 파일 라이터.
	 */
	private FileWriter writer = null;

	/**
	 * 한글 to 점자.
	 */
	private KoreanToBraille braille = null;

	/**
	 * 키보드 입력용 로봇.
	 */
	private Robot robot = null;

	/**
	 * 입력용과 출력용 파일을 열어줌.
	 * 
	 * @throws IOException
	 *             파일리더, 파일 라이터를 여는데 실패할경우.
	 * @throws AWTException
	 *             로봇을 위한 예외처리.
	 */
	public DeviceControl() throws IOException, AWTException {
		reader = new FileReader("\\\\?\\USB#VID_10C4&PID_EA60#0001#"
				+ "{3d74c75d-9d60-4a43-824c-24da88d52aa6}");
		writer = new FileWriter("\\\\?\\USB#VID_10C4&PID_EA60#0001#"
				+ "{3d74c75d-9d60-4a43-824c-24da88d52aa6}");
		braille = new KoreanToBraille();
		robot = new Robot();
	}

	@Override
	public final void run() {
		char[] arr = new char[BUFFER_SIZE];
		int readCount;
		while (true) {
			try {
				readCount = reader.read(arr);
				arr[readCount] = '\0';
				if (readCount > 0) {
					for (int i = 0; i < readCount; i++) {
						if (arr[i] == NEXT_TOKEN) {
							next();
						} else {
							toKeyInput(arr[i]);
						}
					}
				}
			} catch (IOException e) {
			}

		}
	}

	/**
	 * 장치에 전송함.
	 * 
	 * @param buf
	 *            전송할 케릭터.
	 */
	private void sendChar(final char[] buf) {
		try {
			writer.write(buf);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 외부에서 스트링을 전송해 달라고 요청하면 우선 KoreanToBraille 클래스에 저장한뒤, 그걸 첫번째 글짜를 장치로 전송함.
	 * 
	 * @param string
	 *            전송할 스트링.
	 */
	public final void send(final String string) {
		braille.setString(string);
		sendChar(braille.popWord());
	}

	/**
	 * 다음 요청이 들어왔을때 다음 문자를 전송.
	 */
	public final void next() {
		sendChar(braille.popWord());
	}

	/**
	 * 키보드 입력으로 전환.
	 * 
	 * @param brailleCode
	 *            점자 코드.
	 */
	public final void toKeyInput(final char brailleCode) {

		/* 점자를 키보드 입력 하나하나에 대응 시켜주는 코드 */
		if (brailleCode == FirstConsonent.R.toChar()
				|| brailleCode == FirstConsonent.R.toChar()) {
			robot.keyPress(KeyEvent.VK_R);
		} else if (brailleCode == FirstConsonent.RR.toChar()
				|| brailleCode == FirstConsonent.RR.toChar()) {
			robot.keyPress(KeyEvent.VK_R);
		} else if (brailleCode == FirstConsonent.S.toChar()
				|| brailleCode == FirstConsonent.S.toChar()) {
			robot.keyPress(KeyEvent.VK_S);
		} else if (brailleCode == FirstConsonent.E.toChar()
				|| brailleCode == FirstConsonent.E.toChar()) {
			robot.keyPress(KeyEvent.VK_E);
		} else if (brailleCode == FirstConsonent.EE.toChar()
				|| brailleCode == FirstConsonent.EE.toChar()) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_E);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		} else if (brailleCode == FirstConsonent.F.toChar()
				|| brailleCode == FirstConsonent.F.toChar()) {
			robot.keyPress(KeyEvent.VK_F);
		} else if (brailleCode == FirstConsonent.A.toChar()
				|| brailleCode == FirstConsonent.A.toChar()) {
			robot.keyPress(KeyEvent.VK_A);
		} else if (brailleCode == FirstConsonent.Q.toChar()
				|| brailleCode == FirstConsonent.Q.toChar()) {
			robot.keyPress(KeyEvent.VK_Q);
		} else if (brailleCode == FirstConsonent.QQ.toChar()
				|| brailleCode == FirstConsonent.QQ.toChar()) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		} else if (brailleCode == FirstConsonent.T.toChar()
				|| brailleCode == FirstConsonent.T.toChar()) {
			robot.keyPress(KeyEvent.VK_T);
		} else if (brailleCode == FirstConsonent.TT.toChar()
				|| brailleCode == FirstConsonent.TT.toChar()) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		} else if (brailleCode == FirstConsonent.D.toChar()
				|| brailleCode == FirstConsonent.D.toChar()) {
			robot.keyPress(KeyEvent.VK_D);
		} else if (brailleCode == FirstConsonent.W.toChar()
				|| brailleCode == FirstConsonent.W.toChar()) {
			robot.keyPress(KeyEvent.VK_W);
		} else if (brailleCode == FirstConsonent.WW.toChar()
				|| brailleCode == FirstConsonent.WW.toChar()) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		} else if (brailleCode == FirstConsonent.C.toChar()
				|| brailleCode == FirstConsonent.C.toChar()) {
			robot.keyPress(KeyEvent.VK_C);
		} else if (brailleCode == FirstConsonent.Z.toChar()
				|| brailleCode == FirstConsonent.Z.toChar()) {
			robot.keyPress(KeyEvent.VK_Z);
		} else if (brailleCode == FirstConsonent.X.toChar()
				|| brailleCode == FirstConsonent.X.toChar()) {
			robot.keyPress(KeyEvent.VK_X);
		} else if (brailleCode == FirstConsonent.V.toChar()
				|| brailleCode == FirstConsonent.V.toChar()) {
			robot.keyPress(KeyEvent.VK_V);
		} else if (brailleCode == FirstConsonent.G.toChar()
				|| brailleCode == FirstConsonent.G.toChar()) {
			robot.keyPress(KeyEvent.VK_G);
		} else if (brailleCode == Vowel.K.toChar()) {
			robot.keyPress(KeyEvent.VK_K);
		} else if (brailleCode == Vowel.O.toChar()) {
			robot.keyPress(KeyEvent.VK_O);
		} else if (brailleCode == Vowel.I.toChar()) {
			robot.keyPress(KeyEvent.VK_I);
		} else if (brailleCode == Vowel.OO.toChar()) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		} else if (brailleCode == Vowel.J.toChar()) {
			robot.keyPress(KeyEvent.VK_J);
		} else if (brailleCode == Vowel.P.toChar()) {
			robot.keyPress(KeyEvent.VK_P);
		} else if (brailleCode == Vowel.U.toChar()) {
			robot.keyPress(KeyEvent.VK_U);
		} else if (brailleCode == Vowel.PP.toChar()) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		} else if (brailleCode == Vowel.H.toChar()) {
			robot.keyPress(KeyEvent.VK_H);
		} else if (brailleCode == Vowel.HK.toChar()) {
			robot.keyPress(KeyEvent.VK_H);
			robot.keyPress(KeyEvent.VK_K);
		} else if (brailleCode == Vowel.HO.toChar()) {
			robot.keyPress(KeyEvent.VK_H);
			robot.keyPress(KeyEvent.VK_O);
		} else if (brailleCode == Vowel.HL.toChar()) {
			robot.keyPress(KeyEvent.VK_H);
			robot.keyPress(KeyEvent.VK_L);
		} else if (brailleCode == Vowel.Y.toChar()) {
			robot.keyPress(KeyEvent.VK_Y);
		} else if (brailleCode == Vowel.N.toChar()) {
			robot.keyPress(KeyEvent.VK_N);
		} else if (brailleCode == Vowel.NJ.toChar()) {
			robot.keyPress(KeyEvent.VK_N);
			robot.keyPress(KeyEvent.VK_J);
		} else if (brailleCode == Vowel.NP.toChar()) {
			robot.keyPress(KeyEvent.VK_P);
		} else if (brailleCode == Vowel.NL.toChar()) {
			robot.keyPress(KeyEvent.VK_N);
			robot.keyPress(KeyEvent.VK_L);
		} else if (brailleCode == Vowel.B.toChar()) {
			robot.keyPress(KeyEvent.VK_B);
		} else if (brailleCode == Vowel.M.toChar()) {
			robot.keyPress(KeyEvent.VK_M);
		} else if (brailleCode == Vowel.ML.toChar()) {
			robot.keyPress(KeyEvent.VK_M);
			robot.keyPress(KeyEvent.VK_L);
		} else if (brailleCode == Vowel.L.toChar()) {
			robot.keyPress(KeyEvent.VK_L);
		}

	}
}
