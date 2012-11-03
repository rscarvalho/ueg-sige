<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Untitled Document</title>
<link href="scripts/gerencial.css" rel="stylesheet" type="text/css">
<script language="javascript">
function envia(){
 entidade = document.getElementById("entidade").value.length
 serie = document.getElementById("serie").value.length
 ano = document.getElementById("ano").value.length
 literal = document.getElementById("literal").value.length
 if(entidade>0 && serie>0 && ano>0){
   //document.getElementById("form1").submit()
   return true
 }else{
   alert("preencha os campos corretamente!")
   return false
 }
}
</script>
</head>

<body>
 <h1>Turmas
</h1>
 <form name="form1" method="post" id="form1" action='turmas?action=<c:out value="${action}" />' onsubmit="javascript:envia()" >
 <input type="hidden" name="codigo" value='<c:out value="${turma.codigo}"/>'>
   <table width="0" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td>Escola:</td>
       <td><select name="entidade" id="entidade" id="entidade">
       <option value='<c:out value="${turma.entidade.codigo}" />' selected ><c:out value="${turma.entidade.nome}"/></option>
       <c:forEach items="${entidades}" var="entidade" >
       <option value='<c:out value="${entidade.codigo}" />'><c:out value="${entidade.nome}"/></option>
       </c:forEach>
       </select></td>
     </tr>
     <tr>
       <td>S&eacute;rie:</td>
       <td>
       <select name="serie" id="serie" >
       <option value='<c:out value="${turma.serie.codigo}" />' selected ><c:out value="${turma.serie.numero}"/></option>
        <c:forEach items="${series}" var="serie" >
       <option value='<c:out value="${serie.codigo}" />'><c:out value="${serie.numero}"/></option
       </c:forEach>
       </select>
       </td>
     </tr>
     <tr>
       <td>Literal de Identifica&ccedil;&atilde;o : </td>
       <td><input name="literal" type="text" class="input" id="literal" value='<c:out value=" ${turma.literal}" />' size="3"></td>
     </tr>
     <tr>
       <td>Ano:</td>
       <td><select name="ano" id="ano" id="ano">
       <option value='<c:out value="${turma.ano}" />' selected ><c:out value="${turma.ano}"/></option>
       <c:forEach begin="1950" end="2005" step="1" varStatus="i">
       <option value='<c:out value="${i.index}"/>'><c:out value="${i.index}"/></option>
       </c:forEach>
       </select></td>
     </tr>
     <tr>
       <td colspan="2"><div align="center">
          <input name="Submit" type="submit" class="input" value="Continuar">
            <input name="Reset" type="reset" class="input" value="Limpar Dados">
       </div></td>
     </tr>
   </table>
   </form>
<p>&nbsp; </p>
</body>
</html>
