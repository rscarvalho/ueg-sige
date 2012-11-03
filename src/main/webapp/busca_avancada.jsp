<%@ page import="br.ueg.si.sige.*, java.util.*" %>
<%
ArrayList permissoes = (ArrayList) request.getAttribute("permissoes");
ArrayList entidades = (ArrayList) request.getAttribute("entidades");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="scripts/gerencial.css" rel="StyleSheet">
</head>

<body>
<h1>Busca Avan&ccedil;ada</h1>
<form name="form1" method="post" action="">
  <p>Preencha os campos para a busca</p>
  <table width="0" border="0" cellspacing="5" cellpadding="0">
    <tr>
      <td>Nome cont&eacute;m:</td>
      <td><input name="nomeCompleto" type="text" class="input" size="50"></td>
    </tr>
    <tr>
      <td>CPF:</td>
      <td><input name="cpf" type="text" class="input" size="20"></td>
    </tr>
    <tr>
      <td>login cont&eacute;m:</td>
      <td><input name="login" type="text" class="input" size="30"></td>
    </tr>
    <tr>
      <td>Situa&ccedil;&atilde;o:</td>
      <td><select name="status">
          <option value="ativo" >Ativo</option>
          <option value="inativo" >Inativo</option>
          <option value="both" selected>Ambos</option>
        </select></td>
    </tr>
    <tr>
      <td>Entidade</td>
      <td><select name="entidade">
          <option selected value="0">--- Todas as entidades ---</option>
          <%
          for (Iterator iter = entidades.iterator(); iter.hasNext(); ) {
            Entidade entidade = (Entidade) iter.next(); %>
            <option value="<%=entidade.getCodigo()%>" >
              <%=entidade.getNome() %>
            </option>
          <%
          }
          %>
        </select></td>
    </tr>
    <tr>
      <td colspan="2">Com permiss&atilde;o nos m&oacute;dulos:<br>
        <table width="0" border="0" cellspacing="5" cellpadding="5">
          <tr>
            <td valign="top"><fieldset>
                                          <input type="hidden" name="totalPermissoes" value="<%=(String) request.getAttribute("totalPermissoes")%>" />
                                            <legend>M&oacute;dulo Gerencial</legend>
                                            <%
                                            int i=0;
                                            for (Iterator iter = permissoes.iterator(); iter.hasNext(); ) {
                                              Permissao permissao = (Permissao) iter.next();
                                              if(permissao.getModulo().getModuloPai().getDescricao().equalsIgnoreCase("sigegerencial")){
                                                %>
                                                <input type="checkbox" name="permissao<%=i++%>" value="<%=permissao.getCodigo()%>" />
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
                                                        %>
                                                        <input type="checkbox" name="permissao<%=i++%>" value="<%=permissao.getCodigo()%>"/>
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
                                                  </div>
            </td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td colspan="2"><div align="center">
          <input name="Submit" type="submit" class="input" value="Buscar">
        </div></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form>
<p>&nbsp;</p>
</body>
</html>
