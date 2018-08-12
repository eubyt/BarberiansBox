package unix.caixa.sistema.traducao;

import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.traducao.linguagem.*;

public class TraducaoUtil {

	private static Traducoes traducao = UnixCaixa.linguagem.carregar;
	
	public static String NomeCaixa = traducao.NomeCaixa();
	public static String NomeCaixaBau = traducao.NomeCaixaBau();
	public static String ClickParaAbrir = traducao.ClickParaAbrir();
	public static String ErroBauNaoEncontrado = traducao.ErroBauNaoEncontrado();
	public static String NomeItemQuandoNaoTemNadaParaAbrir = traducao.NomeItemQuandoNaoTemNadaParaAbrir();
	public static String[] DescItemQuandoNaoTemNadaParaAbrir = traducao.DescItemQuandoNaoTemNadaParaAbrir();
	public static String DescComando = traducao.DescComando();
	public static String MsgErro = traducao.MsgErro();
	public static String NomeItemErro = traducao.NomeItemErro();
	public static String[] DescItemErro = traducao.DescItemErro();
	public static String CaixaSendoAberta = traducao.CaixaSendoAberta();
	public static String PrefixPlugin = traducao.PrefixPlugin();
	public static String ListaDeComandosTitulo = traducao.ListaDeComandosTitulo();
	public static String ListaDeComandos = traducao.ListaDeComandos();
	public static String VersaoComando = traducao.VersaoComando();
	public static String CaixaListaComandosTitulo = traducao.CaixaListaComandosTitulo();
	public static String CaixaListaComandosLista =  traducao.CaixaListaComandosLista();
	public static String CaixaListaComandosListaShow =  traducao.CaixaListaComandosListaShow();
	public static String CaixaListaComandosListaTotalCaixas =  traducao.CaixaListaComandosListaTotalCaixas();
	public static String CaixaListaComandosRemover =  traducao.CaixaListaComandosRemover();
	public static String CaixaListaComandosRemoverInformar =  traducao.CaixaListaComandosRemoverInformar();
	public static String CaixaListaComandosRemoverInformarNaoEncontrado =  traducao.CaixaListaComandosRemoverInformarNaoEncontrado();
	public static String CaixaAdicionarComandoInfo =  traducao.CaixaAdicionarComandoInfo();
	public static String CaixaJogadorNaoOnline =  traducao.CaixaJogadorNaoOnline();
	public static String CaixaTipoDeBauNaoExiste =  traducao.CaixaTipoDeBauNaoExiste();
	public static String CaixaAdicionarJogador =  traducao.CaixaAdicionarJogador();
	public static String HologramaNumeroCaixas =  traducao.HologramaNumeroCaixas();

	public static enum TiposTraducoes {
		
		PT_BR(new PT_BR());
		
		Traducoes carregar;
		
		TiposTraducoes(Traducoes traducoes) {
			this.carregar = traducoes;
		}
	}
}
