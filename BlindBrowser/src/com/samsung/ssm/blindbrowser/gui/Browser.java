package com.samsung.ssm.blindbrowser.gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;

import com.samsung.ssm.blindbrowser.device.DeviceControl;
import com.samsung.ssm.blindparser.BlindParser;

/**
 * 
 * JFrame기반으로 GUI 구성.
 * 
 * @author HoonKim
 * 
 */
public class Browser extends JFrame {

	/**
	 * 
	 * 장치로부터 글자를 받음.
	 * 
	 * @return char 장치로부터 받은 글자.
	 */
	final native char receiveString();

	/**
	 * 
	 * 장치를 오픈함.
	 * 
	 * @return int 장치 포인터.
	 */
	final native int open();

	/**
	 * 기본 브라우저 창 너비.
	 */
	private static final int DEFAULT_WINDOW_WIDTH = 1024;

	/**
	 * 기본 브라우저 창 높이.
	 */
	private static final int DEAFULT_WINDOW_HEIGHT = 768;

	/**
	 * 자동 부여된 serialVersionUID.
	 */
	private static final long serialVersionUID = 2288458966818812508L;

	/**
	 * 브라우저 전체 패널.
	 */
	private JPanel panel;

	/**
	 * 링크, 텍스트 테이블.
	 */
	private JTable table;

	/**
	 * 주소입력창.
	 */
	private JTextField urlField;

	/**
	 * 링크 실제 이동할 URL.
	 */
	private ArrayList<String> linkUrls;

	/**
	 * 콘테이너.
	 */
	private Container container;

	/**
	 * 파서.
	 */
	private BlindParser parser;

	/**
	 * 장치 컨트롤러.
	 */
	private DeviceControl deviceControl;

	/**
	 * 윈도우 생성.
	 * 
	 * @param url
	 *            불러올 페이지 URL.
	 */
	public Browser(final String url) {
		try {
			deviceControl = new DeviceControl();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		panel = null;

		setSize(DEFAULT_WINDOW_WIDTH, DEAFULT_WINDOW_HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		loadPage(url);

	}

	/**
	 * @author HoonKim
	 * 
	 *         Swing 테이블 구성을 위한 테이블 데이터 모델.
	 * 
	 */
	private class TextDataModel extends AbstractTableModel {

		/**
		 * 자동생성된 serialVersionUID.
		 */
		private static final long serialVersionUID = -4967006974659183218L;

		/**
		 * 텍스트들.
		 */
		private ArrayList<String> texts;

		/**
		 * 링크들.
		 */
		private ArrayList<String> links;

		/**
		 * 테이블에 들어갈 테이터의 추상모델. 테이블에 표시할 텍스트들을 인자로 넘깁니다.
		 * 
		 * @param textData
		 *            표시할 텍스트 데이터.
		 * 
		 * @param linkData
		 *            표시할 링크 데이터.
		 */
		public TextDataModel(final ArrayList<String> textData,
				final ArrayList<String> linkData) {
			texts = textData;
			links = linkData;
		}

		@Override
		public int getColumnCount() {
			return 2; // 우린 무조건 가로 2칸짜리 테이블.
		}

		@Override
		public int getRowCount() {
			if (texts.size() > links.size()) {
				return texts.size();
			} else {
				return links.size();
			}
		}

		@Override
		public String getValueAt(final int rowIndex, final int columnIndex) {
			/* 0번 Column이 링크 목록, 1번 Column이 그냥 텍스트 목록. */
			if (columnIndex == 0) {
				if (rowIndex < links.size()) { // Null 포인터 에러 방지.
					return links.get(rowIndex);
				} else {
					return null;
				}

			} else {
				if (rowIndex < texts.size()) { // Null 포인터 에러 방지.
					return texts.get(rowIndex);
				} else {
					return null;
				}
			}
		}

		@Override
		public String getColumnName(final int index) {
			if (index == 0) {
				/* 위에 컬럼 이름. */
				return "Link";
			} else {
				return "Texts";
			}
		}
	}

	/**
	 * URL을 기준으로 페이지를 로드합니다.
	 * 
	 * @param url
	 *            로드할 URL.
	 */
	private void loadPage(final String url) {

		/* 페이지 이동 등을 위해 전체를 깨끗하게 지우고 갱신해 줍니다. */
		container = getContentPane();
		container.removeAll();
		validate();
		revalidate();
		repaint();

		/* URL에 해당하는 페이지를 불러옴. */
		parser = new BlindParser(url);
		linkUrls = parser.getLinkUrls();

		/* 로드된 페이지 제목 설정 */
		setTitle(parser.getTitle());
		JPanel panel2 = new JPanel(new BorderLayout());

		/* 주소 입력창 추가 및 설정 */
		urlField = new JTextField(parser.getURL());
		panel2.add(urlField, BorderLayout.NORTH);

		/* 테이블 초기화 및 관련 설정들 */
		TextDataModel data = new TextDataModel(parser.getTexts(),
				parser.getLinkTexts());
		table = new JTable(data);
		table.setRowSelectionAllowed(false);

		/* 테이블에서 Tab키 기본기능 막기. */
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "none");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_TAB,
						InputEvent.SHIFT_DOWN_MASK), "none");

		/* 테이블 위에서 엔터키 기본 기능 막기. */
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "none");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
						InputEvent.SHIFT_DOWN_MASK), "none");

		panel2.add(table, BorderLayout.WEST);
		panel2.add(new JScrollPane(table));

		/* 주소 입력창 키 설정. */
		urlField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(final KeyEvent e) {
			}

			@Override
			public void keyReleased(final KeyEvent e) {
			}

			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					table.changeSelection(1, 1, true, false);
					table.requestFocus();

				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// 엔터 쳤을때 구현. 입력된 주소로 넘어감.
					loadPage(urlField.getText());

				}
			}
		});

		/* 페이지 키설정. */
		table.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(final KeyEvent e) {

			}

			@Override
			public void keyReleased(final KeyEvent e) {

			}

			@Override
			public void keyPressed(final KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_TAB) {

					/* Tab 키 주소창으로 이동. */
					urlField.requestFocus();
					urlField.selectAll();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					/* 스페이스 키. 장치로 연결되어 있음 */
					deviceControl.send(table
							.getModel()
							.getValueAt(table.getSelectedRow(),
									table.getSelectedColumn()).toString());

				} else if (e.getKeyCode() == KeyEvent.VK_ENTER
						&& table.getSelectedColumn() == 0) {

					/* 엔터키. 해당 링크로 이동. */
					loadPage(linkUrls.get(table.getSelectedRow()));
				} else if (e.getKeyCode() == KeyEvent.VK_L) {
					/* 다음 하이라이트 */
					table.changeSelection(
							parser.getNextHighlight(table.getSelectedRow()), 0,
							false, false);
				} else if (e.getKeyCode() == KeyEvent.VK_K) {
					/* 이전 하이라이트 */
					table.changeSelection(
							parser.getPreviousHighlight(table.getSelectedRow()),
							0, false, false);
				}
			}
		});

		add(panel2);
		validate();
		table.requestFocus();
		repaint();

		// TODO 페이지 로드 후에 커서가 주소창 or 페이지 둘중하나에 가있게 해야 함.

	}
}
