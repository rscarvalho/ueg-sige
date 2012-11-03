<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="scripts/academico.css"/>
    </head>
    <body>

    <h1>Matricular Alunos</h1>
    Clique em <i>Ok</i> para continuar.
    <table cellspacing="2">
    <tr>
     <th>S&eacute;rie<th>
     <th>Turma<th>
     <th>Quantidade de vagas<th>
    </tr>
    <c:forEach items="${turmas}" var="turma" varStatus="i">
      <tr class='tabela<c:out value="${(i.index%2)+1}" />'>
      <td><c:out value="${turma.serie.nome}"/>ª s&eacute;rie</td>
      <td>"<c:out value="${turma.nome}"/>"</td>
      <td><c:out value="${turma.serie.vagas}"/></td>
      </tr>
    </c:forEach>
    </table>
    <form action="matriculas">
    <input type="submit" value="Ok" />
    </form>
    </body>
</html>
