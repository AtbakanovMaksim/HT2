package app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.DBWorker;

public class Phone {
	
	// ������ ������ � ��������.
	private String id = "";
	private String owner = "";
	private String number = "";

	// ����������� ��� �������� ������ � ������ �� ������ ������ �� ��. 
	public Phone(String id, String owner, String number)
	{
		this.id = id;
		this.owner = owner;
		this.number = number;
	}
	
	// ����������� ��� �������� ������ ������.
	public Phone()
	{
		this.id  = "";
		this.owner  = "";
		this.number = "";
	}	

	// ����������� ��� �������� ������, ��������������� ��� ���������� � ��. 
	public Phone(String owner, String number)
	{
		this.id = "0";
		this.owner = owner;
		this.number = number;
	}

	// ��������� ������. ��� �������� ����� �������� ������ ������� == true,
	// ����� ����������� ������ ��������.
	public String validatePhone(String number)
	{
	    	Matcher matcher = Pattern.compile("[0-9]\\+\\-\\#{2,50}").matcher(number);
	    	if(!matcher.matches()) {
	    		return "����� �������� ������ ���� �� 2 �� 50 ��������: �����, +, -, #.";
	    	} else {
	    		return "���������";
	    	}
	}
	
	// ++++++++++++++++++++++++++++++++++++++
	// ������� � �������

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

	// ������� � �������
	// --------------------------------------
}
