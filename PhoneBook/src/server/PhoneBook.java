/*
 * Program: przechowywanie danych ksi��ki telfonicznej z programu PhoneBookServer
 * Plik PhoneBook.java
 * Autor Adam Krizar
 * Data 27 grudnia 2018
 */
package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class PhoneBook 
{
	private ConcurrentHashMap<String, String> phoneBookMap;
	
	public PhoneBook()
	{
		phoneBookMap = new ConcurrentHashMap<String, String>();
	}
	
	public String GET(String name)
	{
		String number = phoneBookMap.get(name);
		if(number == null) return "ERROR brak numeru w bazie danych";
		else return "OK " + number;
	}
	public String PUT(String name, String number)
	{
		if(name == " " || name == null || name=="" || number == " " || number == null || number=="") return "ERROR b��d komendy put";
		if(phoneBookMap.get(name) != null) return "ERROR numer ju� znajduje si� w bazie dancyh";
		if(number.length() !=9) return "ERROR numer nale�y wprowadza� bez spacji i pwoinien zawiera� 9 cyfr";
		for(int i=0; i<number.length(); i++)
		{
			if(!(47<(int)number.charAt(i) && (int)number.charAt(i)<58)) return "ERROR wszytkie znaki numeru musz� by� liczb�";
		}
		phoneBookMap.put(name, number);
		return "OK";
	}
	public String REPLACE(String name, String number)
	{
		if(name == " " || name == null || name=="" || number == " " || number == null || number=="") return "ERROR b��d komendy replace";
		if(number.length() !=9) return "ERROR numer nale�y wprowadza� bez spacji i pwoinien zawiera� 9 cyfr";
		for(int i=0; i<number.length(); i++)
		{
			if(!(47<(int)number.charAt(i) && (int)number.charAt(i)<58)) return "ERROR wszytkie znaki numeru musz� by� liczb�";
		}
		phoneBookMap.put(name, number);
		return "OK";
	}
	public String DELETE(String name)
	{
		if(phoneBookMap.get(name) == null) return "ERROR brak numeru w bazie dancyh";
		phoneBookMap.remove(name);
		return "OK";
	}
	public String LIST()
	{
		if(phoneBookMap.isEmpty()) return "ERROR baza dancyh jest pusta";
		return "OK " + phoneBookMap.toString();
	}
	public String SAVE(String filename)
	{
		try
		(
			FileOutputStream writer = new FileOutputStream(filename);
			ObjectOutputStream obj = new ObjectOutputStream(writer)
		)
		{
			obj.writeObject(phoneBookMap);
		} 
		catch (Exception error) 
		{
			return "ERROR b��d zapisu";
		}
		return "OK";
	}
	public String LOAD(String filename)
	{
		try
		(
			FileInputStream reader = new FileInputStream(filename);
			ObjectInputStream obj = new ObjectInputStream(reader)
		)
		{
			phoneBookMap = (ConcurrentHashMap<String, String>) obj.readObject();
		} 
		catch (Exception error) 
		{
			return "ERROR b��d odczytu";
		}
		return "OK";
	}
}

