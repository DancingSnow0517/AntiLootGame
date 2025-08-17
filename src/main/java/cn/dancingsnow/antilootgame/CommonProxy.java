package cn.dancingsnow.antilootgame;

import cn.dancingsnow.antilootgame.network.RequestTypePacket;
import cn.dancingsnow.antilootgame.network.RequestTypePacketHandler;

import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {

    public void loadComplete(FMLLoadCompleteEvent event) {

    }

    public void preInit(FMLPreInitializationEvent event) {
        AntiLootGame.NETWORK.registerMessage(RequestTypePacketHandler.class, RequestTypePacket.class, 0, Side.SERVER);
    }
}
