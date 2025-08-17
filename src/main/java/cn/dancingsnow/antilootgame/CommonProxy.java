package cn.dancingsnow.antilootgame;

import cn.dancingsnow.antilootgame.waila.WailaCompat;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import ru.timeconqueror.lootgames.registry.LGBlocks;

public class CommonProxy {

    public void loadComplete(FMLLoadCompleteEvent event) {
        ModuleRegistrar.instance().registerHeadProvider(WailaCompat.INSTANCE, LGBlocks.MS_MASTER.getClass());
        ModuleRegistrar.instance()
            .registerBodyProvider(WailaCompat.INSTANCE, LGBlocks.SMART_SUBORDINATE.getClass());
        ModuleRegistrar.instance()
            .registerNBTProvider(WailaCompat.INSTANCE, LGBlocks.SMART_SUBORDINATE.getClass());
    }

}
