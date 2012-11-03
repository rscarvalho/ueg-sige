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
<link rel="StyleSheet" href="scripts/academico.css" />
    <h1>Matr&iacute;culas</h1>
<form method="get" action="matriculas">
 Pesquisar: <input type="text" name="search" size="40" class="input" />&nbsp;&nbsp;
 <input type="submit" class="input" value="Pesquisar" />&nbsp;&nbsp;
 <a href="matriculas?action=matricular"><font size="-2">Efetuar Novas Matr&iacute;culas</font></a>
</form>
<table width="100%">
<tr>
<th style="font-size:12pt">Alunos para rematr&iacute;cula</th>
<th style="font-size:12pt">Alunos matriculados no ano atual: <c:out value="${usuarioAcademico.entidade.anoLetivo}" /> </th>
</tr>
<tr><td width="0" valign="top">
<table>
 <tr>
  <th>Nome</th>
  <th>Escolaridade</th>
  <th>Status</th>
 </tr>
 <c:forEach items="${matriculas}" var="matricula" varStatus="i">
 <c:choose>
  <c:when test="${i.index%2==0}">
   <tr class="tabela1">
  </c:when>
  <c:otherwise>
   <tr class="tabela2">
  </c:otherwise>
 </c:choose>
   <td><a href='matriculas?action=rematricula&codigo=<c:out value="${matricula.aluno.codigo}"/>'><c:out value="${matricula.aluno.nome}"/></a></td>
  <td><c:out value="${matricula.aluno.nome}"/></td>
  <td><c:out value="${matricula.aluno.escolaridade}"/>ª S&eacute;rie</td>
  <td>N&atilde;o Matriculado</td>
 </tr>
 </c:forEach>
</table>
</td>
<td width="0" valign="top" >
<table>
 <tr>
  <th>Nome</th>
  <th>Escolaridade</th>
  <th>Status</th>
 </tr>
 <c:forEach items="${matriculasAtuais}" var="matricula" varStatus="i">
 <c:choose>
  <c:when test="${i.index%2==0}">
   <tr class="tabela1">
  </c:when>
  <c:otherwise>
   <tr class="tabela2">
  </c:otherwise>
 </c:choose>
   <td><c:out value="${matricula.aluno.nome}"/></td>
   <td><c:out value="${matricula.turma.serie.numero}"/>ª S&eacute;rie 
    "<c:out value="${matricula.turma.literal}"/>"</td>
   <td>Matriculado</td>
 </tr>
 </c:forEach>
</table>
</td>
</tr></table>
    
    </body>
</html>
