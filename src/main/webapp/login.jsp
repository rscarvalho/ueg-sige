<%@ page contentType="text/html" %>
<%
String erro = (String) request.getAttribute("erro");
if(erro == null) erro = "";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>.::SIGE - Sistema de Gest&atilde;o Escolar | Login</title>
<link href="scripts/gerencial.css" rel="StyleSheet" type="text/css">
</head>

<body>
<p>&nbsp;</p>
<p>&nbsp;</p>
<table width="0" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="760" align="center" valign="middle"><img src="images/logo.gif" width="358" height="150"></td>
  </tr>
  <tr>
    <td align="center" valign="middle">
      <form name="form1" method="post" action="login">
      <table width="0%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td width="0">Login:</td>
          <td width="0"><input name="login" type="text" class="input"></td>
        </tr>
        <tr>
          <td width="0">Senha:</td>
          <td width="0"><input name="senha" type="password" class="input"></td>
        </tr>
        <tr align="center">
          <td colspan="2"><input name="Submit" type="submit" class="input" value="Entrar"> &nbsp;&nbsp;
            <input name="Reset" type="reset" class="input" value="Limpar"></td>
          </tr>
          <tr>
            <td colspan="2" >
              <center>
              <font color="red" ><b><%=erro%></b></font>
              </center>
            </td>
          </tr>
      </table>
    </form>      <p>&nbsp;</p>
    </td>
  </tr>
</table>
</body>
</html>
