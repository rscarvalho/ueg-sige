<%@ page import="br.ueg.si.sige.*, java.util.*, java.text.*" %>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioAcademico");
Calendar calendario = Calendar.getInstance();
DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
Date d = new Date(System.currentTimeMillis());
String data = df.format(d);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Untitled Document</title>
<link href="scripts/academico.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="0" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2"><img src="images/banner_academico.jpg" width="760" height="100"></td>
  </tr>
  <tr>
    <td bgcolor="#F6F1B9">
        <a href="alunos" target="content" >Alunos</a> | 
        <a href="dossie" target="content" >Dossi&ecirc;</a> |
        <a href="matriculas" target="content">Matr&iacute;culas</a> |
        <a href="matriculas?action=rematricular" target="content">Rematr&iacute;culas</a> |
        <a href="transferencias" target="content">Transfer&ecirc;ncias</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
    </td>
    <td bgcolor="#F6F1B9"><div align="right" class="menu">Ol&aacute;, <%=usuario.getLogin()%>! <%=data %>&nbsp;&nbsp;<a href="../login?action=muda_senha" target="_parent" >[Mudar senha]</a><a href="../login?action=logout" target="_parent" > [sair]</a> </div></td>
  </tr>
</table>
</body>
</html>
