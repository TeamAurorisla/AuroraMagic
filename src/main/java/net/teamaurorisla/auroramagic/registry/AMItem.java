package net.teamaurorisla.auroramagic.registry;

import com.dark2932.darklib.util.ItemEntry;
import com.dark2932.darklib.register.item.FoodRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.teamaurorisla.auroramagic.AuroraMagic;
import net.teamaurorisla.auroramagic.spell.item.SpellFocusItem;

public final class AMItem {

    public static final FoodRegister REGISTER = FoodRegister.of(AuroraMagic.MODID);
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, AuroraMagic.MODID);

    public static final ItemEntry EXAMPLE_ITEM = REGISTER.newFood("example_item", REGISTER.newFoodProps(1, 2.0f, false, false, true));
    public static final RegistryObject<Item> BLAZE_FOCUS = ITEM.register("blaze_focus", () -> new SpellFocusItem("blaze_burst", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FROST_FOCUS = ITEM.register("frost_focus", () -> new SpellFocusItem("frost_nova", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STORM_FOCUS = ITEM.register("storm_focus", () -> new SpellFocusItem("storm_strike", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> EARTH_FOCUS = ITEM.register("earth_focus", () -> new SpellFocusItem("earth_guard", new Item.Properties().stacksTo(1)));

}
