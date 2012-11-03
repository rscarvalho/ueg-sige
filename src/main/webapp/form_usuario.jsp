<%@ page import="br.ueg.si.sige.*, java.util.*" pageEncoding="ISO-8859-1" %>
<%
  Usuario usuario = (Usuario) request.getAttribute("usuario");
  ArrayList permissoes = (ArrayList) request.getAttribute("permissoes");
  ArrayList entidades = (ArrayList) request.getAttribute("entidades");
  if(entidades==null) out.print("Entidade é nulo!!!");
  boolean isUpdate = (usuario != null);
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
	function validaFormIncluiUsuario(){
          nomeCompleto = document.getElementById("nomeCompleto")
          login = document.getElementById("login")
          cpf = document.getElementById("cpf")
          if(nomeCompleto.value.length==0)
             alert("Preencha o campo \"nome\"!!!")
          else if(login.value.length==0)
                  alert("preencha o campo \"login\"!!!")
          else if(cpf.value.length != 14 || !(validaCPF(cpf.value)))
                  alert("Preencha o campo \"CPF\" corretamente!!!")
          else document.getElementById("formInclui").submit()
        }

        function validaCPF(CPF){
          cpf = CPF.substring(0,3)+CPF.substring(4,7)+CPF.substring(8,11)+CPF.substring(12,14);
          soma = 0;
          for(i=0, j=10; j > 1; j--, i++){
            soma += (parseInt(cpf[i])*j);
          }
          resultado = parseInt(soma/11)*11;
          digito1 = ((soma-resultado)==0 || (soma-resultado)==1) ? 0: 11-(soma-resultado);
          soma = 0;
          for(i=0, j=11; j > 1; j--, i++){
            soma += (parseInt(cpf[i])*j);
          }
          resultado = parseInt(soma/11)*11;
          digito2 = ((soma-resultado)==0 || (soma-resultado)==1) ? 0: 11-(soma-resultado);
          if((parseInt(digito1) == parseInt(cpf[9])) && (parseInt(digito2) == parseInt(cpf[10])))
             return true;
          else return false;
        }
        var lastSize = 0;
        function cpfKeyPress(){
          cpf = document.getElementById("cpf")
          if(cpf.value.length > lastSize){
            if(cpf.value.length==3 || cpf.value.length==7)
            cpf.value += "."
            if(cpf.value.length==11)
            cpf.value += "-"
          }
          lastSize = cpf.value.length
        }
	</script>
        <%
        String erro = (String) request.getAttribute("erro");
        if(erro != null){
          %>
          <font color="red" ><strong><%=erro%></strong></font><p>
            <%
            }

            %>
            <form name="form1" id="formInclui" method="post" action="usuarios?action=<%=(String) request.getAttribute("acaoFormulario")%>" >
              <% if(isUpdate){ %>
              <input type="hidden" name="codigo" value="<%=usuario.getCodigo()%>" />
              <%}%>
              <table width="332" border="0" cellspacing="5" cellpadding="0">
                <tr>
                  <td width="55">Nome:</td>
                  <td width="406">
                    <input value="<%=(isUpdate)?usuario.getNomeCompleto():""%>" name="nomeCompleto" type="text" class="input" id="nomeCompleto" size="50"></td>
                  </tr>
                  <tr>
                    <td>CPF:</td>
                    <td><input value="<%=(isUpdate)?usuario.getCPFFormatado():""%>" name="cpf" type="text" class="input" id="cpf" size="16" onkeypress="cpfKeyPress()" maxlength="14" ></td>
                    </tr>
                    <tr>
                      <td>Entidade: </td>
                      <td><select name="entidade" id="entidade">
                        <% for (Iterator iter = entidades.iterator(); iter.hasNext(); ) {
                        Entidade entidade = (Entidade) iter.next();
                        String checked = "";
                        if(isUpdate){
                          checked = (usuario.getEntidade().getCodigo()==entidade.getCodigo())?"checked":"";
                        }
                        %>
                        <option value="<%=entidade.getCodigo()%>" <%=checked%> ><%=entidade.getNome()%></option>
                        <%
                        } %>
                        </select></td>
                      </tr>
                      <tr>
                        <td>Login:</td>
                        <td><input value="<%=(isUpdate)?usuario.getLogin():""%>" name="login" type="text" class="input" id="login" size="30"></td>
                        </tr>
                        <tr>
                          <td colspan="2">
                            Gerar Senha Autom&aacute;tica?
                            <input name="geraSenha" type="radio" value="true" checked> Sim &nbsp;&nbsp;
                            <input name="geraSenha" type="radio" value="false" /> N&atilde;o
                          </td>
                          </tr>
                          <tr>
                            <td colspan="2">
                              Usu&aacute;rio Ativo?&nbsp;
                              <input name="ativo" type="radio" value="true" <%=(isUpdate)?((usuario.isAtivo())?"checked":""):""%><%=(!isUpdate)?"checked":"" %> > Sim&nbsp;&nbsp;
                              <input name="ativo" type="radio" value="false" <%=(isUpdate)?((!usuario.isAtivo())?"checked":""):""%> >  N&atilde;o
                                </td>
                              </tr>
                              <tr>
                                <td colspan="2"><div align="center">
                                  <fieldset>
                                    <legend>Permiss&otilde;es</legend>
                                    <table width="0" border="0" cellspacing="10" cellpadding="0">
                                      <tr>
                                        <td valign="top"> <fieldset>
                                          <input type="hidden" name="totalPermissoes" value="<%=(String) request.getAttribute("totalPermissoes")%>" />
                                            <legend>M&oacute;dulo Gerencial</legend>
                                            <%
                                            int i=0;
                                            for (Iterator iter = permissoes.iterator(); iter.hasNext(); ) {
                                              Permissao permissao = (Permissao) iter.next();
                                              if(permissao.getModulo().getModuloPai().getDescricao().equalsIgnoreCase("sigegerencial")){
                                                String checked = (isUpdate)?((usuario.isPermitted(permissao))?"checked":""):"";
                                                %>
                                                <input type="checkbox" name="permissao<%=i++%>" value="<%=permissao.getCodigo()%>" <%=checked%> />
                                                  <B><%=permissao.getModulo().getDescricao()%></B> :
                                                  <%=permissao.getDescricao()%><BR>
                                                    <%
                                                    }
                                                  }
                                                  %>
                                                  </fieldset>
                                                  &nbsp;</td>
                                                  <td valign="top"> <fieldset>
                                                    <legend>M&oacute;dulo Acad&ecirc;mico</legend>
                                                    <%
                                                    for (Iterator iter = permissoes.iterator(); iter.hasNext(); ) {
                                                      Permissao permissao = (Permissao) iter.next();
                                                      if(permissao.getModulo().getModuloPai().getDescricao().equalsIgnoreCase("sigeacademico")){
                                                        String checked = (isUpdate)?((usuario.isPermitted(permissao))?"checked":""):"";
                                                        %>
                                                        <input type="checkbox" name="permissao<%=i++%>" value="<%=permissao.getCodigo()%>" <%=checked%> />
                                                          <B><%=permissao.getModulo().getDescricao()%></B> :
                                                          <%=permissao.getDescricao()%><BR>
                                                            <%
                                                            }
                                                          }
                                                          %>
                                                          </fieldset>
                                                          &nbsp; </td>
                                                        </tr>
                                                      </table>
                                                    </fieldset>
                                                  </div></td>
                                                </tr>
                                                <tr>
                                                  <td colspan="2"><div align="center">
                                                    <input type="button" class="input" value="Salvar" onclick="validaFormIncluiUsuario()">
                                                      &nbsp;&nbsp;&nbsp;
                                                      <input name="Submit2" type="reset" class="input" value="Limpar Dados">
                                                    </div>
						  </td>
                                                 </tr>
                                                </table>
                                                  <p>&nbsp;</p>
                                                </form>
                                                <p>&nbsp;</p>
                                              </body>
                                            </html>
