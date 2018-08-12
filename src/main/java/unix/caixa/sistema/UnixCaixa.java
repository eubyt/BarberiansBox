package unix.caixa.sistema;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitRunnable;
import unix.caixa.UnixPlugin;
import unix.caixa.sistema.comando.Comandos;
import unix.caixa.sistema.comando.UnixComando;
import unix.caixa.sistema.data.BausMagicoJogador;
import unix.caixa.sistema.data.array.DataBau;
import unix.caixa.sistema.evento.UnixEvento;
import unix.caixa.sistema.modulo.UnixModulo;
import unix.caixa.sistema.sqlite.Database;
import unix.caixa.sistema.sqlite.UnixSQLite;
import unix.caixa.sistema.traducao.TraducaoUtil.TiposTraducoes;
import unix.caixa.sistema.util.Tempo;

public class UnixCaixa extends UnixPlugin {

	private static UnixEvento ue;
	public static HashMap<Player, BausMagicoJogador> jogadores = new HashMap<>();
	private static String versao;
	public static TiposTraducoes linguagem;
	public static String versao_plugin = "0.1.61";
	public static UnixModulo um;
    private static Class<?> craftServer;
    public static Database database;
	public static HashMap<String, Comandos> lista_comandos = new HashMap<String, Comandos>();
	public static HashMap<String, Location> CaixaLista;
    
   public void Carregar() {
        versao = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";

        try {
            craftServer = Class.forName("org.bukkit.craftbukkit." + versao + "CraftServer");
        } catch (Exception ex) {  ex.printStackTrace();  }
   }
	
	
	@Override
	public void Ativar() {
	//Config
    FileConfiguration config = getConfig();
    config.addDefault("Linguagem", "PT_BR");
    config.options().copyDefaults(true);
    saveConfig();
    linguagem = TiposTraducoes.valueOf(config.get("Linguagem").toString());
		
	//SQLite	
	database = new UnixSQLite(this);
	database.Carregar();
	CaixaLista = UnixCaixa.database.CarregarCaixas();

	this.um =  new UnixModulo();
	this.um.CarregarCaixas();
	
	Carregar();
	this.ue = new UnixEvento(this);
	 
	 //Registrar o comando
	 SimpleCommandMap comando;
	try {
		
		comando = (SimpleCommandMap) craftServer.cast(this.getServer()).getClass().getMethod("getCommandMap").invoke(this.getServer());
	    comando.register("caixa", new UnixComando("caixa"));
	     
	} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
			| SecurityException e) {
		e.printStackTrace();
	}

	new unix.caixa.sistema.comando.lista.Criar();
	new unix.caixa.sistema.comando.lista.Versao();
	new unix.caixa.sistema.comando.lista.Lista();
	new unix.caixa.sistema.comando.lista.Remover();
	new unix.caixa.sistema.comando.lista.Add();



		new BukkitRunnable() {
			public float i = 0.1F;
			public void run(){

				for (BausMagicoJogador jog : UnixCaixa.jogadores.values()) {
					jog.holo.AtualizarListaHoloTotalCaixas(jog.baus.size());
					jog.AtualizarTempoBaus();

				}

			}
		}.runTaskTimer(this, 0, 90);
	}

	@Override
	public void Desativar() {
	
		
	}
	
	public static UnixEvento getUnixEvento() { return ue; }
	
	public static void Carregar(Player jogador) { UnixCaixa.jogadores.put(jogador, new BausMagicoJogador(jogador, "")); }

}
