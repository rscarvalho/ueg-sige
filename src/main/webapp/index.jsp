<%@ page import="br.ueg.si.sige.*" %>
<%
Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioGerencial");
if(usuario == null) response.sendRedirect("login");
else if(usuario.getSenha().equals(usuario.getCPF())) response.sendRedirect("login?action=primeiro_login");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>.:: SIGE - Sistema de Gest&atilde;o Escolar | M&oacute;dulo Gerencial</title>
</head>

<frameset rows="120,*" cols="*" framespacing="0" frameborder="NO" border="0">
  <frame src="banner_topo.jsp" name="top" scrolling="NO" noresize >
  <frame src="main.html" name="content">
</frameset>
<noframes><body>
</body></noframes>
</html>
