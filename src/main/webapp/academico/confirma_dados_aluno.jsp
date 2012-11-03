<%@ page import="br.ueg.si.sige.*,java.util.*"  pageEncoding="UTF-8" %>
<%
Aluno aluno = (Aluno) request.getAttribute("aluno");
String formAction = (String)request.getAttribute("formAction");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Untitled Document</title>
<link href="scripts/academico.css" rel="StyleSheet" type="text/css">
</head>

<body>
 <h1>Alunos
</h1>
 <strong>Confirme os dados abaixo:</strong> <BR>
 <form name="form1" method="post" action="alunos?action=<%=formAction%>">
   <input type="hidden" name="confirma" value="1" />
   <input type="hidden" name="codigo" value="<%=aluno.getCodigo()%>" />
   <table width="0" border="0">
     <tr class="tabela1">
       <td>Nome:</td>
       <td><strong>
        <input name="nome" type="hidden" class="input" id="nome" value="<%=aluno.getNome()%>">
       <%=aluno.getNome()%></strong></td>
     </tr>
     <tr class="tabela2">
       <td>Endere&ccedil;o:</td>
       <td><strong>
        <input name="endereco" type="hidden" class="input" id="endereco" value="<%=aluno.getEndereco()%>">
       <%=aluno.getEndereco()%></strong></td>
     </tr>
     <tr class="tabela1">
       <td>Telefone:</td>
       <td><strong>
        <input name="telefone" type="hidden" class="input" id="telefone" value="<%=aluno.getTelefone()%>">
       <%=aluno.getTelefoneFormatado()%></strong></td>
     </tr>
     <tr class="tabela2">
       <td>Pai:</td>
       <td><strong>
        <input name="pai" type="hidden" class="input" id="pai" value="<%=aluno.getPai()%>">
       <%=aluno.getPai()%></strong></td>
     </tr>
     <tr class="tabela1">
       <td>M&atilde;e:</td>
       <td><strong>
        <input name="mae" type="hidden" class="input" id="mae" value="<%=aluno.getMae()%>">
       <%=aluno.getMae()%></strong></td>
     </tr>
     <tr class="tabela2">
       <td>Respons&aacute;vel:</td>
       <td><strong>
        <input name="responsavel" type="hidden" class="input" id="responsavel" value="<%=aluno.getResponsavel()%>">
          <%=aluno.getResponsavel()%>
       </strong></td>
     </tr>
     <tr class="tabela1">
       <td>Data de Nascimento: </td>
       <td><strong>
        <input name="dia" type="hidden" class="input" id="dia" value="<%=aluno.getDataDeNascimento().getDate()%>">
        <input name="mes" type="hidden" value="<%=aluno.getDataDeNascimento().getMonth()+1%>" />
        <input name="ano" type="hidden" value="<%=aluno.getDataDeNascimento().getYear()+1900%>" />
        <%=aluno.getDataDeNascimentoFormatada()%></strong></td>
     </tr>
     <tr class="tabela2">
       <td>Aluno ativo? </td>
       <td><strong>
        <input name="ativo" type="hidden" value="<%=aluno.isAtivo()%>">
        <%=(aluno.isAtivo())?"Sim":"Não"%></strong></td>
     </tr>
     <tr class="tabela1">
       <td>Nacionalidade:</td>
       <td><strong>
        <input name="nacionalidade" type="hidden" class="input" id="nacionalidade" value="<%=aluno.getNacionalidade()%>">
       <%=aluno.getNacionalidade()%></strong></td>
     </tr>
     <tr class="tabela2">
       <td>Naturalidade:</td>
       <td><strong>
        <input name="naturalidade" type="hidden" class="input" id="naturalidade" value="<%=aluno.getNaturalidade()%>">
       <%=aluno.getNaturalidade()%></strong></td>
     </tr>
     <tr class="tabela1">
       <td>Escolaridade:</td>
       <td><strong>
        <input name="escolaridade" type="hidden" id="escolaridade" value="<%=aluno.getEscolaridade()%>" >
       <%=aluno.getEscolaridade()%>&ordf; s&eacute;rie </strong></td>
     </tr>
     <tr class="tabela2">
       <td>Unidade Escolar: </td>
       <td><strong>
        <input name="entidade" type="hidden" id="entidade" value="<%=aluno.getEntidade().getCodigo()%>" >
       <%=aluno.getEntidade().getNome()%></strong></td>
     </tr>
     <tr class="tabela1">
       <td valign="top">Documentos Ok?</td>
       <td><input name="documentos" type="hidden" id="documentos" value="<%=aluno.isDocumentos()%>" >
         <%=(aluno.isDocumentos())?"Sim":"Não"%>
       </td>
     </tr>
     <tr>
       <td colspan="2" valign="top"><div align="center"><strong>Os dados est&atilde;o
          corretos?</strong><br>
          <input name="Button" type="button" class="input" value="nao" onclick="history.go(-1)" >
          <input name="Submit" type="submit" class="input" value="sim">
       </div></td>
     </tr>
   </table>
 </form>
 <p>&nbsp; </p>
</body>
</html>
