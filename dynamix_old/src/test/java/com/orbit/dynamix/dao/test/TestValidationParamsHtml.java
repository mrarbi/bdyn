package com.orbit.dynamix.dao.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestValidationParamsHtml {

	public static final int stringOccur(String text, String string) {
		return regexOccur(text, Pattern.quote(string));
	}

	public static final int regexOccur(String text, String regex) {
		Matcher matcher = Pattern.compile(regex).matcher(text);
		int occur = 0;
		while (matcher.find()) {
			occur++;
		}
		return occur;
	}

	public static void main(String[] args) {

		String text = "xxxxxxxxxxxxxxxxxxxxxxxxxx zzzzzz  key=@!cash!@ oooooooooooooooo  key=@!cash_symbol!@  nnnnnnnnn ";

		String arroInterogat = "@!";
		String interogatArro = "!@";

		int arroInt = stringOccur(text, arroInterogat);
		int intArro = stringOccur(text, interogatArro);

		if (arroInt != intArro) {

			System.out.println("No  validation");

		} else {
			System.out.println("Yes valide");
		}

	}

}
