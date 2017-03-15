package com.lfc.wxadminweb.common.utils;

public class StringUtilLY {
	
	public static String replaceHTMLEntity(String originalStr){
		originalStr = originalStr.replaceAll("amp;", "");
		originalStr = originalStr.replaceAll("&lt;", "<");
		originalStr = originalStr.replaceAll("&gt;", ">");
		originalStr = originalStr.replaceAll("&quot;", "\"");
		originalStr = originalStr.replaceAll("&nbsp;", " ");
		originalStr = originalStr.replaceAll("&ldquo;", "“");
		originalStr = originalStr.replaceAll("&rdquo;", "”");
		originalStr = originalStr.replaceAll("&middot;", "·");
		originalStr = originalStr.replaceAll("&amp;nbsp;", " ");
		originalStr = originalStr.replaceAll("&amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;amp;rdquo;", "”");
		originalStr = originalStr.replaceAll("&amp;rdquo;", "”");
		return originalStr;
	}
}
