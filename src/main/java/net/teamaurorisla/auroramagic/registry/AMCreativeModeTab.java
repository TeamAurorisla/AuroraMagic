package net.teamaurorisla.auroramagic.registry;

import com.dark2932.darklib.register.CreativeTabRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;
import net.teamaurorisla.auroramagic.AuroraMagic;

import static net.teamaurorisla.auroramagic.registry.AMBlockItem.ARCANE_PEDESTAL_ITEM;
import static net.teamaurorisla.auroramagic.registry.AMItem.BLAZE_FOCUS;
import static net.teamaurorisla.auroramagic.registry.AMItem.EARTH_FOCUS;
import static net.teamaurorisla.auroramagic.registry.AMItem.EXAMPLE_ITEM;
import static net.teamaurorisla.auroramagic.registry.AMItem.FROST_FOCUS;
import static net.teamaurorisla.auroramagic.registry.AMItem.STORM_FOCUS;

public final class AMCreativeModeTab {

    public static final CreativeTabRegister REGISTER = CreativeTabRegister.of(AuroraMagic.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = REGISTER.newTab("example_tab", EXAMPLE_ITEM,
            (parameters, output) -> {
                output.accept(EXAMPLE_ITEM.item());
                output.accept(ARCANE_PEDESTAL_ITEM.get());
                output.accept(BLAZE_FOCUS.get());
                output.accept(FROST_FOCUS.get());
                output.accept(STORM_FOCUS.get());
                output.accept(EARTH_FOCUS.get());
            });

}
