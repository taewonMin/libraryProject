package common;

import java.util.regex.Pattern;

public class RegExClass {
	// 5~15자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.
	public static boolean checkMem_id(String mem_id) {
		String patternMId = "^[a-z0-9_-]{5,15}$";
		return Pattern.matches(patternMId, mem_id);
	}

	// 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.
	public static boolean checkMem_pw(String mem_pw) {
		String patternMPw = "\\S{8,16}$";
		return Pattern.matches(patternMPw, mem_pw);
	}

	// 2~5자 한글만 사용가능합니다. (특수기호, 공백 사용 불가)
	public static boolean checkMem_name(String mem_name) {
		String patternMName = "[가-힣]{2,4}";
		return Pattern.matches(patternMName, mem_name);
	}

	// 올바른 형식의 이메일이 아닙니다. (영문자로 시작, 특수기호 (-,_,\,.) 사용가능)
	public static boolean checkMem_email(String mem_email) {
		String patternMEmail = "[A-Za-z][\\w-_.]*@\\w{1,7}[.][A-Za-z]{2,3}(.kr)?";
		return Pattern.matches(patternMEmail, mem_email);
	}

	// 올바른 형식의 생년월일이 아닙니다. (예-1990-01-01)
	public static boolean checkMem_bir(String mem_bir) {
		String patternMBir = "(1\\d{3}|20[0-1]\\d)-(0[1-9]|1[0-2])-(0[1-9]|([1-2]\\d)|3[0-1])";
		return Pattern.matches(patternMBir, mem_bir);

	}

	// 올바른 형식의 전화번호가 아닙니다. (예-010-1111-1111)
	public static boolean checkMem_tel(String mem_number) {
		String patternMTel = "0[0-1]{2}-[0-9]{3,4}-[0-9]{3,4}";
		return Pattern.matches(patternMTel, mem_number);
	}

	public static boolean checkScanCID(String scanCID) {
		String patternScanCID = "\\d{1,2}";// 숫자1,2자리 1~
		return Pattern.matches(patternScanCID, scanCID);
	}

}
