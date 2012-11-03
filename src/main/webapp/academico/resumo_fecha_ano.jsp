<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="scripts/academico.css" />
    </head>
    <body>

    <h1>Fechar Ano Letivo</h1>
    <table>
     <tr>
      <th rowspan="2">Nome</th>
      <th rowspan="2">Situa&ccedil;&atilde;o</th>
     </tr>
     <c:forEach items="${alunos}" var="aluno" varStatus="i" >
     <tr class='<c:out value="${(i.index%2)+1}"/>'>
     <td>${alunos.nome}</td>
     <td>
     <c:choose>
      <c:when test="${alunos.situacao}">AP</c:when>
      <c:otherwise><font color="red"><b>RP</b></font></c:otherwise>
     </c:choose>
     </td>
     </tr>
     </c:forEach>
    </table>
    <center>
    <form action="main.html">
    <input type="submit" value="Continuar" />
    </form>
    </center>
    </body>
</html>
