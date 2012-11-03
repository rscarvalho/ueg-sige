<%@page import="br.ueg.si.sige.*, java.util.*" pageEncoding="ISO-8859-1" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Usu&aacute;rios</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/gerencial.css" rel="StyleSheet" type="text/css">
</head>
<body>
<h1>Usu&aacute;rios</h1>
<form name="form1" method="get" action="usuarios">
<table width="760" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="middle">      Pesquisar:
      <input name="search" type="text" size="40" class="input" />
      <input type="submit" value="Pesquisar" class="input" />
      &nbsp;
      <a href="usuarios?search=avancada">
        <font size="-2">
          Busca Avan&ccedil;ada
        </font>
      </a>
    </td>
    <td valign="middle">
      <div align="right">&nbsp;&nbsp;
        <a href="usuarios?action=incluir">
          Novo Usu&aacute;rio
        </a>
      </div>
    </td>
  </tr>
</table>
</form>
<table border="0" cellspacing="2" cellpadding="0" width="760">
  <%try{ %>
  <tr>
    <th>Nome</th>
    <th>CPF</th>
    <th>Entidade</th>
    <th width="0">
      <font size="-2">Ativo</font>
    </th>
    <th width="0">
      <font size="-2">Editar</font>
    </th>
    <th width="0">
      <font size="-2">Excluir</font>
    </th>
  </tr>
<%
    ArrayList usuarios = (ArrayList) request.getAttribute("usuarios");
    int i = 1;
    for (Iterator iter = usuarios.iterator(); iter.hasNext(); i++) {
      Usuario item = (Usuario) iter.next();
      String classId = (i % 2 == 0) ? "tabela2" : "tabela1";
      %>
      <tr class="<%=classId%>">
        <td><%=item.getNomeCompleto()%> </td>
        <td><%=item.getCPFFormatado()%> </td>
        <td><%=item.getEntidade().getNome() %> </td>
        <td><%=(item.isAtivo())?"Sim":"N&atilde;o" %></td>
        <td>
          <div align="center">
            <a href="usuarios?action=editar&codigo=<%=item.getCodigo()%>">
              <img src="images/editar.gif" alt="Editar" width="25" height="26" border="0">
              </a>
            </div>
          </td>
          <td>
            <div align="center">
              <a href="usuarios?action=excluir&codigo=<%=item.getCodigo()%>">
                <img src="images/lixeira.gif" alt="Excluir" width="25" height="26" border="0">
                </a>
              </div>
            </td>
          </tr>
          <%}
          }catch(NullPointerException ex){
            %>
            <tr>
              <td class="tabela1" colspan="6" >
                <font color="red"><B>Não há usuários cadastrados</B></font>
              </td>
            </tr>
            <%
          }
         %>
</table>
</body>
</html>
