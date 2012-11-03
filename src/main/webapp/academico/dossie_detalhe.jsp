<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/academico.css" rel="StyleSheet" type="text/css">
</head>

<body>
<h1>Dossi&ecirc; - ${historico.aluno.nome} </h1>
<form action="dossie?action=mudaAno" method="get" id="formMudaAno">
<p><strong>Ano: </strong>
 <select name="ano" onchange="document.getElementById('formMudaAno').submit()" >
  <c:forEach items="${historico.boletins}" var="boletimH">
   <c:if test="${boletim.anoLetivo!=boletimH.anoLetivo}">
   <option value="${boletimH.anoLetivo}">${boletimH.anoLetivo}</option> 
   </c:if>
  </c:forEach>
   <option value="${boletim.anoLetivo}" selected="selected">${boletim.anoLetivo}</option>
 </select>
 </form>
 &nbsp;&nbsp;&nbsp;<a href="dossie?action=sair">[Voltar à lista de alunos]</a>&nbsp;&nbsp;&nbsp;
 <a href="dossie?action=salvar">[salvar alterações feitas]</a></p>
<table width="0" height="0"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="124" class="h2">Ficha Individual</td>
    <td width="223">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="tabela3"><table width="0%"  border="0" cellpadding="0" cellspacing="2">
        <tr>
          <th rowspan="2">&nbsp;</th>
          <c:forEach items="${boletim.turma.serie.disciplinas}" var="disciplina">
          <th colspan="2"><div align="center"><c:out value="${disciplina.sigla}"/></div>
          </th>
          </c:forEach>
        </tr>
        <tr class="tabela3">
        <c:forEach items="${boletim.turma.serie.disciplinas}" var="disciplina">
          <th>Nota</th>
          <th>Faltas</th>
        </c:forEach>
        </tr>
        <c:forEach begin="1" end="4" varStatus="j">
        <tr class="tabela3">
          <th>${j.index}&deg; Bimestre </th>
          <c:forEach items="${boletim.turma.serie.disciplinas}" var="disciplina" varStatus="i">
           <td class="tabela2"><div align="center" id='nota${j.index}${disciplina.codigo}'>--</div></td>
           <td class="tabela1"><div align="center" id='falta${j.index}${disciplina.codigo}'>--</div></td>
          </c:forEach>
        </tr>
        </c:forEach>
        <tr class="tabela3">
          <th rowspan="2">Situa&ccedil;&atilde;o</th>
          
          <c:forEach items="${boletim.turma.serie.disciplinas}" var="disciplina" varStatus="i">
           <td class="tabela2" colspan="2"><div align="center" id='situacao${disciplina.codigo}' style="font-weight:bold">RP</div></td>
          </c:forEach>
        </tr>
        <tr class="h2">
          <td colspan="${fn:length(boletim.turma.serie.disciplinas)*2}" class="situacao"><div align="center" style="font-weight:bold" id='situacao' >--</div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <c:if test="${boletim.turma.ano == usuarioAcademico.entidade.anoLetivo}">
  <tr>
    <td colspan="2" class="tabela3"><a href="dossie?action=fichaIndividual">Atualizar Ficha Individual</a></td>
  </tr>
  </c:if>
  <tr>
    <td colspan="2" class="tabela3"><a href="dossie?action=boletim">Emitir Boletim</a></td>
  </tr>
  <tr>
    <td colspan="2" class="tabela3"><a href="dossie?action=historico">Emitir Hist&oacute;rico</a></td>
  </tr>
</table>
<p>&nbsp;</p>
<table width="0" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="h2" width="98">Penalidades</td>
    <td width="249">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="tabela3">
     <c:forEach items="${historico.penalidades}" var="penalidade">
      <strong>- <c:out value="${penalidade.tipo}"/></strong> 
       - <c:out value="${penalidade.descricao}"/>
       <c:if test="${boletim.turma.ano == usuarioAcademico.entidade.anoLetivo}">
       (<a href='dossie?action=penalidade&sub=cancelar&codigo=<c:out value="${penalidade.codigo}"/>'>cancelar</a>)<br>
       </c:if>
     </c:forEach>
  </tr>
  <tr>
   <c:if test="${boletim.turma.ano == usuarioAcademico.entidade.anoLetivo}">
    <td colspan="2" class="tabela3"><a href="dossie?action=penalidade&sub=incluir">Nova Penalidade</a></td>
   </c:if>
  </tr>
</table>
<p>&nbsp; </p>
<script language="javascript">
<%-- Preenchimento dinamico das celulas --%>
disciplinas = Array()
k=0
<c:forEach items="${boletim.itensBoletim}" var="item">
f = document.getElementById('falta${item.bimesLetivo}${item.disciplina.codigo}')
f.innerHTML = '${item.faltas}'
n = document.getElementById('nota${item.bimesLetivo}${item.disciplina.codigo}')
n.innerHTML = '${item.nota}'
disciplinas[k++]='${item.disciplina.codigo}'
</c:forEach>
aprovado = true
for(var i=0;i < disciplinas.length; i++){
 for(var j=0; j < 5; j++){
  soma=0;
  d = document.getElementById('nota'+j+disciplinas[i])
  if(d!=null){
   soma += parseInt(d.value)
  }
 }
 
 if(soma/4 > 5.0){
  document.getElementById('situacao'+disciplinas[i]).innerHTML='AP'
 }else{
  aprovado = false
  document.getElementById('situacao'+disciplinas[i]).innerHTML='RP'
 }
} 
str = (aprovado)?'Aprovado':'Reprovado'
document.getElementById('situacao').innerHTML = str
</script>
</body>
</html>
