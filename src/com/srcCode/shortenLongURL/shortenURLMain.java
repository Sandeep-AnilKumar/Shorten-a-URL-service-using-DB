package com.srcCode.shortenLongURL;

import java.util.Scanner;
import com.srcCode.shortenLongURL.ShortenURL;

public class shortenURLMain {

	public static void main(String[] args) {

		System.out.println("Enter the long URL that you want to shorten");

		Scanner in = new Scanner(System.in);
		String longURL = in.next();

		ShortenURL dao = new ShortenURL();
		String shortURL = dao.createShortURL(longURL);

		if(shortURL == null || shortURL.length() == 0) {
			System.out.println("\n The short URL could not be generated");
		}

		else {
			System.out.println("\n The short URL generated is: " + "http://san.dy/" + shortURL);
		}
	}
}
