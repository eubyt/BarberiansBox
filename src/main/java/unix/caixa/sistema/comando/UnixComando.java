package unix.caixa.sistema.comando;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.traducao.TraducaoUtil;

public class UnixComando extends BukkitCommand {
	
	public UnixComando(String name) {
		super(name);
		
		   this.description = TraducaoUtil.DescComando;
	       this.usageMessage = "/caixa";
	       this.setPermission("barberiansboxs.admin");
	       this.setAliases(new ArrayList<String>());
	}

	@Override
	public boolean execute(CommandSender arg0, String arg1, String[] arg2) {



		if (arg2.length < 1)
		{
			arg0.sendMessage(TraducaoUtil.ListaDeComandosTitulo);
			Iterator comandos = UnixCaixa.lista_comandos.keySet().iterator();

			while(comandos.hasNext()){
				Comandos cmd = UnixCaixa.lista_comandos.get(comandos.next());
				String msg = TraducaoUtil.ListaDeComandos.replace("{comando}", cmd.nome_comando);
				msg = msg.replace("{descricao}", cmd.descricao);
				arg0.sendMessage(msg);
			}


			return false;
		}

		if (UnixCaixa.lista_comandos.containsKey(arg2[0])) {
			UnixCaixa.lista_comandos.get(arg2[0]).Exe(arg0, arg1, arg2);

		} else {

			arg0.sendMessage(TraducaoUtil.ListaDeComandosTitulo);
			Iterator comandos = UnixCaixa.lista_comandos.keySet().iterator();

			while(comandos.hasNext()){
				Comandos cmd = UnixCaixa.lista_comandos.get(comandos.next());
				String msg = TraducaoUtil.ListaDeComandos.replace("{comando}", cmd.nome_comando);
				msg = msg.replace("{descricao}", cmd.descricao);
				arg0.sendMessage(msg);
			}
		}
		
		
		return false;
	}

}
