package com.leena.util;

import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class Util {
	// string 값이 들어오면 int 타입인지 확인해보는 메소드
	// 127 -> true
	// 1a2e4 -> false
	// public -> 모든 곳에서 써
	// static -> 객체 생성하지 말고 써

	public static int str2Int(String str) {
		// A59 -> 59
		// 5A9 -> 59
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				sb.append(str.charAt(i));
			}
		}
		int number = 0;
		if (sb.length() > 0) {
			number = Integer.parseInt(sb.toString());
		}
		System.out.println("변환된 숫자" + number);
		return number;
	}

	public static int str2Int2(String str) {
		// A59 -> 59
		// 5A9 -> 59
		String numberOnly = str.replaceAll("[^0-9]", "");
		return Integer.parseInt(numberOnly);
	}

	public static boolean intCheck(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean intCheck2(String str) {
		boolean result = true;

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				result = false;
				break;
			}
		}

		return result;
	}

	// 01.23 ip 얻어오기
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	// 01.23 html태그를 특수기호로 인식하게 만들기.
	// [<] -> &lt, [>] -> &gt
	public static String removeTag(String str) {
		str = str.replaceAll("<", "&lt");
		str = str.replaceAll(">", "&gt");
		return str;
	}

	// 01.23 엔터키 처리
	public static String addBR(String str) {
		return str.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
	}

	// 01.23 아이피 가리기 172.30.1.27 -> 172.★.1.27
	public static String ipMasking(String ip) {
		if (ip.indexOf('.') != -1) { // ip가 아닐때
			StringBuffer sb = new StringBuffer(); // 멀티스레드 환경에서도 동기화 지원
			sb.append(ip.substring(0, ip.indexOf('.') + 1));
			sb.append("★");
			sb.append(ip.substring(ip.indexOf('.', ip.indexOf('.') + 1)));
			return sb.toString();
		} else {
			return ip;
		}
		/*
		 * String[] parts = ip
		 * 
		 * // 두 번째 "." 이후의 숫자를 "*"로 대체 String maskedIPAddress = ipMarking(ipAddress);
		 * 
		 * System.out.println("Original IP Address: " + ipAddress);
		 * System.out.println("Masked IP Address: " + maskedIPAddress);
		 */
	}
}
