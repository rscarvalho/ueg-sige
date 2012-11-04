<%@ page import="java.util.*, br.ueg.si.sige.*"  pageEncoding="UTF-8" %>
<%
@SuppressWarnings("unchecked")
ArrayList<Aluno> alunos = (ArrayList<Aluno>) request.getAttribute("alunos");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Untitled Document</title>
<link href="scripts/academico.css" rel="StyleSheet" type="text/css">
</head>

<body>
<h1>Alunos</h1>
<form name="form1" method="post" action="alunos?action=pesquisar">
  <%
  String erro = (String) request.getAttribute("erro");
  %>
  <%=(erro==null)?"":erro%>
  <table width="0" border="0" cellpadding="5" cellspacing="0">
    <tr>
      <td>Pesquisa:
        <input name="pesquisa" type="text" class="input" id="pesquisa" size="50">
        <input type="submit" class="input" value="Pesquisar">
        <%--a href="#"><font size="-2">Busca Avan&ccedil;ada</font></a--%></td>
      <td><a href="alunos?action=incluir">Novo Aluno</a> </td>
    </tr>
  </table>
</form>
<table width="0" border="0" cellpadding="0" cellspacing="2">
  <tr>
    <th scope="col">Nome</th>
    <th scope="col">Data de Nascimento </th>
    <th scope="col">Escolaridade</th>
    <th scope="col"><font size="-2">Editar</font></th>
    <th scope="col"><font size="-2">Excluir</font></th>
  </tr>
  <%try{%>
  <%
  int i = 1;
  for (Iterator<Aluno> iter = alunos.iterator(); iter.hasNext(); i++) {
    Aluno item = (Aluno) iter.next();
    String tabela = (i%2==0)?"tabela1":"tabela2";
  %>
  <tr class="<%=tabela%>">
    <td><%=item.getNome()%></td>
    <td><div align="center"><%=item.getDataDeNascimentoFormatada()%></div></td>
    <td><%=item.getEscolaridade()%>&ordf; s&eacute;rie </td>
    <td><div align="center">
      <a href="alunos?action=editar&codigo=<%=item.getCodigo()%>">
      <img src="images/editar.gif" width="19" height="20" alt="">
      </a>
    </div></td>
    <td><div align="center">
      <a href="alunos?action=excluir&codigo=<%=item.getCodigo()%>">
      <img src="images/lixeira.gif" width="19" height="20" alt="">
        </a>
    </div>
   </td>
  </tr>
  <%}//for %>
<% }catch(NullPointerException ex){
  %>
  <tr>
    <td colspan="5" >
      <font color="red" ><b>nenhum aluno encontrado</b></font>
    </td>
  </tr>
  <%
} %>
</table>
</body>
</html>
