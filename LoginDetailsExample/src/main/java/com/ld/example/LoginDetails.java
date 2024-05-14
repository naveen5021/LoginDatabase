package com.ld.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginDetails extends HttpServlet
{
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		String email = req.getParameter("email");
		String pswd = req.getParameter("pswd");
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","naveen","naveen1");
			ps = con.prepareStatement("insert into login values(?,?)");
			ps.setString(1, email);
			ps.setString(2, pswd);
			
			int n = ps.executeUpdate();
			if(n>0)
				System.out.println("Login Details are inserted");
			else
				System.out.println("Login details are not inserted");
		}
		catch(SQLException | ClassNotFoundException e)
		{
			resp.sendError(450, e.toString());
		}
		finally
		{
			try
			{
				ps = con.prepareStatement("select * from login");
				ResultSet rs = ps.executeQuery();
				out.println("<table border=\"1\" align=\"center\">");
				out.println("<tr><th>Email</th><th>Password</th></tr>");
				while(rs.next())
				{
					out.println("<tr>");
					out.println("<td align=\"center\">"+rs.getString(1)+"</td>");
					out.println("<td align=\"center\">"+rs.getString(2)+"</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				
			}
			catch(SQLException e)
			{
				resp.sendError(451, e.toString());
			}
		}
		
		try {
			
			if(ps != null)
				ps.close();
			if(con != null)
				con.close();
			
		} catch (SQLException e) {
			
		}
	}
}
