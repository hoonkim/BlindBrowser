package com.samsung.ssm.blindparser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 주어진 링크를 파싱해서 가지고 있는 클래스 .
 * 
 * @author HoonKim
 * 
 */
public class BlindParser {

	/**
	 * 파싱된 페이지 내용.
	 */
	private Document page = null;

	/**
	 * 링크 제목.
	 */
	private ArrayList<String> linkTexts;

	/**
	 * 링크 주소.
	 */
	private ArrayList<String> linkUrls;

	/**
	 * 텍스트 리스트.
	 */
	private ArrayList<String> texts;

	/**
	 * h1, h2등으로 하이라이트로 표시된 부분을 저장합니다. - 링크 기준
	 */
	private ArrayList<Integer> linkHighlight;

	// /**
	// * h1, h2등으로 하이라이트로 표시된 부분을 저장합니다. - 텍스트 기준
	// */
	// private ArrayList<Integer> textHighlight;

	/**
	 * 주어진 URL을 파싱하여 링크, 텍스트를 분리해 줍니다..
	 * 
	 * @param url
	 *            URL to connect
	 */
	public BlindParser(final String url) {

		/* 자료형들 초기화. */
		linkTexts = new ArrayList<String>();
		linkUrls = new ArrayList<String>();
		texts = new ArrayList<String>();
		linkHighlight = new ArrayList<Integer>();
		// textHighlight = new ArrayList<Integer>();

		try {
			/* 주어진 URL로 부터 HTTP GET Response를 받아와 저장. */
			if (!url.startsWith("http")) {
				page = Jsoup.connect("http://" + url).userAgent("Mozilla")
						.get();
			} else {
				page = Jsoup.connect(url).userAgent("Mozilla").get();
			}
			
			/* 링크만 파싱함. */
			for (Element e : page.getAllElements()) {
				if (e.attr("href").startsWith("http")// @formatter:off
						&& e.text().length() > 1) { // @formatter:on
					linkTexts.add(e.text());
					linkUrls.add(e.attr("href"));

				}
				if (e.html().startsWith("<h1") || e.html().startsWith("<h2")
						|| e.html().startsWith("<h3")
						|| e.html().startsWith("<h4")) {
					linkHighlight.add(linkTexts.size());
				}

			}
			/* 링크 아닌 텍스트만 파싱함. */
			page.select("legend").unwrap();
			page.select("button").remove();
			page.select("br").remove();
			page.select("span").unwrap();
			page.select("a").remove();
			page.select("p").unwrap();
			page.select("input").remove();
			page.select("select").remove();

			for (Element e : page.getAllElements()) {
				if (e.text().length() > 1) {
					if (e.parent() != null && !e.hasAttr("href")) {
						if (e.childNodeSize() <= 2) {
							texts.add(e.text());
						}
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 파싱된 페이지의 제목(title)을 리턴함.
	 * 
	 * @return 페이지 제목.
	 */
	public final String getTitle() {
		return page.title();
	}

	/**
	 * 파싱된 페이지의 URL을 리턴함.
	 * 
	 * @return 페이지 URL
	 */
	public final String getURL() {
		return page.baseUri();
	}

	/**
	 * 링크들 목록을 스트링으로 리턴함.
	 * 
	 * @return 링크목록을 리턴함.
	 */
	public final ArrayList<String> getLinkTexts() {
		return linkTexts;
	}

	/**
	 * 링크 URL들을 스트링형태로 리턴함.
	 * 
	 * @return 링크 URL들.
	 */
	public final ArrayList<String> getLinkUrls() {
		return linkUrls;
	}

	/**
	 * 전체 텍스트를 리턴함.
	 * 
	 * @return 전체 텍스트들.
	 */
	public final ArrayList<String> getTexts() {

		return texts;

	}

	/**
	 * 
	 * 다음 중요 링크 목차.
	 * 
	 * @param row
	 *            현재 선택된 줄.
	 * 
	 * @return next important row.
	 */
	public final int getNextHighlight(final int row) {
		for (int highlight : linkHighlight) {
			if (highlight == row) {
				continue;
			} else if (highlight > row) {
				return highlight;
			}
		}
		return row;
	}

	/**
	 * 
	 * 이전 중요 링크 목차.
	 * 
	 * @param row
	 *            현재 선택된 줄.
	 * 
	 * @return previous important row
	 */
	public final int getPreviousHighlight(final int row) {
		int prev = 0;
		for (int highlight : linkHighlight) {
			if (highlight >= row) {
				return prev;
			}
			prev = highlight;
		}
		return row;
	}

}
