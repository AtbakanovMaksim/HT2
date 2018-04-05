package app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagePhoneServlet extends HttpServlet {
	
	// ������������� ��� ������������/��������������.
	private static final long serialVersionUID = 1L;
	
	// �������� ������, �������� ������ ���������� �����.
	private Phonebook phonebook;
       
    public ManagePhoneServlet()
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
		RequestDispatcher dispatcher_for_manager = request.getRequestDispatcher("/ManagePerson/ManagePerson.jsp/");
        RequestDispatcher dispatcher_for_list = request.getRequestDispatcher("/List.jsp");
        RequestDispatcher dispatcher_for_phone = request.getRequestDispatcher("/ManagePhone.jsp");

		// �������� (action) � ������������� ������ (id) ��� ������� ����������� ��� ��������.
		String action = request.getParameter("action");
		String idUser = request.getParameter("idUser");
		String idPhone = request.getParameter("idPhone");
		
        	switch (action)
        	{
        		// ���������� ������.
        		case "addPhone":
        			// �������� ����� ������ ������ � ������������.
        			Phone empty_phone = new Phone();
        			
        			// ���������� ���������� ��� JSP.
        			jsp_parameters.put("current_action", "addPhone");
        			jsp_parameters.put("next_action", "addPhone_go");
        			jsp_parameters.put("next_action_label", "��������");
        			
        			// ��������� ���������� JSP.
        			request.setAttribute("person", phonebook.getPerson(idUser));
        			request.setAttribute("phone", empty_phone);
        			request.setAttribute("jsp_parameters", jsp_parameters);
        			
        			// �������� ������� � JSP.
        			dispatcher_for_phone.forward(request, response);
        		break;
			
        		// �������������� ������.
        		case "editPhone":
        			// ���������� �� ���������� ����� ���������� � ������������� ������.        			
        			Phone editable_phone = phonebook.getPhone(idPhone);
        			
        			// ���������� ���������� ��� JSP.
        			jsp_parameters.put("current_action", "editPhone");
        			jsp_parameters.put("next_action", "editPhone_go");
        			jsp_parameters.put("next_action_label", "���������");

        			// ��������� ���������� JSP.
        			request.setAttribute("person", phonebook.getPerson(idUser));
        			request.setAttribute("phone", editable_phone);
        			request.setAttribute("jsp_parameters", jsp_parameters);
        			
        			// �������� ������� � JSP.
        			dispatcher_for_phone.forward(request, response);
        		break;
			
        		// �������� ������.
        		case "deletePhone":
        			
        			// ���� ������ ������� �������...
        			if (phonebook.deletePhone(idPhone))
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
        			dispatcher_for_manager.forward(request, response);
       			break;
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
		RequestDispatcher dispatcher_for_phone = request.getRequestDispatcher("/ManagePhone.jsp");
		
		// �������� (add_go, edit_go) � ������������� ������ (id) ��� ������� ����������� ��� ��������.
		String add_go = request.getParameter("addPhone_go");
		String edit_go = request.getParameter("editPhone_go");
		String idPhone = request.getParameter("idPhone");
		
		// ���������� ������.
		if (add_go != null)
		{
			// �������� ������ �� ������ ������ �� �����.
			System.out.println("VALUE"+ request.getParameter("value"));
			System.out.println("iduser"+ request.getParameter("idUser"));
			Phone new_phone = new Phone(request.getParameter("idUser"), request.getParameter("value"));

			// ��������� ���.
			Phone phoneTmp = new Phone();
			String error_message = phoneTmp.validatePhone(request.getParameter("number")); 
			
			// ���� ������ ������, ����� ����������� ����������.
			if (error_message.equals(""))
			{

				// ���� ������ ������� ��������...
				if (this.phonebook.addPhone(request.getParameter("number"), request.getParameter("idUser")))
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
				dispatcher_for_manager.forward(request, response);
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
    			request.setAttribute("phone", new_phone);
    			request.setAttribute("jsp_parameters", jsp_parameters);
    			
    			// �������� ������� � JSP.
    			dispatcher_for_phone.forward(request, response);
			}
		}
		
		// �������������� ������.
		if (edit_go != null)
		{
			// ��������� ������ � � ���������� �� ������ ������ �� �����.
			Phone updatable_phone = this.phonebook.getPhone(request.getParameter("idPhone")); 
			updatable_phone.setOwner(request.getParameter("idUser"));
			updatable_phone.setNumber(request.getParameter("number"));
			

			// ��������� ���.
			Phone phoneTmp = new Phone();
			String error_message = phoneTmp.validatePhone(request.getParameter("number")); 
			
			// ���� ������ ������, ����� ����������� ����������.
			if (error_message.equals(""))
			{
			
				// ���� ������ ������� ��������...
				if (this.phonebook.updatePhone(request.getParameter("number"), request.getParameter("idPhone")))
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
    			request.setAttribute("phone", updatable_phone);
    			request.setAttribute("jsp_parameters", jsp_parameters);
    			
    			// �������� ������� � JSP.
    			dispatcher_for_phone.forward(request, response);    			
    			
			}
		}
	}
}
