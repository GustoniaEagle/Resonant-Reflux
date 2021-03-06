package com.eagle.resonantreflux;

import com.eagle.resonantreflux.gui.GuiHandler;
import com.eagle.resonantreflux.integration.ItemStealer;
import com.eagle.resonantreflux.integration.ModChecker;
import com.eagle.resonantreflux.networking.PacketHandler;
import com.eagle.resonantreflux.registry.BlockRegistry;
import com.eagle.resonantreflux.registry.ItemRegistry;
import com.eagle.resonantreflux.registry.RecipeRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Dictionary.MOD_ID, version = Dictionary.VERSION, dependencies = "required-after:Forge@[10.13.2.1291,);after:ThermalExpansion;after:EnderIO;after:ThermalFoundation")
public class ResonantReflux
{
    @Mod.Instance(Dictionary.MOD_ID)
    public static ResonantReflux instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        BlockRegistry.load();
        ItemRegistry.load();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        PacketHandler.init();
        ModChecker.check();
        ItemStealer.load();
        RecipeRegistry.load();
    }
}
