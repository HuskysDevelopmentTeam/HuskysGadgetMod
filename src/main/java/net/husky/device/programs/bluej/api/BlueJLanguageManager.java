package net.husky.device.programs.bluej.api;

import java.util.HashMap;

public class BlueJLanguageManager {
	
	private static HashMap<String, SyntaxHighlighter> highlighters = new HashMap<>();
	
	public static void addHighlighter(SyntaxHighlighter highlighter) {
		highlighters.put(highlighter.getName(), highlighter);
	}
	
	public static HashMap<String, SyntaxHighlighter> getHighlighters() {
		return highlighters;
	}
	
}
