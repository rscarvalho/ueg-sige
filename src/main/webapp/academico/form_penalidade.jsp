<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Documento sem t&iacute;tulo</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/academico.css" rel="StyleSheet" type="text/css">
</head>

<body>
<h1>Inserir Penalidade </h1>
<form name="form1" method="post" action="dossie?action=penalidade&sub=incluir">
  <table width="0" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><p>Aluno: <strong>${historico.aluno.nome}</strong></p>
        <p>Tipo: 
          <select name="tipo" class="input">
            <option selected value="Advertencia">Advertencia</option>
            <option value="Suspensao">Suspensao</option>
          </select>
        </p>
        <p>Descri&ccedil;&atilde;o:<br>
          <textarea name="descricao" cols="60" rows="6" class="input"></textarea>
        </p>
        <p align="center"> 
          <input type="button" class="input" value="&lt;&lt; Voltar" onclick="history.go(-1)">
          &nbsp; &nbsp;&nbsp; 
          <input type="submit" class="input" value="Continuar &gt;&gt;">
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
