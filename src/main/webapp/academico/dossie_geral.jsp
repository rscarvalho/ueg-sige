<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Untitled Document</title>
<link href="scripts/academico.css" rel="stylesheet" type="text/css">
</head>

<body>
 <h1>Dossi&ecirc;
</h1>
 <form name="form1" method="post" action="dossie?action=pesquisa">
   Pesquisa por nome: 
   <input name="texto" type="text" class="input" size="40">
   <input name="Submit" type="submit" class="input" value="Pesquisar"> 
   <a href="#"><font size="-2">Boletim por Turma</font> </a>  
</form>
 <table width="0%"  border="0" cellspacing="2" cellpadding="0">
   <tr>
     <th scope="col">Matr&iacute;cula</th>
     <th scope="col">Nome</th>
     <th scope="col">Turma</th>
   </tr>
   <c:forEach var="matricula" items="${matriculas}" varStatus="i">
   <c:choose>
    <c:when test="${i.index%2==0}">
     <tr class="tabela1">
    </c:when>
    <c:otherwise>
     <tr class="tabela2">
    </c:otherwise>
   </c:choose>
     <td><div align="center"><c:out value="${matricula.numeracao}"/></div></td>
     <td><a href="dossie?action=detalhe&codigo=<c:out value="${matricula.aluno.codigo}"/>">
            <c:out value="${matricula.aluno.nome}">
             <font color="red"><b>Nenhum Aluno encontrado.</b></font>
            </c:out>
        </a></td>
     <td><div align="center"><c:out value="${matricula.turma.serie.numero}"/>&ordf;<c:out value="${matricula.turma.literal}"/></div></td>
   </tr>
   </c:forEach>
 </table>
 <p>&nbsp;</p>
</body>
</html>
