<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
</head>
<body><h1>Rematricular Aluno</h1>
<form method="post" action="matriculas?action=rematricula">
<input type="hidden" name="codigo" value='<c:out value="${matricula.aluno.codigo}" />' />
<table cellspacing="2" >
 <tr>
   <td>Aluno: </td>
   <td><c:out value="${matricula.aluno.nome}" /></td>
 </tr><tr>
    <td>S&eacute;rie: </td>
    <td><c:out value="${matricula.turma.serie.nome}"/></td>
 </tr><tr>
    <td>Nova Turma: </td>
    <td><select name="novaTurma">
    <c:forEach items="${turmas}" var="turma" varStatus="i" >
    <option class='<c:out value="${(i.index%2)+1}"/>' value='<c:out value="${turma.codigo}"/>'>
    Turma "<c:out value="${turma.nome}"/>"
    </option>
    </c:forEach>
    </select></td>
 </tr><tr>
  <td align="center" colspan="2" >
  <input type="submit" value="Renovar Matricula" >
  </td>
 </tr>
</table>
</form>
    </body>
</html>
