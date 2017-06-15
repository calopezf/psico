
<%
	request.getSession().invalidate();
%>
<%
	response.sendRedirect("paginas/index.jsf");
%>

