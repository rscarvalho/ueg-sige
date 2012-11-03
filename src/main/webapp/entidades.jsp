<%@page import="br.ueg.si.sige.*, java.util.*" pageEncoding="ISO-8859-1" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Entidades</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/gerencial.css" rel="StyleSheet" type="text/css">
</head>
<body>
<h1>Entidades</h1>
<%
String erro = (String) request.getAttribute("erro");
if(erro!=null){
%>
<font color="red"><b><%=erro%></b></font>
<%
}
%>
<form name="form1" method="get" action="entidades?action=pesquisar">
  <input type="hidden" value="pesquisar" name="action" />
<table width="760" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="middle">      Pesquisar:
      <input name="search" type="text" size="40" class="input" />
      <input type="submit" value="Pesquisar" class="input" />
      &nbsp;
      <a href="entidades?action=avancada">
        <font size="-2">
          Busca Avan&ccedil;ada
        </font>
      </a>
    </td>
    <td valign="middle">
      <div align="right">&nbsp;&nbsp;
        <a href="entidades?action=incluir">
          Nova Entidade
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
    <th>Telefone</th>
    <th>Qtde de salas </th>
    <th width="0">
      <font size="-2">Editar</font>
    </th>
    <th width="0">
      <font size="-2">Excluir</font>
    </th>
  </tr>
<%
    ArrayList entidades = (ArrayList) request.getAttribute("entidades");
    int i = 1;
    for (Iterator iter = entidades.iterator(); iter.hasNext(); i++) {
      Entidade item = (Entidade) iter.next();
      String classId = (i % 2 == 0) ? "tabela2" : "tabela1";
      %>
      <tr class="<%=classId%>">
        <td><%=item.getNome()%></td>
        <td><%=item.getTelefoneFormatado()%></td>
        <td><%=item.getSalasDeAula()%> </td>
        <td>
          <div align="center">
            <a href="entidades?action=editar&codigo=<%=item.getCodigo()%>">
              <img src="images/editar.gif" alt="Editar" width="25" height="26" border="0">
            </a>
          </div>
        </td>
          <td>
            <div align="center">
              <a href="entidades?action=excluir&codigo=<%=item.getCodigo()%>">
                <img src="images/lixeira.gif" alt="Excluir" width="25" height="26" border="0">
              </a>
            </div>
        </td>
  </tr>
          <%}
          }catch(NullPointerException ex){
            %>
            <tr>
              <td class="tabela1" colspan="5" >
                <font color="red"><B>Nenhuma entidade encontrada</B></font>
              </td>
            </tr>
            <%
          }
         %>
</table>
</body>
</html>
