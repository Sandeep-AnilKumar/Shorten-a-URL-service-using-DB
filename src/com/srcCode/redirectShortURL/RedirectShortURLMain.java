package com.srcCode.redirectShortURL;

import java.util.Scanner;
import com.srcCode.redirectShortURL.RedirectShortURL;

public class RedirectShortURLMain {

	public static void main(String[] args) {

		System.out.println("Enter the short URL that you want to redirect");

		Scanner in = new Scanner(System.in);
		String shortURL = in.next();

		RedirectShortURL dao = new RedirectShortURL();
		String longURL = dao.redirectURL(shortURL);

		if(longURL == null || longURL.length() == 0) {
			System.out.println("\n The short URL could not be redirected");
		}

		else {
			System.out.println("\n The long URL to be redirected is -> " + longURL);
		}
	}
}
