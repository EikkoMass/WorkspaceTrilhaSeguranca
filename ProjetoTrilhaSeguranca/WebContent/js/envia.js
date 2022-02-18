function validaDados(){
	
	let senhabase64 = btoa(document.frmLogin.senha.value);
	document.frmLogin.senha.value = senhabase64;
	
	
	return true;
}