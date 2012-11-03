<%@ page contentType="text/html; charset=UTF-8" %>
<%
String erro = (String) request.getAttribute("erro");
if(erro == null) erro = "Permissão Negada";
%>
<html>
<head>
<title>
ERRO
</title>
<link rel="StyleSheet" href="scripts/academico.css" />
</head>
<body bgcolor="#ffffff">
<h1>
ERRO
</h1>
<center>
  Foi encontrado o seguinte erro ao processar a página:<BR>
<font size="+1" ><B><%=erro %></B></font><BR>
<a href="javascript:history.go(-1)" class="menu" >Voltar</a>
</center>
</body>
</html>
