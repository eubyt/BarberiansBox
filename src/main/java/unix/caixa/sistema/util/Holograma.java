package unix.caixa.sistema.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import unix.caixa.sistema.UnixCaixa;

public class Holograma {

    private static String versao;
    private static Class<?> craftWorld, entityClass, nmsWorld, armorStand, entityLiving, spawnPacket;

    static {
        versao = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";

        try {
            craftWorld = Class.forName("org.bukkit.craftbukkit." + versao + "CraftWorld");
            entityClass = Class.forName("net.minecraft.server." + versao + "Entity");
            nmsWorld = Class.forName("net.minecraft.server." + versao + "World");
            armorStand = Class.forName("net.minecraft.server." + versao + "EntityArmorStand");
            entityLiving = Class.forName("net.minecraft.server." + versao + "EntityLiving");
            spawnPacket = Class.forName("net.minecraft.server." + versao + "PacketPlayOutSpawnEntityLiving");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Location local;
    
    private List<String> titulo = new ArrayList<String>();
    
    private List<Integer> ids = new ArrayList<Integer>();
    private List<Object> amorstand = new ArrayList<Object>();

    public Holograma(Location local, String... text) {
        this.local = local;
        addTexto(text);
    }

    public Holograma(String... text) {
        this(null, text);
    }

    public Holograma(Location local) {
        this(local, null);
    }

    public Holograma() {
        this(null, null);
    }


    public void addTexto(String... texto) {
        titulo.addAll(Arrays.asList(texto));
        update();
    }

    public void Mostrar(Player... players) {
        Location current = local;

        for (String str : titulo) {
            Object[] packet = getCreatePacket(local, str);
            ids.add((Integer) packet[1]);

            for (Player player : players) {
                sendPacket(player, packet[0]);
            }

            current = current.add(0,-0.30,0);
        }
    }


    public void RemoverPara(Player... players) {
        Object packet = null;

        for (int id : ids)
            packet = getRemovePacket(id);

        for (Player player : players)
            if (packet != null)
                sendPacket(player, packet);
    }


    public void spawn() {
        Location local = this.local;

        for (String str : titulo) {
            CriarHolograma(str, local);
            local = local.add(0,-0.30,0);   
        }
    }
    


    private void CriarHolograma(String texto, Location loc) {
            ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setCustomName(texto);
            stand.setCustomNameVisible(true);
            amorstand.add(stand);    
    }

    private Object[] getCreatePacket(Location local, String texto) {
        try {

            Object objeto = armorStand.getConstructor(nmsWorld).newInstance(craftWorld.getMethod("getHandle").invoke(craftWorld.cast(local.getWorld())));
            Object id = objeto.getClass().getMethod("getId").invoke(objeto);

            ConfigHolograma(objeto, texto, local);

            return new Object[]{spawnPacket.getConstructor(entityLiving).newInstance(objeto), id};
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private Object getRemovePacket(int id) {
        try {
            Class<?> packet = Class.forName("net.minecraft.server." + versao + "PacketPlayOutEntityDestroy");
            return packet.getConstructor(int[].class).newInstance(new int[]{id});
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void update() {
        try {
            if (!amorstand.isEmpty()) { 

                for (int i = 0; i < amorstand.size(); i++) {
                    Object ent = amorstand.get(i);

                    if (i > titulo.size() - 1) 
                        remover(ent);
                }

                Location loc = local;

                for (int i = 0; i < titulo.size(); i++) {
                    String text = titulo.get(i);

                    if (i >= amorstand.size()) {
                        CriarHolograma(text, loc);
                        
                    } else {
                        ConfigHolograma(amorstand.get(i), text, loc);
                    }
                    loc = loc.add(0,-0.30,0);
                }

            } else { 

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ConfigHolograma(Object entityObject, String text, Location location) throws Exception {
        Method setCustomName = entityObject.getClass().getMethod("setCustomName", String.class);
        Method setCustomNameVisible = entityObject.getClass().getMethod("setCustomNameVisible", boolean.class);
        Method setNoGravity = entityObject.getClass().getMethod("setGravity", boolean.class);
        Method setLocation = entityObject.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
        Method setInvisible = entityObject.getClass().getMethod("setInvisible", boolean.class);

        setCustomName.invoke(entityObject, text);
        setCustomNameVisible.invoke(entityObject, true);
        setNoGravity.invoke(entityObject, false);
        setLocation.invoke(entityObject, location.getX(), location.getY(), location.getZ(), 0.0F, 0.0F);
        setInvisible.invoke(entityObject, true);
    }

    private void sendPacket(Player player, Object packet) {
        try {
            if (packet == null)
                return;

            Object alvo = player.getClass().getMethod("getHandle").invoke(player);
            Object conexao = alvo.getClass().getField("playerConnection").get(alvo);
            conexao.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + versao + "Packet")).invoke(conexao, packet);
            
        } catch (Exception ex) { ex.printStackTrace();}
    }
    
    
    
    
    //Remover o Holograma
    public void remover() { for (Object ent : amorstand)  remover(ent); }
    private void remover(Object entity) {  try {  Object craftWorld = Holograma.craftWorld.cast(local.getWorld()); nmsWorld.getMethod("removeEntity", entityClass).invoke(Holograma.craftWorld.getMethod("getHandle").invoke(craftWorld), entity); } catch (Exception ex) {   ex.printStackTrace(); }  }


}