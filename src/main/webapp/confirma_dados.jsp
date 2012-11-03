<%@ page import="br.ueg.si.sige.*, java.util.*" %>
<%
try{
Usuario usuario = (Usuario) request.getAttribute("usuario");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/gerencial.css" rel="StyleSheet" type="text/css">
</head>

<body>
<h1>Usu&aacute;rios </h1>
<script language="javascript" type="text/javascript">
function URLRedirect(component){
 document.getElementById("confirma").value = component
 document.getElementById("formConfirma").submit()
}
</script>
<p><strong>Confirme os dados Abaixo:</strong></p>
<form name="form1" method="post" action="usuarios?action=<%=(String) request.getAttribute("acaoFormulario") %>" id="formConfirma">
  <input type="hidden" name="confirma" id="confirma" value="S" />
  <input type="hidden" name="codigo" value="<%=usuario.getCodigo()%>" />
  <table width="0" border="0" cellspacing="5" cellpadding="0">
    <tr class="tabela1">
      <td width="55">Nome:</td>
      <td width="406">
        <strong><%=usuario.getNomeCompleto() %></strong>
        <input type="hidden" name="nomeCompleto" value="<%=usuario.getNomeCompleto() %>" />
      </td>
    </tr>
    <tr class="tabela2">
      <td>CPF:</td>
      <td>
        <strong><%=usuario.getCPFFormatado() %></strong>
        <input type="hidden" name="cpf" value="<%=usuario.getCPF() %>" />
      </td>
    </tr>
    <tr class="tabela1">
      <td>Entidade: </td>
      <td>
        <strong><%=usuario.getEntidade().getNome() %></strong>
        <input type="hidden" name="entidade" value="<%=usuario.getEntidade().getCodigo() %>" />
      </td>
    </tr>
    <tr class="tabela2">
      <td>Login:</td>
      <td>
        <strong><%=usuario.getLogin() %></strong>
        <input type="hidden" value="<%=usuario.getLogin() %>" name="login" />
      </td>
    </tr>
    <tr>
      <td class="tabela1">Gerar Senha Autom&aacute;tica: </td>
      <td class="tabela1">
        <strong><%=(usuario.getSenha()!=null)?"Sim":"N&atilde;o" %></strong>
        <input type="hidden" value="<%=(usuario.getSenha()!=null)?"true":"false"%>" name="geraSenha" />
      </td>
    </tr>
    <tr>
      <td class="tabela2"> Usu&aacute;rio Ativo: </td>
      <td class="tabela2">
        <strong><%=(usuario.isAtivo())?"Sim":"N&atilde;o"%></strong>
        <input name="ativo" type="hidden" value="<%=usuario.isAtivo()%>" />
      </td>
    </tr>
    <tr>
      <td colspan="2"><div align="center">
          <fieldset>
          <legend>Permiss&ocirc;es</legend>
          <table width="0" border="0" cellspacing="10" cellpadding="0">
            <tr>
              <td valign="top"> <fieldset>
                <legend>M&oacute;dulo Gerencial</legend>
                <%
                int k = 0;
                for (int i = 0; i < usuario.getPermissoes().length; i++) {
                  Permissao permissao = usuario.getPermissoes()[i];
                  if(permissao.getModulo().getModuloPai().getDescricao().equalsIgnoreCase("sigegerencial")){
                    %>
                    <strong><%=permissao.getModulo().getDescricao() %></strong>:
                    <%=permissao.getDescricao() %>
                      <input type="hidden" name="permissao<%=k++%>" value="<%=permissao.getCodigo()%>" /><BR>
                      <%
                      }
                    } %>
                </fieldset>
                &nbsp;</td>
              <td valign="top"> <fieldset>
                <legend>M&oacute;dulo Acad&ecirc;mico</legend>
                <%
                for (int j = 0; j < usuario.getPermissoes().length; j++) {
                  Permissao permissao = usuario.getPermissoes()[j];
                  if(permissao.getModulo().getModuloPai().getDescricao().equalsIgnoreCase("sigeacademico")){
                    %>
                    <strong><%=permissao.getModulo().getDescricao() %></strong>:
                    <%=permissao.getDescricao() %>
                      <input type="hidden" name="permissao<%=k++%>" value="<%=permissao.getCodigo()%>" /><BR>
                      <%
                      }
                    } %>
                    <input type="hidden" name="totalPermissoes" value="<%=k%>" />
                </fieldset>
                &nbsp; </td>
            </tr>
          </table>
          </fieldset>
        </div></td>
    </tr>
    <tr>
      <td colspan="2"><div align="center">Os dados est&atilde;o corretos?<br>
          <input type="button" class="input" value="Sim" onclick="URLRedirect('S')">
          &nbsp;&nbsp;&nbsp;
          <input type="button" class="input" value="N&atilde;o" onclick="history.go(-1)">
        </div></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form>
<p>&nbsp;</p>
</body>
</html>
<%}catch(Exception e){
  out.print("<H1>DEU PAU - "+e.toString()+"</H1>");
} %>
