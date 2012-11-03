<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>.:: SIGE - Sistema de Gest&atilde;o Escolar | Mudan&ccedil;a de Senha</title>
<link href="scripts/gerencial.css" rel="StyleSheet" type="text/css">
</head>

<body>
<script type="text/javascript" language="javascript" >
function submitForm(){
  senha = document.getElementById("senha")
  confirma = document.getElementById("confirma")
  if(senha.value.length < 6){
    alert("A senha deve ter no mínimo 6 caracteres!")
  }else if(senha.value != confirma.value){
    alert("As senhas não conferem!")
  }else{
    document.getElementById("form1").submit()
  }
}
</script>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><br>
</p>
<center>
<table width="400" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><h1>Primeiro Login</h1>
      <p>Por motivos de seguran&ccedil;a, mude sua senha original:</p>
      <form name="form1" method="post" action="login?action=primeiro_login" id="form1">
        <table width="0" border="0" cellspacing="0" cellpadding="3">
          <tr>
            <td>Nova Senha:</td>
            <td><input name="senha" type="password" class="input" size="35" id="senha" ></td>
          </tr>
          <tr>
            <td>Confirme a Senha: </td>
            <td><input name="confirmaSenha" type="password" class="input" size="35" id="confirma"></td>
          </tr>
          <tr>
            <td colspan="2"><div align="center">
                <input name="Submit" type="button" onclick="submitForm()" class="input" value="Salvar">
&nbsp;&nbsp;
          <input name="Reset" type="reset" class="input" value="Limpar">
            </div></td>
          </tr>
        </table>
    </form></td>
  </tr>
</table>
</center>
<p>&nbsp;</p>
</body>
</html>
