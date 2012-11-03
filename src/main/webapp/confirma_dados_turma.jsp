<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Untitled Document</title>
<link href="scripts/gerencial.css" rel="stylesheet" type="text/css">
</head>

<body>
 <h1>Turmas
</h1>
 <form name="form1" method="post" action='turmas?action=<c:out value="${action}" />' >
 <input type="hidden" name="confirma" value="1" />
 <input type="hidden" name="codigo" value='<c:out value="${turma.codigo}"/>'>
   <table width="0" border="0" cellspacing="2" cellpadding="0">
     <tr class="tabela1">
       <td>Escola:</td>
       <td><input type="hidden" name="entidade" value='<c:out value="${turma.entidade.codigo}" />'><c:out value="${turma.entidade.nome}"/></td>
     </tr>
     <tr class="tabela2">
       <td>S&eacute;rie:</td>
       <td><input type="hidden" name="serie" value='<c:out value="${turma.serie.codigo}" />'>
	   <c:out value="${turma.serie.numero}" />
	   </td>
     </tr>
     <tr class="tabela1">
       <td>Literal de Identifica&ccedil;&atilde;o : </td>
       <td><input name="literal" type="hidden" class="input" value='<c:out value="${turma.literal}" />' size="3">
       <c:out value="${turma.literal}"/>
       </td>
     </tr>
     <tr class="tabela2">
       <td>Ano:</td>
       <td><input type="hidden" name="ano" value='<c:out value="${turma.ano}" />'>
	   <c:out value="${turma.ano}" /></td>
     </tr>
     <tr>
       <td colspan="2"><div align="center">Os dados acima est&atilde;o corretos?<br>
          <input name="Button" type="button" class="input" value="N&atilde;o" onclick="javascript:history.go(-1)">
            <input name="Reset" type="submit" class="input" value="Sim">
       </div></td>
     </tr>
   </table>
</form>
<p>&nbsp; </p>
</body>
</html>
