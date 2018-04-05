package app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ManagePersonServlet extends HttpServlet {
	
	// ������������� ��� ������������/��������������.
	private static final long serialVersionUID = 1L;
	
	// �������� ������, �������� ������ ���������� �����.
	private Phonebook phonebook;
       
    public ManagePersonServlet()
    {
        // ����� ������������� ������������.
    	super();
		
    	// �������� ���������� ���������� �����.
        try
		{
			this.phonebook = Phonebook.getInstance();

		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}        
        
    }

    // ��������� ��� � ��������� ��������� �� ������ � ������ ���������� ������.
    private String validatePersonFMLName(Person person)
    {
		String error_message = "";
		
		if (!person.validateFMLNamePart(person.getName(), false))
		{
			error_message += "��� ������ ���� ������� �� 1 �� 150 �������� �� ����, ����, ������ ������������� � ������ �����.<br />";
		}
		
		if (!person.validateFMLNamePart(person.getSurname(), false))
		{
			error_message += "������� ������ ���� ������� �� 1 �� 150 �������� �� ����, ����, ������ ������������� � ������ �����.<br />";
		}
		
		if (!person.validateFMLNamePart(person.getMiddlename(), true))
		{
			error_message += "�������� ������ ���� ������� �� 0 �� 150 �������� �� ����, ����, ������ ������������� � ������ �����.<br />";
		}
		
		return error_message;
    }
    
    // ������� �� GET-�������.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// ����������� �� ��������� � ������ ��������� ����� ������������� � UTF-8,
		// ����� ������� ���� ��� �������� GET/POST-����������� ������������ � "����������".
		request.setCharacterEncoding("UTF-8");
		
		// � JSP ��� ����������� ���� ���������� �����. ����� ������� � ��������� ���,
		// �� � ������������� ����� ������ �������� ������� ��� � �������� � �������� � JSP.
		request.setAttribute("phonebook", this.phonebook);
		
		// ��������� ���������� ��� �������� � JSP.
		HashMap<String,String> jsp_parameters = new HashMap<String,String>();

		// ���������� ��� �������� ���������� �� ������ JSP (������ ������������� (view)).
		RequestDispatcher dispatcher_for_manager = request.getRequestDispatcher("/ManagePerson.jsp");
        RequestDispatcher dispatcher_for_list = request.getRequestDispatcher("/List.jsp");

		// �������� (action) � ������������� ������ (id) ��� ������� ����������� ��� ��������.
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		
		// ���� ������������� � �������� �� �������, �� ��������� � ���������
		// "������ �������� ������ � ������ ������ �� ������".
        if ((action == null)&&(id == null))
        {
        	request.setAttribute("jsp_parameters", jsp_parameters);
            dispatcher_for_list.forward(request, response);
        }
        // ���� �� �������� �������, ��...
        else
        {
        	switch (action)
        	{
        		// ���������� ������.
        		case "add":
        			// �������� ����� ������ ������ � ������������.
        			Person empty_person = new Person();
        			
        			// ���������� ���������� ��� JSP.
        			jsp_parameters.put("current_action", "add");
        			jsp_parameters.put("next_action", "add_go");
        			jsp_parameters.put("next_action_label", "��������");
        			
        			// ��������� ���������� JSP.
        			request.setAttribute("person", empty_person);
        			request.setAttribute("jsp_parameters", jsp_parameters);
        			
        			// �������� ������� � JSP.
        			dispatcher_for_manager.forward(request, response);
        		break;
			
        		// �������������� ������.
        		case "edit":
        			// ���������� �� ���������� ����� ���������� � ������������� ������.        			
        			Person editable_person = this.phonebook.getPerson(id);
        			
        			// ���������� ���������� ��� JSP.
        			jsp_parameters.put("current_action", "edit");
        			jsp_parameters.put("next_action", "edit_go");
        			jsp_parameters.put("next_action_label", "���������");

        			// ��������� ���������� JSP.
        			request.setAttribute("person", editable_person);
        			request.setAttribute("jsp_parameters", jsp_parameters);
        			
        			// �������� ������� � JSP.
        			dispatcher_for_manager.forward(request, response);
        		break;
			
        		// �������� ������.
        		case "delete":
        			
        			// ���� ������ ������� �������...
        			if (phonebook.deletePerson(id))
        			{
        				jsp_parameters.put("current_action_result", "DELETION_SUCCESS");
        				jsp_parameters.put("current_action_result_label", "�������� ��������� �������");
        			}
        			// ���� ������ �� ������� ������� (��������, ����� ������ ���)...
        			else
        			{
        				jsp_parameters.put("current_action_result", "DELETION_FAILURE");
        				jsp_parameters.put("current_action_result_label", "������ �������� (��������, ������ �� �������)");
        			}

        			// ��������� ���������� JSP.
        			request.setAttribute("jsp_parameters", jsp_parameters);
        			
        			// �������� ������� � JSP.
        			dispatcher_for_list.forward(request, response);
       			break;
       		}
        }
		
	}

	// ������� �� POST-�������.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// ����������� �� ��������� � ������ ��������� ����� ������������� � UTF-8,
		// ����� ������� ���� ��� �������� GET/POST-����������� ������������ � "����������".
		request.setCharacterEncoding("UTF-8");

		// � JSP ��� ����������� ���� ���������� �����. ����� ������� � ��������� ���,
		// �� � ������������� ����� ������ �������� ������� ��� � �������� � �������� � JSP.
		request.setAttribute("phonebook", this.phonebook);
		
		// ��������� ���������� ��� �������� � JSP.
		HashMap<String,String> jsp_parameters = new HashMap<String,String>();

		// ���������� ��� �������� ���������� �� ������ JSP (������ ������������� (view)).
		RequestDispatcher dispatcher_for_manager = request.getRequestDispatcher("/ManagePerson.jsp");
		RequestDispatcher dispatcher_for_list = request.getRequestDispatcher("/List.jsp");
		
		
		// �������� (add_go, edit_go) � ������������� ������ (id) ��� ������� ����������� ��� ��������.
		String add_go = request.getParameter("add_go");
		String edit_go = request.getParameter("edit_go");
		String id = request.getParameter("id");
		
		// ���������� ������.
		if (add_go != null)
		{
			// �������� ������ �� ������ ������ �� �����.
			Person new_person = new Person(request.getParameter("name"), request.getParameter("surname"), request.getParameter("middlename"));

			// ��������� ���.
			String error_message = this.validatePersonFMLName(new_person); 
			
			// ���� ������ ������, ����� ����������� ����������.
			if (error_message.equals(""))
			{

				// ���� ������ ������� ��������...
				if (this.phonebook.addPerson(new_person))
				{
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("current_action_result_label", "���������� ��������� �������");
				}
				// ���� ������ �� ������� ��������...
				else
				{
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("current_action_result_label", "������ ����������");
				}

				// ��������� ���������� JSP.
				request.setAttribute("jsp_parameters", jsp_parameters);
	        
				// �������� ������� � JSP.
				dispatcher_for_list.forward(request, response);
			}
			// ���� � ������ ���� ������, ���� ������ �������� ����� � �������� �� �������.
			else
			{
    			// ���������� ���������� ��� JSP.
    			jsp_parameters.put("current_action", "add");
    			jsp_parameters.put("next_action", "add_go");
    			jsp_parameters.put("next_action_label", "��������");
    			jsp_parameters.put("error_message", error_message);
    			
    			// ��������� ���������� JSP.
    			request.setAttribute("person", new_person);
    			request.setAttribute("jsp_parameters", jsp_parameters);
    			
    			// �������� ������� � JSP.
    			dispatcher_for_manager.forward(request, response);
			}
		}
		
		// �������������� ������.
		if (edit_go != null)
		{
			// ��������� ������ � � ���������� �� ������ ������ �� �����.
			Person updatable_person = this.phonebook.getPerson(request.getParameter("id")); 
			updatable_person.setName(request.getParameter("name"));
			updatable_person.setSurname(request.getParameter("surname"));
			updatable_person.setMiddlename(request.getParameter("middlename"));

			// ��������� ���.
			String error_message = this.validatePersonFMLName(updatable_person); 
			
			// ���� ������ ������, ����� ����������� ����������.
			if (error_message.equals(""))
			{
			
				// ���� ������ ������� ��������...
				if (this.phonebook.updatePerson(id, updatable_person))
				{
					jsp_parameters.put("current_action_result", "UPDATE_SUCCESS");
					jsp_parameters.put("current_action_result_label", "���������� ��������� �������");
				}
				// ���� ������ �� ������� ��������...
				else
				{
					jsp_parameters.put("current_action_result", "UPDATE_FAILURE");
					jsp_parameters.put("current_action_result_label", "������ ����������");
				}

				// ��������� ���������� JSP.
				request.setAttribute("jsp_parameters", jsp_parameters);
	        
				// �������� ������� � JSP.
				dispatcher_for_list.forward(request, response);
			}
			// ���� � ������ ���� ������, ���� ������ �������� ����� � �������� �� �������.
			else
			{

    			// ���������� ���������� ��� JSP.
    			jsp_parameters.put("current_action", "edit");
    			jsp_parameters.put("next_action", "edit_go");
    			jsp_parameters.put("next_action_label", "���������");
    			jsp_parameters.put("error_message", error_message);

    			// ��������� ���������� JSP.
    			request.setAttribute("person", updatable_person);
    			request.setAttribute("jsp_parameters", jsp_parameters);
    			
    			// �������� ������� � JSP.
    			dispatcher_for_manager.forward(request, response);    			
    			
			}
		}
	}
}
