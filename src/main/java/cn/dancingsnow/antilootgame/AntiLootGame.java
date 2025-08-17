package cn.dancingsnow.antilootgame;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;

@Mod(modid = AntiLootGame.MODID, version = Tags.VERSION, name = "AntiLootGame", acceptedMinecraftVersions = "[1.7.10]")
public class AntiLootGame {

    public static final String MODID = "antilootgame";
    public static final Logger LOG = LogManager.getLogger(MODID);

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @SidedProxy(
        clientSide = "cn.dancingsnow.antilootgame.ClientProxy",
        serverSide = "cn.dancingsnow.antilootgame.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }
}
