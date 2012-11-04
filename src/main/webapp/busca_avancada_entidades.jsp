<%@ page import="br.ueg.si.sige.*, java.util.*" %>
<%
@SuppressWarnings("unchecked")
ArrayList<Permissao> permissoes = (ArrayList<Permissao>) request.getAttribute("permissoes");

@SuppressWarnings("unchecked")
ArrayList<Entidade> entidades = (ArrayList<Entidade>) request.getAttribute("entidades");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/gerencial.css" rel="StyleSheet">
</head>

<body>
<h1>Busca Avan&ccedil;ada</h1>
<form name="form1" method="post" action="entidades?action=avancada">
  <p>Preencha os campos para a busca</p>
  <table width="0" border="0" cellspacing="5" cellpadding="0">
    <tr>
      <td>Nome cont&eacute;m:</td>
      <td><input name="nome" type="text" class="input" size="50"></td>
    </tr>
    <tr>
      <td>Endere&ccedil;o cont&eacute;m:</td>
      <td><input name="endereco" type="text" class="input" size="50"></td>
    </tr>
    <tr>
      <td>Telefone cont&eacute;m:</td>
      <td><input name="telefone" type="text" class="input" size="30"></td>
    </tr>
    <tr>
      <td>Quantidade de Salas de Aula:</td>
      <td><select name="criterio" >
        <option value="ma">Maior que</option>
        <option value="me">Menor que</option>
        <option value="ig">Igual a</option>
      </select>&nbsp;
        <input type="text" name="qtde_salas" size="5" class="input" />&nbsp;salas </td>
    </tr>

    <tr>
      <td colspan="2"><div align="center">
          <input name="Submit" type="submit" class="input" value="Buscar">
        </div></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form>
<p>&nbsp;</p>
</body>
</html>
