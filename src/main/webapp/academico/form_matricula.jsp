<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="scripts/academico.css" />
    </head>
    <body>

    <h1>Matr&iacute;culas</h1>
Selecione a série que deseja efetuar matrículas:
<table>
 <tr>
  <th>S&eacute;rie</th>
  <th>Quantidade de Vagas</th>
 </tr>
 <c:forEach items="${series}" var="serie" varStatus="i">
 <c:choose>
  <c:when test="${i.index%2==0}">
   <tr class="tabela1">
  </c:when>
  <c:otherwise>
   <tr class="tabela2">
  </c:otherwise>
 </c:choose>
  <td><a href='matriculas?action=matricular&serie=<c:out value="${serie.codigo}"/>'><c:out value="${serie.numero}"/></a></td>
  <td align="center"><c:out value="${serie.vagas}">0</c:out></td>
 </tr>
 </c:forEach>
</table>
    
    </body>
</html>