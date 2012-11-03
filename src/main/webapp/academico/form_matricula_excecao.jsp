<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

    <h1>Matr&iacute;culas</h1>
Existem mais alunos cadastrados que a quantidade de vagas oferecidas.<br/>
Selecione os alunos que deseja matricular na série desejada. (vagas: <b id="texto"><c:out value="${serie.vagas}"/></b>)
<form method="post" action="matriculas?action=matricula&excecao=1" >
<table>
 <tr>
  <th>&nbsp;</th>
  <th>Alunos</th>
 </tr>
 <c:forEach items="${alunos}" var="aluno" varStatus="i">
 <c:choose>
  <c:when test="${i.index%2==0}">
   <tr class="tabela1">
  </c:when>
  <c:otherwise>
   <tr class="tabela2">
  </c:otherwise>
 </c:choose>
  <td><input type="checkbox" name="aluno[<c:out value="${i.index}"/>]" value='<c:out value="${aluno.codigo}"/>' /></td>
  <td align="center"><c:out value="${aluno.nome}">0</c:out></td>
 </tr>
 </c:forEach>
 <tr>
  <td align="center">
   <input type="submit" value="Confirmar"/>
  </td>
 </tr>
</table>
</form>
    
    </body>
</html>