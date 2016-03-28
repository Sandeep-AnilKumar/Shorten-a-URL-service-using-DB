package com.srcCode.shortenLongURL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class ShortenURL {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public String createShortURL(String longURL) {
		String shortURL = "";
		try {

			if(longURL == null || longURL.length() == 0) {
				System.out.println("\n You have passed an empty URL");
			}

			longURL = longURL.replaceAll("^http(s)?://(www.)?",""); // Remove http:// or https:// & only at the start.

			String[] parts = longURL.split("/");
			StringBuffer sb = new StringBuffer();

			for(int j = 1; j < parts.length; j++) {
				sb.append(parts[j]); //Apart from the domain, all other parts are combined, whose checksum will be calculated.
			}

			byte bytes[] = sb.toString().getBytes();
			long checksumValue = 0L;

			Checksum checksum = new CRC32();
			checksum.update(bytes, 0, bytes.length);
			checksumValue = checksum.getValue();

			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/shorten_url";
			String user = "sandy";
			String password = "exact";

			connect = DriverManager.getConnection(url, user, password);

			if(connect != null) {
				System.out.println("\n connected to the database");
			}
			
			else if(connect == null) {
				System.out.println("\n Could not establish connection to Database");
				return "";
			}

			statement = connect.createStatement();
			resultSet = statement.executeQuery("select short_url from shorten_url.url_data_table where checksum_value = " + checksumValue);
			boolean test = false;

			if(test = resultSet.next()) {
				System.out.println("\n A short URL was already generated before for this path, checking if it was registered for the same domain . . .");

				String domain = parts[0];

				String previousParts[];
				String previousDomain = "";

				while(!previousDomain.equals(domain) && test) {

					statement = connect.createStatement();
					resultSet = statement.executeQuery("select * from shorten_url.url_data_table where checksum_value = " + checksumValue);

					if(test = resultSet.next()) {

						shortURL = resultSet.getString("short_url");
						previousParts = resultSet.getString("long_url").split("/");
						previousDomain = previousParts[0];
						checksumValue = resultSet.getLong("checksum_value");

						if(!previousDomain.equals(domain)) {
							checksumValue += 1;
						}
					}
				}

				if(! test) {

					shortURL = getPathFromChecksumValue(checksumValue);

					preparedStatement = connect.prepareStatement("insert into shorten_url.url_data_table values (?, ?, ?)");

					preparedStatement.setLong(1 , checksumValue);
					preparedStatement.setString(2, "http://san.dy/" + shortURL);
					preparedStatement.setString(3, "http://" + longURL);

					preparedStatement.executeUpdate();
				}
				else {
					System.out.println("\n A short URL was already registered for this path, with the same domain");
					parts = shortURL.replace("http://", "").split("/");
					shortURL = parts[1];
				}

				return shortURL;
			}

			else {
				shortURL = getPathFromChecksumValue(checksumValue);

				preparedStatement = connect.prepareStatement("insert into shorten_url.url_data_table values (?, ?, ?)");

				preparedStatement.setLong(1 , checksumValue);
				preparedStatement.setString(2, "http://san.dy/" + shortURL);
				preparedStatement.setString(3, "http://" + longURL);

				preparedStatement.executeUpdate();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		finally{
			try{
				if(resultSet != null) {
					resultSet.close();
				}
				if(statement != null) {
					statement.close();
				}
				if(preparedStatement != null) {
					preparedStatement.close();
				}
				if(connect != null) {
					connect.close();
				}
			}
			catch(Exception e) {

			}
		}
		return shortURL;
	}

	public String getPathFromChecksumValue(long checksumValue) {

		if(checksumValue == 0) {
			return "";
		}

		String shortURL = "";
		long temp = 0L;
		long remainder = 0L;
		List<Long> values = new ArrayList<Long>();
		long currentLong = 0L;
		StringBuffer sb = new StringBuffer();
		char c;

		System.out.println("\n This long URL was not shortened before.");
		temp = checksumValue;

		while(temp > 0) {
			remainder = temp % 100;
			values.add(remainder); //Breaking the checksum values into 2 digit numbers.
			temp /= 100;
		}

		sb = new StringBuffer();

		for(Iterator<Long> k = values.iterator(); k.hasNext();) { //for each 2 digit number.
			currentLong = k.next();

			if(currentLong <= 51) { // see if it is below 51. 0 -> 0, 1-26 -> a-z, 27-51 -> A-Z is assigned.
				if(currentLong <= 26) {
					if(currentLong == 0) {
						c = '0';
					}
					else {
						c = (char) (96L + currentLong);
					}
					sb.append(c);
				}
				else {
					currentLong -= 26;
					c = (char) (64L + currentLong);
					sb.append(c);
				}

			}

			else {
				temp = currentLong; // if the 2 digit number is > 51.

				while(temp > 0) {
					remainder = temp % 10; //break it into two 1 digit numbers and assign a lower case letter.
					if(remainder == 0) {
						c = '0';
					}
					else {
						c = (char) (remainder + 64L);
					}
					sb.append(c);
					temp /= 10;
				}
			}
		}
		shortURL = sb.toString();
		return shortURL;
	}
}
