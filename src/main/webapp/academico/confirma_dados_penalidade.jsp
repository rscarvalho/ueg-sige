<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Documento sem t&iacute;tulo</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/academico.css" rel="StyleSheet" type="text/css">
</head>

<body>
<h1>Penalidade </h1>
<form name="form1" method="post" action="dossie?action=penalidade&sub=cancelar">
 <input type="hidden" name="penalidade" value="${penalidade.codigo}" />
  <table width="0" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><p>Aluno: <strong>${penalidade.matricula.aluno.nome}</strong></p>
        <p>Tipo: <b>${penalidade.tipo} </b> </p>
        <p>Descri&ccedil;&atilde;o:<br>
          <b>${penalidade.descricao}</b></p>
        <p align="center">Deseja cancelar a penalidade acima?</p>
        <p align="center"> 
          <input type="button" class="input" value="Não" onclick="history.go(-1)">
&nbsp;
<input type="submit" class="input" value="Sim">
        </p>
      </td>
    </tr>
  </table>
  <p><br>
  </p>
</form>
<p>&nbsp;</p>
</body>
</html>
