<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>

        <meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
        <title>Turmas</title>
        <link href="scripts/gerencial.css" rel="stylesheet" type="text/css">

    </head>
    <body>

        <h1>Turmas</h1>

        <form method="post" action="turmas?action=pesquisa">Pesquisar por escola:
            <input class="input" size="50" name="entidade">
            <input class="input" value="Pesquisar" type="submit">
            <a href="turmas?action=incluir"><font size="-2">Nova Turma<font></a>
        </form><P>
        <font color="red"><b><c:out value="${erro}" escapeXml="false" /></b></font>
        <table width="0" cellpadding="0" cellspacing="2" border="0">

            <tbody>

                <tr>

                    <th>Escola</th>

                    <th>S&eacute;rie</th>

                    <th>Turma</th>

                    <th><font size="-2">Editar</font></th>

                    <th><font size="-2">Excluir</font></th>

                </tr>

                <c:forEach var="turma" items="${turmas}" varStatus="index">
                    <c:choose>
                       <c:when test="${index.index%2==0}">
                       <tr class="tabela1">
                       </c:when>
                       <c:otherwise>
                       <tr class="tabela2">
                       </c:otherwise>
                    </c:choose>
                    

                        <td><c:out value="${turma.entidade.nome}"/></td>

                        <td align="center"><c:out value="${turma.serie.numero}"/>&ordf;</td>

                        <td align="center"><c:out value="${turma.literal}"/></td>

                        <td align="center">
                        <a href='turmas?action=editar&codigo=<c:out value="${turma.codigo}" />'>
                        <img src="images/editar.gif" height="25" width="26" border=0>
                        </a>
                        </td>

                        <td align="center">
                        <a href='turmas?action=excluir&codigo=<c:out value="${turma.codigo}" />'>
                        <img src="images/lixeira.gif" height="25" width="26" border=0>
                        </a>
                        </td>

                    </tr>
                </c:forEach>



            </tbody>
        </table>

    </body>
</html>
