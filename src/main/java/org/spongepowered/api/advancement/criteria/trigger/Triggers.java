/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.advancement.criteria.trigger;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryScope;
import org.spongepowered.api.registry.RegistryScopes;

/**
 * An enumeration of all the available {@link Trigger}s in minecraft.
 */
@SuppressWarnings("unused")
@RegistryScopes(scopes = RegistryScope.GAME)
public final class Triggers {

    // @formatter:off

    // SORTFIELDS:ON

    public static final DefaultedRegistryReference<Trigger<?>> BAD_OMEN = Triggers.key(ResourceKey.sponge("bad_omen"));

    public static final DefaultedRegistryReference<Trigger<?>> BEE_NEST_DESTROYED = Triggers.key(ResourceKey.sponge("bee_nest_destroyed"));

    public static final DefaultedRegistryReference<Trigger<?>> BRED_ANIMALS = Triggers.key(ResourceKey.sponge("bred_animals"));

    public static final DefaultedRegistryReference<Trigger<?>> BREWED_POTION = Triggers.key(ResourceKey.sponge("brewed_potion"));

    public static final DefaultedRegistryReference<Trigger<?>> CHANGED_DIMENSION = Triggers.key(ResourceKey.sponge("changed_dimension"));

    public static final DefaultedRegistryReference<Trigger<?>> CHANNELED_LIGHTNING = Triggers.key(ResourceKey.sponge("channeled_lightning"));

    public static final DefaultedRegistryReference<Trigger<?>> CONSTRUCT_BEACON = Triggers.key(ResourceKey.sponge("construct_beacon"));

    public static final DefaultedRegistryReference<Trigger<?>> CONSUME_ITEM = Triggers.key(ResourceKey.sponge("consume_item"));

    public static final DefaultedRegistryReference<Trigger<?>> CURED_ZOMBIE_VILLAGER = Triggers.key(ResourceKey.sponge("cured_zombie_villager"));

    public static final DefaultedRegistryReference<Trigger<?>> EFFECTS_CHANGED = Triggers.key(ResourceKey.sponge("effects_changed"));

    public static final DefaultedRegistryReference<Trigger<?>> ENCHANTED_ITEM = Triggers.key(ResourceKey.sponge("enchanted_item"));

    public static final DefaultedRegistryReference<Trigger<?>> ENTER_BLOCK = Triggers.key(ResourceKey.sponge("enter_block"));

    public static final DefaultedRegistryReference<Trigger<?>> ENTITY_HURT_PLAYER = Triggers.key(ResourceKey.sponge("entity_hurt_player"));

    public static final DefaultedRegistryReference<Trigger<?>> ENTITY_KILLED_PLAYER = Triggers.key(ResourceKey.sponge("entity_killed_player"));

    public static final DefaultedRegistryReference<Trigger<?>> FILLED_BUCKET = Triggers.key(ResourceKey.sponge("filled_bucket"));

    public static final DefaultedRegistryReference<Trigger<?>> FISHING_ROD_HOOKED = Triggers.key(ResourceKey.sponge("fishing_rod_hooked"));

    public static final DefaultedRegistryReference<Trigger<?>> GENERATE_LOOT = Triggers.key(ResourceKey.sponge("generate_loot"));

    public static final DefaultedRegistryReference<Trigger<?>> HONEY_BLOCK_SIDE = Triggers.key(ResourceKey.sponge("honey_block_slide"));

    public static final DefaultedRegistryReference<Trigger<?>> HERO_OF_THE_VILLAGE = Triggers.key(ResourceKey.sponge("hero_of_the_village"));

    public static final DefaultedRegistryReference<Trigger<?>> IMPOSSIBLE = Triggers.key(ResourceKey.sponge("impossible"));

    public static final DefaultedRegistryReference<Trigger<?>> INVENTORY_CHANGED = Triggers.key(ResourceKey.sponge("inventory_changed"));

    public static final DefaultedRegistryReference<Trigger<?>> ITEM_DURABILITY_CHANGED = Triggers.key(ResourceKey.sponge("item_durability_changed"));

    public static final DefaultedRegistryReference<Trigger<?>> ITEM_PICKED_UP_BY_ENTITY = Triggers.key(ResourceKey.sponge("item_picked_up_by_entity"));

    public static final DefaultedRegistryReference<Trigger<?>> ITEM_USED_ON_BLOCK = Triggers.key(ResourceKey.sponge("item_used_on_block"));

    public static final DefaultedRegistryReference<Trigger<?>> KILLED_BY_CROSSBOW = Triggers.key(ResourceKey.sponge("killed_by_crossbow"));

    public static final DefaultedRegistryReference<Trigger<?>> LEVITATION = Triggers.key(ResourceKey.sponge("levitation"));

    public static final DefaultedRegistryReference<Trigger<?>> LOCATION = Triggers.key(ResourceKey.sponge("location"));

    public static final DefaultedRegistryReference<Trigger<?>> NETHER_TRAVEL = Triggers.key(ResourceKey.sponge("nether_travel"));

    public static final DefaultedRegistryReference<Trigger<?>> PLACED_BLOCK = Triggers.key(ResourceKey.sponge("placed_block"));

    public static final DefaultedRegistryReference<Trigger<?>> PLAYER_HURT_ENTITY = Triggers.key(ResourceKey.sponge("player_hurt_entity"));

    public static final DefaultedRegistryReference<Trigger<?>> PLAYER_INTERACTED_WITH_ENTITY = Triggers.key(ResourceKey.sponge("player_interacted_with_entity"));

    public static final DefaultedRegistryReference<Trigger<?>> PLAYER_KILLED_ENTITY = Triggers.key(ResourceKey.sponge("player_killed_entity"));

    public static final DefaultedRegistryReference<Trigger<?>> RAID_WIN = Triggers.key(ResourceKey.sponge("raid_win"));

    public static final DefaultedRegistryReference<Trigger<?>> RECIPE_UNLOCKED = Triggers.key(ResourceKey.sponge("recipe_unlocked"));

    public static final DefaultedRegistryReference<Trigger<?>> SHOT_CROSSBOW = Triggers.key(ResourceKey.sponge("shot_crossbow"));

    public static final DefaultedRegistryReference<Trigger<?>> SLEPT_IN_BED = Triggers.key(ResourceKey.sponge("slept_in_bed"));

    public static final DefaultedRegistryReference<Trigger<?>> SLIDE_DOWN_BLOCK = Triggers.key(ResourceKey.sponge("slide_down_block"));

    public static final DefaultedRegistryReference<Trigger<?>> SUMMONED_ENTITY = Triggers.key(ResourceKey.sponge("summoned_entity"));

    public static final DefaultedRegistryReference<Trigger<?>> TAME_ANIMAL = Triggers.key(ResourceKey.sponge("tame_animal"));

    public static final DefaultedRegistryReference<Trigger<?>> TARGET_BLOCK_HIT = Triggers.key(ResourceKey.sponge("target_block_hit"));

    public static final DefaultedRegistryReference<Trigger<?>> TICK = Triggers.key(ResourceKey.sponge("tick"));

    public static final DefaultedRegistryReference<Trigger<?>> USED_ENDER_EYE = Triggers.key(ResourceKey.sponge("used_ender_eye"));

    public static final DefaultedRegistryReference<Trigger<?>> USED_TOTEM = Triggers.key(ResourceKey.sponge("used_totem"));

    public static final DefaultedRegistryReference<Trigger<?>> VILLAGER_TRADE = Triggers.key(ResourceKey.sponge("villager_trade"));

    public static final DefaultedRegistryReference<Trigger<?>> VOLUNTARY_EXILE = Triggers.key(ResourceKey.sponge("voluntary_exile"));

    // SORTFIELDS:OFF

    // @formatter:on

    private Triggers() {
    }

    private static DefaultedRegistryReference<Trigger<?>> key(final ResourceKey location) {
        return RegistryKey.of(RegistryTypes.TRIGGER, location).asDefaultedReference(() -> Sponge.getGame().registries());
    }
}
