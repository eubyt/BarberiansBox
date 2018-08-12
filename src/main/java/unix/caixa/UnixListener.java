package unix.caixa;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class UnixListener implements Listener {
	
	public UnixListener(JavaPlugin plugin) {
              plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

}
