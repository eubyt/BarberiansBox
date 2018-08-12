package unix.caixa;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class UnixPlugin extends JavaPlugin {

	private static JavaPlugin plugin;
	public void onEnable() {
	   Ativar();
	   plugin = this;
	}
	
	public void onDisable() {
		Desativar();
	}
	

	public abstract void Ativar();
	public abstract void Desativar();
    public static JavaPlugin getPlugin() { return plugin; }
}
