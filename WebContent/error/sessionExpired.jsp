<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%
    request.getSession().invalidate();
%>
<HTML>
    <HEAD>
    </HEAD>
    <BODY>
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <div align="center">
            <strong>Su sesi&oacute;n ha expirado :(<br /> <br /> Haga <a
                    href="<%=request.getContextPath()%>">CLICK AQUI</a> para ir a la p&aacute;gina de ingreso
                al sistema. <br /> <br /> Muchas Gracias.</strong>
        </div>
    </BODY>
</HTML>