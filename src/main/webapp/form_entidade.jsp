<%@ page import="br.ueg.si.sige.*, java.util.*" pageEncoding="ISO-8859-1" %>
<%
  Entidade entidade = (Entidade) request.getAttribute("entidade");
  boolean isUpdate = (entidade != null);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Untitled Document</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <link href="scripts/gerencial.css" rel="StyleSheet" type="text/css">

 </head>

      <body>
        <h1>Entidades</h1>
	    <script language="javascript" type="text/javascript">
	function validaFormIncluiEntidade(){
          nome = document.getElementById("nome")
          endereco = document.getElementById("endereco")
          telefone = document.getElementById("telefone")
          qtdeSalas = document.getElementById("qtde_salas")
          if(nome.value.length==0)
             alert("Preencha o campo \"nome\"!!!")
          else if(endereco.value.length==0)
                  alert("preencha o campo \"Endereço\"!!!")
          else if(telefone.value.length==0)
                  alert("Preencha o campo \"telefone\"!!!")
          else if(qtdeSalas.value.length==0)
                  alert("Preencha o campo \"Quantidade de Salas\"!!!")
          else document.getElementById("formInclui").submit()
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
            <form name="form1" id="formInclui" method="post" action="entidades?action=<%=(String) request.getAttribute("formAction")%>" >
              <% if(isUpdate){ %>
              <input type="hidden" name="codigo" value="<%=entidade.getCodigo()%>" />
              <input type="hidden" name="ano_letivo" value="<%=entidade.getAnoLetivo()%>" > 
              <%}%>
              <table width="0" border="0" cellspacing="5" cellpadding="0">
                <tr>
                  <td width="67">Nome:</td>
                  <td width="336">
                    <input value="<%=(isUpdate)?entidade.getNome():""%>" name="nome" type="text" class="input" id="nome" size="50"></td>
                  </tr>
                  <tr>
                    <td>Endere&ccedil;o:</td>
                    <td><input value="<%=(isUpdate)?entidade.getEndereco():""%>" name="endereco" type="text" class="input" id="endereco" size="40"></td>
                    </tr>
                    <tr>
                      <td>Telefone: </td>
                      <td><input type="text" name="telefone" id="telefone" value="<%=(isUpdate)?entidade.getTelefone():""%>" class="input" size="16">
                      <font size="-2" class="menu" >DDD + Telefone. Ex.: 6239021520</font> </td>
                      </tr>
                      <tr>
                        <td>Quantidade de Salas de Aula:</td>
                        <td><input value="<%=(isUpdate)?Integer.toString(entidade.getSalasDeAula()):""%>" name="qtde_salas" type="text" class="input" id="qtde_salas" size="5"></td>
                        </tr>
                                                <tr>
                                                  <td colspan="2"><div align="center">
                                                    <input type="button" class="input" value="Voltar" onclick="history.go(-1)">
                                                    &nbsp;&nbsp;&nbsp;
                                                    <input type="button" class="input" value="Salvar" onclick="validaFormIncluiEntidade()">
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
