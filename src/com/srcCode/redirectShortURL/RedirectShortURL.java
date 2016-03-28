package com.srcCode.redirectShortURL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class RedirectShortURL {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public String redirectURL(String shortURL) {
		String longURL = "";

		if(shortURL == null || shortURL.length() == 0) {
			System.out.println("\n You have passed an empty short URL");
		}

		try {
			
			shortURL = shortURL.replaceAll("^https", "http"); // replace https with http, and only at the start. As I have saved it in DB as http for all short URLS.
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/shorten_url";
			String user = "sandy";
			String password = "exact";

			connect = DriverManager.getConnection(url, user, password);
			
			if(connect != null) {
				System.out.println("\n connection established with Database");
			}
			
			else if(connect == null) {
				System.out.println("\n Could not establish connection with the database");
				return "";
			}

			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select long_url from shorten_url.url_data_table where short_url = '"+ shortURL + "'");
			
			if(!resultSet.next()) {
				System.out.println("\n You have passed a short URL that was never created");
				return longURL;
			}
			
			longURL = resultSet.getString("long_url");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return longURL;
	}

}
