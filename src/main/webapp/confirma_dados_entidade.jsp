<%@ page import="br.ueg.si.sige.*, java.util.*" %>
<%
try{
Entidade entidade = (Entidade) request.getAttribute("entidade");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/gerencial.css" rel="StyleSheet" type="text/css">
</head>

<body>
<h1>Entidades</h1>
<script language="javascript" type="text/javascript">
function URLRedirect(component){
 document.getElementById("confirma").value = component
 document.getElementById("formConfirma").submit()
}
</script>
<p><strong>Confirme os dados Abaixo:</strong></p>
<form name="form1" method="post" action="entidades?action=<%=(String) request.getAttribute("formAction") %>" id="formConfirma">
  <input type="hidden" name="confirma" id="confirma" value="S" />
  <input type="hidden" name="codigo" value="<%=entidade.getCodigo()%>" />
  <input type="hidden" name="ano_letivo" value="<%=entidade.getAnoLetivo()%>" /> 
  <table width="0" border="0" cellspacing="5" cellpadding="0">
    <tr class="tabela1">
      <td width="55">Nome:</td>
      <td width="406">
        <strong><%=entidade.getNome() %></strong>
        <input type="hidden" name="nome" value="<%=entidade.getNome() %>" />
      </td>
    </tr>
    <tr class="tabela2">
      <td>Endere&ccedil;o:</td>
      <td>
        <strong><%=entidade.getEndereco() %></strong>
        <input type="hidden" name="endereco" value="<%=entidade.getEndereco() %>" />
      </td>
    </tr>
    <tr class="tabela1">
      <td>Telefone: </td>
      <td>
        <strong><%=entidade.getTelefoneFormatado() %></strong>
        <input type="hidden" name="telefone" value="<%=entidade.getTelefone() %>" />
      </td>
    </tr>
    <tr class="tabela2">
      <td>Quantidade de Salas de Aula:</td>
      <td>
        <strong><%=entidade.getSalasDeAula() %></strong>
        <input type="hidden" value="<%=entidade.getSalasDeAula() %>" name="qtde_salas" />
      </td>
    </tr>
    <tr>
      <td colspan="2"><div align="center">Os dados est&atilde;o corretos?<br>
          <input type="button" class="input" value="Sim" onclick="URLRedirect('S')">
          &nbsp;&nbsp;&nbsp;
          <input type="button" class="input" value="N&atilde;o" onclick="history.go(-1)">
        </div></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form>
<p>&nbsp;</p>
</body>
</html>
<%}catch(Exception e){
  out.print("<H1>DEU PAU - "+e.toString()+"</H1>");
} %>
