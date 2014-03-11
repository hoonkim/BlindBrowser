package com.samsung.ssm.blindbrowser;

import com.samsung.ssm.blindbrowser.gui.Browser;

/**
 * @author HoonKim
 * 
 */
public final class BlindBrowser {

	/**
	 * Constructor for preventing extension.
	 */
	private BlindBrowser() {
	}

	/**
	 * Main.
	 * 
	 * @param args
	 *            parameter for CLI.
	 */
	public static void main(final String[] args) {
		new Browser("http://www.naver.com");
	}

}
