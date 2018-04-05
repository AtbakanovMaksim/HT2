package app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.DBWorker;

public class Phone {
	
	// Данные записи о телефоне.
	private String id = "";
	private String owner = "";
	private String number = "";

	// Конструктор для создания записи о номере на основе данных из БД. 
	public Phone(String id, String owner, String number)
	{
		this.id = id;
		this.owner = owner;
		this.number = number;
	}
	
	// Конструктор для создания пустой записи.
	public Phone()
	{
		this.id  = "";
		this.owner  = "";
		this.number = "";
	}	

	// Конструктор для создания записи, предназначенной для добавления в БД. 
	public Phone(String owner, String number)
	{
		this.id = "0";
		this.owner = owner;
		this.number = number;
	}

	// Валидация номера. Для отчества можно передать второй параетр == true,
	// тогда допускается пустое значение.
	public String validatePhone(String number)
	{
	    	Matcher matcher = Pattern.compile("[0-9]\\+\\-\\#{2,50}").matcher(number);
	    	if(!matcher.matches()) {
	    		return "Номер телефона должен быть от 2 до 50 символов: цифра, +, -, #.";
	    	} else {
	    		return "РЕГУЛЯРКА";
	    	}
	}
	
	// ++++++++++++++++++++++++++++++++++++++
	// Геттеры и сеттеры

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	// Геттеры и сеттеры
	// --------------------------------------
}
