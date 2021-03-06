package edu.ucsd.bioeng.coreplugin.tableImport.ui.theme;

import java.awt.Font;

public enum ImportDialogFontTheme {
	TITLE_FONT(new Font("Sans-serif", Font.BOLD, 18)),
	SELECTED_COL_FONT(new Font("Sans-serif", Font.BOLD, 14)),
	SELECTED_FONT(new Font("Sans-serif", Font.BOLD, 14)),
	UNSELECTED_FONT(new Font("Sans-serif",Font.PLAIN, 14)),
	KEY_FONT(new Font("Sans-Serif", Font.BOLD, 14)), 
	LABEL_FONT(new Font("Sans-serif", Font.BOLD, 14)),
	LABEL_ITALIC_FONT(new Font("Sans-serif", 3, 14)),
	ITEM_FONT(new Font("Sans-serif", Font.BOLD, 12)),
	ITEM_FONT_LARGE(new Font("Sans-serif", Font.BOLD, 14));
	
	private Font font;
	
	private ImportDialogFontTheme(Font font) {
		this.font = font;
	}
	
	public Font getFont() {
		return font;
	}
}
