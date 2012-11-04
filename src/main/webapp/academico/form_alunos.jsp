<%@page import="java.text.DateFormat"%>
<%@ page import="java.util.*, java.text.*, br.ueg.si.sige.*" pageEncoding="UTF-8" %>
<%
Entidade entidade = ((Usuario) request.getSession().getAttribute("usuarioAcademico")).getEntidade();
Aluno aluno = (Aluno) request.getAttribute("aluno");
boolean isUpdate = (aluno != null);
String formAction = (String) request.getAttribute("formAction");
DateFormat dayFormat, monthFormat, yearFormat;
dayFormat = new SimpleDateFormat("d");
monthFormat = new SimpleDateFormat("M");
yearFormat = new SimpleDateFormat("yyyy");
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
<script language="javascript">
function submitForm(){
  if(document.getElementById("nome").value.length>0 &&
  document.getElementById("endereco").value.length>0 &&
  (document.getElementById("pai").value.length>0 ||
  document.getElementById("mae").value.length>0 ||
  document.getElementById("mae").value.length>0) &&
  document.getElementById("dia").value.length>0 &&
  document.getElementById("mes").value.length>0 &&
  document.getElementById("ano").value.length>0 &&
  document.getElementById("nacionalidade").value.length>0 &&
  document.getElementById("naturalidade").value.length>0){
    document.getElementById("form1").submit()
  }else{
    alert("preencha os campos corretamente!")
  }
}
</script>
 <h1>Alunos</h1>
 <form name="form1" method="post" action="alunos?action=<%=formAction%>" id="form1" >
   <table width="0" border="0">
     <tr>
       <td colspan="2">
	       <input type="hidden" name="codigo" value="<%=(isUpdate)?Integer.toString(aluno.getCodigo()):""%>" />
       </td>
     </tr>
     <tr>
       <td>Nome:</td>
       <td><input name="nome" type="text" class="input" id="nome" size="50" value="<%=(isUpdate)?aluno.getNome():""%>"></td>
     </tr>
     <tr>
       <td>Endere&ccedil;o:</td>
       <td><input name="endereco" type="text" class="input" id="endereco" size="50" value="<%=(isUpdate)?aluno.getEndereco():""%>"></td>
     </tr>
     <tr>
       <td>Telefone:</td>
       <td><input name="telefone" type="text" class="input" id="telefone" size="20" value="<%=(isUpdate)?aluno.getTelefone():""%>"></td>
     </tr>
     <tr>
       <td>Pai:</td>
       <td><input name="pai" type="text" class="input" id="pai" size="50" value="<%=(isUpdate)?aluno.getPai():""%>"></td>
     </tr>
     <tr>
       <td>M&atilde;e:</td>
       <td><input name="mae" type="text" class="input" id="mae" size="50" value="<%=(isUpdate)?aluno.getMae():""%>"></td>
     </tr>
     <tr>
       <td>Respons&aacute;vel:</td>
       <td><input name="responsavel" type="text" class="input" id="responsavel" size="50" value="<%=(isUpdate)?aluno.getResponsavel():""%>" ></td>
     </tr>
     <tr>
       <td>Data de Nascimento: </td>
       <td>
       <input name="dia" type="text" class="input" id="dia" size="4" maxlength="2" value="<%=(isUpdate)?dayFormat.format(aluno.getDataDeNascimento()):""%>" >
       /
       <input name="mes" type="text" class="input" id="mes" size="4" maxlength="2" value="<%=(isUpdate)?monthFormat.format(aluno.getDataDeNascimento()):""%>" >
       /
       <input name="ano" type="text" class="input" id="ano" size="8" maxlength="4" value="<%=(isUpdate)?yearFormat.format(aluno.getDataDeNascimento()):""%>" ></td>
     </tr>
     <tr>
       <td>Aluno ativo? </td>
       <td><input name="ativo" type="radio" value="true" <%=(!isUpdate)?"checked":(aluno.isAtivo())?"chedked":""%>>
         Sim
         <input name="ativo" type="radio" value="false" <%=(isUpdate)?((!aluno.isAtivo())?"checked":""):""%> >
         N&atilde;o</td>
     </tr>
     <tr>
       <td>Nacionalidade:</td>
       <td><input name="nacionalidade" type="text" class="input" id="nacionalidade" value="<%=(isUpdate)?aluno.getNacionalidade():""%>" ></td>
     </tr>
     <tr>
       <td>Naturalidade:</td>
       <td><input name="naturalidade" type="text" class="input" id="naturalidade" value="<%=(isUpdate)?aluno.getNaturalidade():""%>" ></td>
     </tr>
     <tr>
       <td>Escolaridade:</td>
       <td><select name="escolaridade" id="escolaridade">
         <%for (int k=1;k<=8 ;k++ ) { %>
           <option <%=(isUpdate)?((aluno.getEscolaridade()==k)?"selected":""):""%> value="<%=k%>" ><%=k%></option>
         <%} %>
       </select>
       &ordf; s&eacute;rie </td>
     </tr>
     <tr>
       <td>Unidade Escolar: </td>
       <td><input type="hidden" name="entidade" value="<%=entidade.getCodigo()%>" /><B><%=entidade.getNome()%></B></td>
     </tr>
     <tr>
       <td valign="top">Documentos ok?</td>
       <td><input name="documentos" type="radio" value="true" <%=(!isUpdate)?"checked":(aluno.isDocumentos())?"chedked":""%>>
         Sim
         &nbsp;&nbsp;&nbsp;
         <input name="documentos" type="radio" value="false" <%=(isUpdate)?((!aluno.isDocumentos())?"checked":""):""%> >
         N&atilde;o</td>
     </tr>
     <tr>
       <td colspan="2" valign="top"><div align="center">
          <input name="Button" type="button" class="input" onClick="history.go(-1)" value="<< Cancelar">
          <input name="Reset" type="reset" class="input" value="Limpar Dados">
          <input name="Button" type="button" class="input" onClick="submitForm()" value="Continuar >>">
       </div></td>
     </tr>
   </table>
 </form>
 <p>&nbsp; </p>
</body>
</html>
