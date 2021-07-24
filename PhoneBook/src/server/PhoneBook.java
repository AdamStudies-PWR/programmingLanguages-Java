/*
 * Program: przechowywanie danych ksi¹¿ki telfonicznej z programu PhoneBookServer
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
		if(name == " " || name == null || name=="" || number == " " || number == null || number=="") return "ERROR b³¹d komendy put";
		if(phoneBookMap.get(name) != null) return "ERROR numer ju¿ znajduje siê w bazie dancyh";
		if(number.length() !=9) return "ERROR numer nale¿y wprowadzaæ bez spacji i pwoinien zawieraæ 9 cyfr";
		for(int i=0; i<number.length(); i++)
		{
			if(!(47<(int)number.charAt(i) && (int)number.charAt(i)<58)) return "ERROR wszytkie znaki numeru musz¹ byæ liczb¹";
		}
		phoneBookMap.put(name, number);
		return "OK";
	}
	public String REPLACE(String name, String number)
	{
		if(name == " " || name == null || name=="" || number == " " || number == null || number=="") return "ERROR b³¹d komendy replace";
		if(number.length() !=9) return "ERROR numer nale¿y wprowadzaæ bez spacji i pwoinien zawieraæ 9 cyfr";
		for(int i=0; i<number.length(); i++)
		{
			if(!(47<(int)number.charAt(i) && (int)number.charAt(i)<58)) return "ERROR wszytkie znaki numeru musz¹ byæ liczb¹";
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
			return "ERROR b³¹d zapisu";
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
			return "ERROR b³¹d odczytu";
		}
		return "OK";
	}
}

