package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBWorker {
	
	// ���������� ����� �������, ���������� ��������� ��������.
	private Integer affected_rows = 0;
	
	// �������� ��������������������� ���������� �����, ���������� �����
	// ���������� ����� ������.
	private Integer last_insert_id = 0;

	// ��������� �� ��������� ������.
	private static DBWorker instance = null;
	
	// ����� ��� ��������� ���������� ������ (���������� Singleton).
	public static DBWorker getInstance()
	{
		if (instance == null)
		{
			instance = new DBWorker();
		}
	
		return instance;
	}
	
	// "��������", ����� ��������� ������ ������ ���� �������� ��������.
	private DBWorker()
	{
	 // ������ "��������".			
	}
	
	private Connection connect = MySQLConnUtils.getMySQLConnection();
	
	// ���������� �������� �� ������� ������.
	public ResultSet getDBData(String query)
	{
		Statement statement;
		try
		{
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return resultSet;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("null on getDBData()!");
		return null;

	}
	
	// ���������� �������� �� ����������� ������.
	public Integer changeDBData(String query)
	{
		Statement statement;
		try
		{
			statement = connect.createStatement();
			this.affected_rows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		
			// �������� last_insert_id() ��� �������� �������.
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
            	this.last_insert_id = rs.getInt(1);
            }
			
			return this.affected_rows;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("null on changeDBData()!");
		return null;
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++
	// ������� � �������.
	public Integer getAffectedRowsCount()
	{
		return this.affected_rows;
	}
	
	public Integer getLastInsertId()
	{
		return this.last_insert_id;
	}
	// ������� � �������.
	// -------------------------------------------------
}

