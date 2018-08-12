package unix.caixa.sistema.traducao.linguagem;

import unix.caixa.sistema.traducao.Traducoes;

public class PT_BR implements Traducoes {


	@Override
	public String PrefixPlugin() {
		return "§a[BarberiansBoxs]";
	}

	@Override
	public String NomeCaixa() {	return "§aCaixa Misteriosa"; }

	@Override
	public String NomeCaixaBau() { return "Caixa Misteriosa"; }

	@Override
	public String ClickParaAbrir() { return "§e§lCLICK PARA ABRIR"; }

	@Override
	public String ErroBauNaoEncontrado() { return "§aNão existem nenhum bau em baixo de você."; }

	@Override
	public String NomeItemQuandoNaoTemNadaParaAbrir() { return "§cAh não!"; }

	@Override
	public String[] DescItemQuandoNaoTemNadaParaAbrir() { return new String[] { "§fVocê não tem " + NomeCaixa() + "!", "§fVocê consegue as " + NomeCaixa(), "§fjogando ou comprando em nosso site", "§eloja.teste.com.br"  }; }

	@Override
	public String DescComando() { return "Comandos da " + NomeCaixa(); }

	@Override
	public String MsgErro() { return "§cOcorreu um erro ao tentar acessar o " + NomeCaixa() + "\n§cTente novamente daqui um tempo, se o erro persistir contate nossa equipe."; }

	@Override
	public String NomeItemErro() { return "§cOPS, Ocorreu um erro."; }

	@Override
	public String[] DescItemErro() { return new String[] { "§fExiste um erro ao tentar carregar seus baús", "§ftente relogar no servidor." }; }

	@Override
	public String CaixaSendoAberta() {
		return "§cUma caixa já esta sendo aberta, aguarde para poder abrir a sua.";
	}

	@Override
	public String ListaDeComandosTitulo() { return  PrefixPlugin()  +" §eComandos do sistema de caixas:"; }

	@Override
	public String ListaDeComandos() { return "§a /{comando} - §e {descricao}"; }

	@Override
	public String VersaoComando() { return PrefixPlugin()  + " §eVersão:§a {versao}"; }

	@Override
	public String CaixaListaComandosTitulo() { return PrefixPlugin()  + " Lista de caixas:"; }

	@Override
	public String CaixaListaComandosLista(){ return "§e#{numero} §a{caixaid}"; }

	@Override
	public String CaixaListaComandosListaShow() { return "§fInformações:\n§fMundo: §a{mundo}\n§fCoordenadas:§a {cord}\n \n§cClick para remover está caixa.\n§6ID#{caixaid}"; }

	@Override
	public String CaixaListaComandosListaTotalCaixas() { return "§eTotal de §a{totalcaixa} §ecaixas."; }

	@Override
	public String CaixaListaComandosRemover() { return PrefixPlugin()  +  "§eVocê removeu a caixa de ID:§a {caixaid}§e com sucesso."; }

	@Override
	public String CaixaListaComandosRemoverInformar() { return PrefixPlugin()  +  "§eVocê deve informar o ID da caixa que deseja remover."; }

	@Override
	public String CaixaListaComandosRemoverInformarNaoEncontrado() { return PrefixPlugin() + "§e Nenhuma caixa com o valor ID:§a §a{caixaid}§e foi encontrado.";}

	@Override
	public String CaixaAdicionarComandoInfo() { return PrefixPlugin() + "§e Use: /caixa adicionar [jogador] [tipo_de_caixa]";}

	@Override
	public String CaixaJogadorNaoOnline() { return PrefixPlugin() + "§e Jogador {nome} não foi encontrando.";}

	@Override
	public String CaixaTipoDeBauNaoExiste() { return PrefixPlugin() + "§e A caixa {nome} não foi encontranda.";}

	@Override
	public String CaixaAdicionarJogador() { return PrefixPlugin() + "§e A caixa §a{nome_bau}§e, adicionada com sucesso a conta do jogador§a {nome_jogador}";}

	@Override
	public String HologramaNumeroCaixas() { return "{numero_caixas} Disponível!";}
}
