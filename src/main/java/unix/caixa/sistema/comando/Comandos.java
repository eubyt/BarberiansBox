package unix.caixa.sistema.comando;

import unix.caixa.sistema.UnixCaixa;

public abstract class Comandos implements ExecutarComando {


	public String nome_comando, descricao, perm;

	public Comandos(String nome_comando, String desc, String perm) {

		this.nome_comando = nome_comando;
		this.descricao = desc;
		this.perm = perm;

		UnixCaixa.lista_comandos.put(nome_comando, this);

	}


	
	
}
