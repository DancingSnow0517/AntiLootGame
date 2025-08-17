package cn.dancingsnow.antilootgame;

import cn.dancingsnow.antilootgame.network.RequestTypePacket;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import mcp.mobius.waila.overlay.RayTracing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import ru.timeconqueror.lootgames.utils.future.BlockPos;

public class ClientProxy extends CommonProxy {

    private static boolean keyDown = false;
    public static final KeyBinding requestTypeKey = new KeyBinding(
        "key.antilootgame.requestType",
        Keyboard.KEY_R,
        "key.categories.antilootgame"
    );

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ClientRegistry.registerKeyBinding(requestTypeKey);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayer player = mc.thePlayer;

        if (world == null || player == null) {
            RayTracing.instance().clear();
            return;
        }
        if (requestTypeKey.isPressed()) {
            if (!keyDown) {
                keyDown = true;
                RayTracing.instance().fire();
                MovingObjectPosition target = RayTracing.instance().getTarget();
                if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    AntiLootGame.NETWORK.sendToServer(new RequestTypePacket(new BlockPos(target.blockX, target.blockY, target.blockZ)));
                }
            }
        } else {
            keyDown = false;
        }
    }
}
