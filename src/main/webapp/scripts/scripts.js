function validaFormIncluiUsuario(){
 nomeCompleto = document.getElementById("nomeCompleto")
 login = document.getElementById("login")
 cpf = document.getElementById("cpf")
 if(nomeCompleto.value.length==0)
   alert("Preencha o campo \"nome\"!!!")
 else if(login.value.length==0)
        alert("preencha o campo \"login\"!!!")
      else if(cpf.value.length != 14 || !validaCPF(cpf.value))
             alert("Preencha o campo \"CPF\" corretamente!!!")
	   else document.getElementById("formInclui").submit()
}

function validaCPF(CPF){
 CPF = "002.571.991-28";
 cpf = CPF.substring(0,3)+CPF.substring(4,7)+CPF.substring(8,11)+CPF.substring(12,14);
 soma = 0;
 for(i=0, j=10; j > 1; j--, i++){
    soma += (parseInt(cpf[i])*j);
 }
 resultado = parseInt(soma/11)*11;
 digito1 = ((soma-resultado)==0 || (soma-resultado)==1) ? 0: 11-(soma-resultado);
 alert(digito1 + "=>" +cpf[9]);
 soma = 0;
 for(i=0, j=11; j > 1; j--, i++){
    soma += (parseInt(cpf[i])*j);
 }
 resultado = parseInt(soma/11)*11;
 digito2 = ((soma-resultado)==0 || (soma-resultado)==1) ? 0: 11-(soma-resultado);
 alert(digito2 + "=>" +cpf[10]);
 if((parseInt(digito1) == parseInt(cpf[9])) && (parseInt(digito2) == parseInt(cpf[10])))
   alert(true);
 else alert(false);
}

function cpfKeyPress(){
 cpf = document.getElementById("cpf")
 if(cpf.value.length==3 || cpf.value.length==7)
   cpf.value += "."
   if(cpf.value.length==11)
     cpf.value += "-"
}

function URLRedirect(component){
 document.getElementById("confirma").value = component
 document.getElementById("formConfirma").submit()
}