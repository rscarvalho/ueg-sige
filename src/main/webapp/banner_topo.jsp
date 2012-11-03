<%@ page import="br.ueg.si.sige.*, java.util.*, java.text.*" %>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioGerencial");
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
<link href="scripts/gerencial.css" rel="stylesheet" type="text/css">
</head>
<body>
<base target="content"/>
<table width="760" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2">
      <img src="images/banner_gerencial.jpg" width="760" height="100" alt=""/>
    </td>
  </tr>
  <tr>
    <td bgcolor="#CDD9F1">
      <span class="menu">
        <a href="main.html">Principal</a>
        |
        <a href="usuarios">
          Usu&aacute;rios
        </a>
        |
        <a href="entidades"> Escolas </a> 
        | 
        <a href="turmas">Turmas</a>
      </span>
    </td>
    <td bgcolor="#CDD9F1">
      <div align="right" class="menu">
        Ol&aacute;,  <%=usuario.getLogin()%>! <%=data %>&nbsp;&nbsp;
        <a href="login?action=muda_senha" target="_parent" >[Mudar senha]</a>&nbsp;&nbsp;
        <a href="login?action=logout" target="_parent">[sair]</a>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
