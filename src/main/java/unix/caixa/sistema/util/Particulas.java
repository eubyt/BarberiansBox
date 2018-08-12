package unix.caixa.sistema.util;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;



public enum Particulas
{
  BARRIER("BARRIER", 35, 8),  BLOCK_CRACK("BLOCK_CRACK", 37, 8),  BLOCK_DUST("BLOCK_DUST", 38, 8),  CLOUD("CLOUD", 29, 8),  CRIT("CRIT", 9, 8),  CRIT_MAGIC("CRIT_MAGIC", 10, 8),  DAMAGE_INDICATOR("DAMAGE_INDICATOR", 44, 9),  DRAGON_BREATH("DRAGON_BREATH", 42, 9),  DRIP_LAVA("DRIP_LAVA", 19, 8),  DRIP_WATER("DRIP_WATER", 18, 8),  ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 25, 8),  END_ROD("END_ROD", 43, 9),  EXPLOSION_HUGE("EXPLOSION_HUGE", 2, 8),  EXPLOSION_LARGE("EXPLOSION_LARGE", 1, 8),  EXPLOSION_NORMAL("EXPLOSION_NORMAL", 0, 8),  FALLING_DUST("FALLING_DUST", 46, 10),  FIREWORKS_SPARK("FIREWORKS_SPARK", 3, 8),  FLAME("FLAME", 26, 8),  FOOTSTEP("FOOTSTEP", 28, 8),  HEART("HEART", 34, 8),  ITEM_CRACK("ITEM_CRACK", 36, 8),  ITEM_TAKE("ITEM_TAKE", 40, 8),  LAVA("LAVA", 27, 8),  MOB_APPEARANCE("MOB_APPEARANCE", 41, 8),  NOTE("NOTE", 23, 8),  PORTAL("PORTAL", 24, 8),  REDSTONE("REDSTONE", 30, 8),  SLIME("SLIME", 33, 8),  SMOKE_LARGE("SMOKE_LARGE", 12, 8),  SMOKE_NORMAL("SMOKE_NORMAL", 11, 8),  SNOW_SHOVEL("SNOW_SHOVEL", 32, 8),  SNOWBALL("SNOWBALL", 31, 8),  SPELL("SPELL", 13, 8),  SPELL_INSTANT("SPELL_INSTANT", 14, 8),  SPELL_MOB("SPELL_MOB", 15, 8),  SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 16, 8),  SPELL_WITCH("SPELL_WITCH", 17, 8),  SPIT("SPIT", 48, 11),  SUSPENDED("SUSPENDED", 7, 8),  SUSPENDED_DEPTH("SUSPENDED_DEPTH", 8, 8),  SWEEP_ATTACK("SWEEP_ATTACK", 45, 9),  TOTEM("TOTEM", 47, 11),  TOWN_AURA("TOWN_AURA", 22, 8),  VILLAGER_ANGRY("VILLAGER_ANGRY", 20, 8),  VILLAGER_HAPPY("VILLAGER_HAPPY", 21, 8),  WATER_BUBBLE("WATER_BUBBLE", 4, 8),  WATER_DROP("WATER_DROP", 39, 8),  WATER_SPLASH("WATER_SPLASH", 5, 8),  WATER_WAKE("WATER_WAKE", 6, 8);
  
  private String name;
  private int id;
  private int requiredVersion;
  int maxRange = 128;
  
  private Particulas(String name, int id, int requiredVersion)
  {
    this.name = name;
    this.id = id;
    this.requiredVersion = requiredVersion;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getID()
  {
    return this.id;
  }
  
  public int getRequiredVersion()
  {
    return this.requiredVersion;
  }
  
  public boolean isVersionSupported()
  {
    return ParticlePacket.getVersion() >= getRequiredVersion();
  }
  
  public void display(Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount)
  {
    if (!isVersionSupported())
    {
      
      return;
    }
    new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, true, null).sendTo(location, this.maxRange);
  }
  
  public void display(Location location, float offsetX, float offsetY, float offsetZ, int amount)
  {
    if (!isVersionSupported())
    {
    
      return;
    }
    new ParticlePacket(this, offsetX, offsetY, offsetZ, 0.0F, amount, true, null).sendTo(location, this.maxRange);
  }
  
  public void display(Location location, float offsetX, float offsetY, float offsetZ, float speed)
  {
    if (!isVersionSupported())
    {
 
      return;
    }
    new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, 1, true, null).sendTo(location, this.maxRange);
  }
  
  public void display(Location location, float offsetX, float offsetY, float offsetZ)
  {
    if (!isVersionSupported())
    {
   
      return;
    }
    new ParticlePacket(this, offsetX, offsetY, offsetZ, 0.0F, 1, true, null).sendTo(location, this.maxRange);
  }
  
  public void display(Location location, float speed, int amount)
  {
    if (!isVersionSupported())
    {
 
      return;
    }
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, speed, amount, true, null).sendTo(location, this.maxRange);
  }
  
  public void display(Location location, int amount)
  {
    if (!isVersionSupported())
    {
    
      return;
    }
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, 0.0F, amount, true, null).sendTo(location, this.maxRange);
  }
  
  public void display(Location location, float speed)
  {
    if (!isVersionSupported())
    {

      return;
    }
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, speed, 1, true, null).sendTo(location, this.maxRange);
  }
  
  public void display(Location location)
  {
    if (!isVersionSupported())
    {

      return;
    }
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, 0.0F, 1, true, null).sendTo(location, this.maxRange);
  }
  
  public void displayRandomColor(Location location)
  {
    new ParticlePacket(this, new OrdinaryColor(0, 255, 0), true).sendTo(location, this.maxRange);
  }
  
  public void displayColor(Location location, int red, int green, int blue)
  {
    new ParticlePacket(this, new OrdinaryColor(red, green, blue), true).sendTo(location, this.maxRange);
  }
  
  public void display(ItemData data, Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount)
  {
    new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(ItemData data, Location location, float offsetX, float offsetY, float offsetZ)
  {
    new ParticlePacket(this, offsetX, offsetY, offsetZ, 0.0F, 1, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(ItemData data, Location location, float speed, int amount)
  {
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, speed, amount, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(ItemData data, Location location, float speed)
  {
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, speed, 1, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(ItemData data, Location location, int amount)
  {
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, 0.0F, amount, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(BlockData data, Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount)
  {
    new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(BlockData data, Location location, float offsetX, float offsetY, float offsetZ)
  {
    new ParticlePacket(this, offsetX, offsetY, offsetZ, 0.0F, 1, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(BlockData data, Location location, float speed, int amount)
  {
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, speed, amount, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(BlockData data, Location location, float speed)
  {
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, speed, 1, true, data).sendTo(location, this.maxRange);
  }
  
  public void display(BlockData data, Location location, int amount)
  {
    new ParticlePacket(this, 0.0F, 0.0F, 0.0F, 0.0F, amount, true, data).sendTo(location, this.maxRange);
  }
  
  public static final class ParticlePacket
  {
    private static int version;
    private static Class<?> enumParticle;
    private static Constructor<?> packetConstructor;
    private static Method getHandle;
    private static Field playerConnection;
    private static Method sendPacket;
    private static boolean initialized;
    private final Particulas effect;
    private float offsetX;
    private final float offsetY;
    private final float offsetZ;
    private float speed;
    private int amount;
    private final boolean longDistance;
    private final Particulas.ParticleData data;
    private Object packet;
    
    public ParticlePacket(Particulas effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, Particulas.ParticleData data)
      throws IllegalArgumentException
    {
      initialize();
      this.effect = effect;
      this.offsetX = offsetX;
      this.offsetY = offsetY;
      this.offsetZ = offsetZ;
      this.speed = speed;
      this.amount = amount;
      this.longDistance = longDistance;
      this.data = data;
      if (speed < 0.0F)
      {
        this.speed = 0.0F;
        throw new IllegalArgumentException("The speed is lower than 0");
      }
      if (amount < 0)
      {
        this.amount = 1;
        throw new IllegalArgumentException("The amount is lower than 0");
      }
    }
    
    public ParticlePacket(Particulas effect, Vector direction, float speed, boolean longDistance, Particulas.ParticleData data)
      throws IllegalArgumentException
    {
      this(effect, (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, 0, longDistance, data);
    }
    
    public ParticlePacket(Particulas effect, Particulas.ParticleColor color, boolean longDistance)
    {
      this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1.0F, 0, longDistance, null);
      if ((effect == Particulas.REDSTONE) && ((color instanceof Particulas.OrdinaryColor)) && 
        (((Particulas.OrdinaryColor)color).getRed() == 0)) {
        this.offsetX = 1.17549435E-38F;
      }
    }
    
    public static void initialize()
      throws Particulas.ParticlePacket.VersionIncompatibleException
    {
      if (initialized) {
        return;
      }
      try
      {
        version = Integer.parseInt(ReflectionUtils.PackageType.getServerVersion().split("\\_")[1]);
        if (version > 7) {
          enumParticle = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
        }
        Class<?> packetClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutWorldParticles");
        packetConstructor = ReflectionUtils.getConstructor(packetClass, new Class[0]);
        getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle", new Class[0]);
        playerConnection = ReflectionUtils.getField("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, 
          "playerConnection");
        sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", new Class[] {
          ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet") });
      }
      catch (Exception exception)
      {
        throw new VersionIncompatibleException(
          "Your current bukkit version seems to be incompatible with this library", exception);
      }
      initialized = true;
    }
    
    public static int getVersion()
    {
      if (!initialized) {
        initialize();
      }
      return version;
    }
    
    public static boolean isInitialized()
    {
      return initialized;
    }
    
    private void initializePacket(Location center)
      throws Particulas.ParticlePacket.PacketInstantiationException
    {
      if (this.packet != null) {
        return;
      }
      try
      {
        this.packet = packetConstructor.newInstance(new Object[0]);
        ReflectionUtils.setValue(this.packet, true, "a", enumParticle.getEnumConstants()[this.effect.getID()]);
        ReflectionUtils.setValue(this.packet, true, "j", Boolean.valueOf(this.longDistance));
        if (this.data != null)
        {
          int[] packetData = this.data.getPacketData();
          ReflectionUtils.setValue(this.packet, true, "k", 
            new int[] {(Integer) (this.effect == Particulas.ITEM_CRACK ? packetData : packetData[0] | packetData[1] << 12) });
        }
        ReflectionUtils.setValue(this.packet, true, "b", Float.valueOf((float)center.getX()));
        ReflectionUtils.setValue(this.packet, true, "c", Float.valueOf((float)center.getY()));
        ReflectionUtils.setValue(this.packet, true, "d", Float.valueOf((float)center.getZ()));
        ReflectionUtils.setValue(this.packet, true, "e", Float.valueOf(this.offsetX));
        ReflectionUtils.setValue(this.packet, true, "f", Float.valueOf(this.offsetY));
        ReflectionUtils.setValue(this.packet, true, "g", Float.valueOf(this.offsetZ));
        ReflectionUtils.setValue(this.packet, true, "h", Float.valueOf(this.speed));
        ReflectionUtils.setValue(this.packet, true, "i", Integer.valueOf(this.amount));
      }
      catch (Exception exception)
      {
        throw new PacketInstantiationException("Packet instantiation failed", exception);
      }
    }
    
    public void sendTo(Location center, Player player)
      throws Particulas.ParticlePacket.PacketInstantiationException, Particulas.ParticlePacket.PacketSendingException
    {
      initializePacket(center);
      try
      {
        sendPacket.invoke(playerConnection.get(getHandle.invoke(player, new Object[0])), new Object[] { this.packet });
      }
      catch (Exception exception)
      {
        throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", 
          exception);
      }
    }
    
    public void sendTo(Location center, List<Player> players)
      throws IllegalArgumentException
    {
      if (players.isEmpty()) {
        throw new IllegalArgumentException("The player list is empty");
      }
      for (Player player : players) {
        sendTo(center, player);
      }
    }
    
    public void sendTo(Location center, double range)
      throws IllegalArgumentException
    {
      if (range < 1.0D) {
        throw new IllegalArgumentException("The range is lower than 1");
      }
      String worldName = center.getWorld().getName();
      double squared = range * range;
      for (Player player : Bukkit.getOnlinePlayers()) {
        if ((player.getWorld().getName().equals(worldName)) && 
          (player.getLocation().distanceSquared(center) <= squared)) {
          sendTo(center, player);
        }
      }
    }
    
    private static final class VersionIncompatibleException
      extends RuntimeException
    {
      private static final long serialVersionUID = 3203085387160737484L;
      
      public VersionIncompatibleException(String message, Throwable cause)
      {
        super(cause);
      }
    }
    
    private static final class PacketInstantiationException
      extends RuntimeException
    {
      private static final long serialVersionUID = 3203085387160737484L;
      
      public PacketInstantiationException(String message, Throwable cause)
      {
        super(cause);
      }
    }
    
    private static final class PacketSendingException
      extends RuntimeException
    {
      private static final long serialVersionUID = 3203085387160737484L;
      
      public PacketSendingException(String message, Throwable cause)
      {
        super(cause);
      }
    }
  }
  
  public static abstract class ParticleData
  {
    private final Material material;
    private final byte data;
    private final int[] packetData;
    
    public ParticleData(Material material, byte data)
    {
      this.material = material;
      this.data = data;
      this.packetData = new int[] { material.getId(), data };
    }
    
    public Material getMaterial()
    {
      return this.material;
    }
    
    public byte getData()
    {
      return this.data;
    }
    
    public int[] getPacketData()
    {
      return this.packetData;
    }
    
    public String getPacketDataString()
    {
      return "_" + this.packetData[0] + "_" + this.packetData[1];
    }
  }
  
  public static final class ItemData
    extends Particulas.ParticleData
  {
    public ItemData(Material material, byte data)
    {
      super(material, data);
    }
  }
  
  public static final class BlockData
    extends Particulas.ParticleData
  {
    public BlockData(Material material, byte data)
      throws IllegalArgumentException
    {
      super(material, data);
      if (!material.isBlock()) {
        throw new IllegalArgumentException("The material is not a block");
      }
    }
  }
  
  public static final class OrdinaryColor
    extends Particulas.ParticleColor
  {
    private final int red;
    private final int green;
    private final int blue;
    
    public OrdinaryColor(int red, int green, int blue)
      throws IllegalArgumentException
    {
      if (red < 0) {
        throw new IllegalArgumentException("The red  is lower than 0");
      }
      if (red > 255) {
        throw new IllegalArgumentException("The red  is higher than 255");
      }
      this.red = red;
      if (green < 0) {
        throw new IllegalArgumentException("The green  is lower than 0");
      }
      if (green > 255) {
        throw new IllegalArgumentException("The green  is higher than 255");
      }
      this.green = green;
      if (blue < 0) {
        throw new IllegalArgumentException("The blue  is lower than 0");
      }
      if (blue > 255) {
        throw new IllegalArgumentException("The blue  is higher than 255");
      }
      this.blue = blue;
    }
    
    public OrdinaryColor(Color color)
    {
      this(color.getRed(), color.getGreen(), color.getBlue());
    }
    
    public int getRed()
    {
      return this.red;
    }
    
    public int getGreen()
    {
      return this.green;
    }
    
    public int getBlue()
    {
      return this.blue;
    }
    
    public float getValueX()
    {
      return this.red / 255.0F;
    }
    
    public float getValueY()
    {
      return this.green / 255.0F;
    }
    
    public float getValueZ()
    {
      return this.blue / 255.0F;
    }
  }
  
  public static final class NoteColor
    extends Particulas.ParticleColor
  {
    private final int note;
    
    public NoteColor(int note)
      throws IllegalArgumentException
    {
      if (note < 0) {
        throw new IllegalArgumentException("The note  is lower than 0");
      }
      if (note > 24) {
        throw new IllegalArgumentException("The note  is higher than 24");
      }
      this.note = note;
    }
    
    public float getValueX()
    {
      return this.note / 24.0F;
    }
    
    public float getValueY()
    {
      return 0.0F;
    }
    
    public float getValueZ()
    {
      return 0.0F;
    }
  }
  
  public static abstract class ParticleColor
  {
    public abstract float getValueX();
    
    public abstract float getValueY();
    
    public abstract float getValueZ();
  }
}
