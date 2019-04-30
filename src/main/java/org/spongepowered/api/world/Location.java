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
package org.spongepowered.api.world;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.property.DirectionRelativePropertyHolder;
import org.spongepowered.api.data.property.Property;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.entity.spawn.SpawnType;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.fluid.FluidState;
import org.spongepowered.api.fluid.FluidType;
import org.spongepowered.api.scheduler.ScheduledUpdate;
import org.spongepowered.api.scheduler.TaskPriority;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.biome.BiomeType;
import org.spongepowered.api.world.volume.entity.MutableEntityVolume;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * A position within a particular {@link World}.
 *
 * <p>Locations are immutable. Methods that change the properties of the
 * location create a new instance.</p>
 */
public interface Location extends DataHolder, DirectionRelativePropertyHolder {

    static Location of(World world, double x, double y, double z) {
        return Sponge.getRegistry().requireFactory(Factory.class).create(world, x, y, z);
    }

    static Location of(World world, Vector3d position) {
        return Sponge.getRegistry().requireFactory(Factory.class).create(world, position.getX(), position.getY(), position.getZ());
    }

    static Location of(World world, int x, int y, int z) {
        return Sponge.getRegistry().requireFactory(Factory.class).create(world, x, y, z);
    }

    static Location of(World world, Vector3i position) {
        return Sponge.getRegistry().requireFactory(Factory.class).create(world, position.getX(), position.getY(), position.getZ());
    }

    /**
     * Gets the underlying world.
     *
     * @return The underlying world.
     */
    World getWorld();

    /**
     * Gets the underlying position.
     *
     * @return The underlying position
     */
    Vector3d getPosition();

    /**
     * Gets the underlying block position.
     *
     * @return The underlying block position
     */
    Vector3i getBlockPosition();

    /**
     * Gets the underlying chunk position.
     *
     * @return The underlying chunk position
     */
    Vector3i getChunkPosition();

    /**
     * Gets the underlying biome position.
     *
     * @return The underlying biome position
     */
    Vector3i getBiomePosition();

    /**
     * Gets the X component of this instance's position.
     *
     * @return The x component
     */
    double getX();

    /**
     * Gets the Y component of this instance's position.
     *
     * @return The y component
     */
    double getY();

    /**
     * Gets the Z component of this instance's position.
     *
     * @return The z component
     */
    double getZ();

    /**
     * Gets the floored X component of this instance's position.
     *
     * @return The floored x component
     */
    int getBlockX();

    /**
     * Gets the floored Y component of this instance's position.
     *
     * @return The floored y component
     */
    int getBlockY();

    /**
     * Gets the floored Z component of this instance's position.
     *
     * @return The floored z component
     */
    int getBlockZ();

    /**
     * Returns true if this location is in the given world. This is implemented
     * as an {@link Object#equals(Object)} check.
     *
     * @param world The world to check
     * @return Whether this location is in the world
     */
    default boolean inWorld(World world) {
        return this.getWorld().equals(world);
    }

    /**
     * Returns true if this location has a biome at its
     * {@link #getBiomePosition()}.
     *
     * @return Whether or not there is a biome at this location.
     */
    default boolean hasBiome() {
        return this.getWorld().containsBiome(this.getBiomePosition());
    }

    /**
     * Returns true if this location has a block at its
     * {@link #getBlockPosition()} ()}.
     *
     * @return Whether or not there is a block at this location.
     */
    default boolean hasBlock() {
        return this.getWorld().containsBlock(this.getBlockPosition());
    }

    /**
     * Gets a {@link LocatableBlock}.
     *
     * @return The locatable block of this location.
     */
    default LocatableBlock asLocatableBlock() {
        return LocatableBlock
            .builder()
            .world(this.getWorld())
            .position(this.getBlockPosition())
            .build();
    }

    /**
     * Create a new instance with a new World.
     *
     * @param world The new world
     * @return A new instance
     */
    default Location withWorld(World world) {
        checkNotNull(world, "world");
        if (world == this.getWorld()) {
            return this;
        }
        return Location.of(world, this.getPosition());
    }

    /**
     * Create a new instance with a new position.
     *
     * @param position The new position
     * @return A new instance
     */
    default Location withPosition(Vector3d position) {
        checkNotNull(position, "position");
        if (position == this.getPosition()) {
            return this;
        }
        return Location.of(this.getWorld(), position);
    }

    /**
     * Create a new instance with a new block position.
     *
     * @param position The new position
     * @return A new instance
     */
    default Location withBlockPosition(Vector3i position) {
        checkNotNull(position, "position");
        if (position == this.getBlockPosition()) {
            return this;
        }
        return Location.of(this.getWorld(), position);
    }

    /**
     * Subtract another Vector3d to the position on this instance, returning
     * a new Location instance.
     *
     * @param v The vector to subtract
     * @return A new instance
     */
    default Location sub(Vector3d v) {
        return this.sub(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Subtract another Vector3i to the position on this instance, returning
     * a new Location instance.
     *
     * @param v The vector to subtract
     * @return A new instance
     */
    default Location sub(Vector3i v) {
        return this.sub(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Subtract vector components to the position on this instance, returning a
     * new Location instance.
     *
     * @param x The x component
     * @param y The y component
     * @param z The z component
     * @return A new instance
     */
    default Location sub(double x, double y, double z) {
        return this.withPosition(this.getPosition().sub(x, y, z));
    }

    /**
     * Add another Vector3d to the position on this instance, returning a new
     * Location instance.
     *
     * @param v The vector to add
     * @return A new instance
     */
    default Location add(Vector3d v) {
        return this.add(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Add another Vector3i to the position on this instance, returning a new
     * Location instance.
     *
     * @param v The vector to add
     * @return A new instance
     */
    default Location add(Vector3i v) {
        return this.add(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Add vector components to the position on this instance, returning a new
     * Location instance.
     *
     * @param x The x component
     * @param y The y component
     * @param z The z component
     * @return A new instance
     */
    default Location add(double x, double y, double z) {
        return this.withPosition(this.getPosition().add(x, y, z));
    }

    /**
     * Calls the mapper function on the world and position.
     *
     * @param mapper The mapper
     * @param <T> The return type of the mapper
     * @return The results of the mapping
     */
    default <T> T map(BiFunction<World, Vector3d, T> mapper) {
        return mapper.apply(this.getWorld(), this.getPosition());
    }

    /**
     * Calls the mapper function on the world and block position.
     *
     * @param mapper The mapper
     * @param <T> The return type of the mapper
     * @return The results of the mapping
     */
    default <T> T mapBlock(BiFunction<World, Vector3i, T> mapper) {
        return mapper.apply(this.getWorld(), this.getBlockPosition());
    }

    /**
     * Calls the mapper function on the world and chunk position.
     *
     * @param mapper The mapper
     * @param <T> The return type of the mapper
     * @return The results of the mapping
     */
    default <T> T mapChunk(BiFunction<World, Vector3i, T> mapper) {
        return mapper.apply(this.getWorld(), this.getChunkPosition());
    }

    /**
     * Calls the mapper function on the world and biome position.
     *
     * @param mapper The mapper
     * @param <T> The return type of the mapper
     * @return The results of the mapping
     */
    default  <T> T mapBiome(BiFunction<World, Vector3i, T> mapper) {
        return mapper.apply(this.getWorld(), this.getBiomePosition());
    }

    /**
     * Gets the location next to this one in the given direction.
     * Always moves by a unit amount, even diagonally.
     *
     * @param direction The direction to move in
     * @return The location in that direction
     */
    default Location relativeTo(Direction direction) {
        return this.add(direction.asOffset());
    }

    /**
     * Gets the location next to this one in the given direction.
     * Always moves by a block amount, even diagonally.
     *
     * <p>{@link org.spongepowered.api.util.Direction.Division#SECONDARY_ORDINAL}
     * directions are not a valid argument. These will throw an exception.
     * </p>
     *
     * @param direction The direction to move in
     * @return The location in that direction
     * @throws IllegalArgumentException If the direction is a
     * {@link org.spongepowered.api.util.Direction.Division#SECONDARY_ORDINAL}
     */
    default Location relativeToBlock(Direction direction) {
        checkArgument(!direction.isSecondaryOrdinal(), "Secondary cardinal directions can't be used here");
        return this.add(direction.asBlockOffset());
    }

    /**
     * Gets the block at this location.
     *
     * @return The biome at this location
     */
    default BiomeType getBiome() {
        return this.getWorld().getBiome(this.getBiomePosition());
    }

    /**
     * Gets the {@link BlockState} for this position.
     *
     * @return The block state
     */
    default BlockState getBlock() {
        return this.getWorld().getBlock(this.getBlockPosition());
    }

    /**
     * Gets the {@link FluidState} for this position.
     *
     * @return The fluid state
     */
    default FluidState getFluid() {
        return this.getWorld().getFluid(getBlockPosition());
    }

    /**
     * Checks for whether the block at this position contains tile entity data.
     *
     * @return True if the block at this position has tile entity data, false
     *      otherwise
     */
    default boolean hasTileEntity() {
        return this.getWorld().getTileEntity(this.getBlockPosition()).isPresent();
    }

    /**
     * Gets the associated {@link TileEntity} on this block.
     *
     * @return The associated tile entity, if available
     */
    default Optional<TileEntity> getTileEntity() {
        return this.getWorld().getTileEntity(this.getBlockPosition());
    }

    /**
     * Replace the block at this position with a new state.
     *
     * <p>This will remove any extended block data at the given position.</p>
     *
     * @param state The new block state
     * @return True if the block change was successful
     */
    default boolean setBlock(BlockState state) {
        return this.getWorld().setBlock(this.getBlockPosition(), state);
    }

    /**
     * Replace the block at this position with a new state.
     *
     * <p>This will remove any extended block data at the given position.</p>
     *  @param state The new block state
     * @param flag The various change flags controlling some interactions
     * @return True if the block change was successful
     */
    default boolean setBlock(BlockState state, BlockChangeFlag flag) {
        return this.getWorld().setBlock(this.getBlockPosition(), state, flag);
    }

    /**
     * Replace the block type at this position by a new type.
     *
     * <p>This will remove any extended block data at the given position.</p>
     *
     * @param type The new type
     * @return True if the block change was successful
     */
    default boolean setBlockType(BlockType type) {
        return this.getWorld().setBlock(this.getBlockPosition(), type.getDefaultState());
    }

    /**
     * Replace the block type at this position by a new type.
     *
     * <p>This will remove any extended block data at the given position.</p>
     * @param type The new type
     * @param flag The various change flags controlling some interactions
     * @return True if the block change was successful
     */
    default boolean setBlockType(BlockType type, BlockChangeFlag flag) {
        return this.getWorld().setBlock(this.getBlockPosition(), type.getDefaultState(), flag);
    }

    /**
     * Replace the block at this position with a copy of the given snapshot.
     *
     * <p>Changing the snapshot afterwards will not affect the block that has
     * been placed at this location.</p>
     *  @param snapshot The snapshot
     * @param force If true, forces block state to be set even if the
     * {@link BlockType} does not match the snapshot one.
     * @param flag The various change flags controlling some interactions
     * @return True if the snapshot restore was successful
     */
    default boolean restoreSnapshot(BlockSnapshot snapshot, boolean force, BlockChangeFlag flag) {
        return this.getWorld().restoreSnapshot(this.getBlockPosition(), snapshot, force, flag);
    }

    /**
     * Remove the block at this position by replacing it with
     * {@link BlockTypes#AIR}.
     *
     * <p>This will remove any extended block data at the given position.</p>
     * @return True if the block change was successful
     */
    default boolean removeBlock() {
        return this.getWorld().removeBlock(this.getBlockPosition());
    }

    /**
     * Create an entity instance at the given position.
     *
     * <p>Creating an entity does not spawn the entity into the world. An entity
     * created means the entity can be spawned at the given location. If
     * {@link Optional#empty()} was returned, the entity is not able to spawn at
     * the given location. Furthermore, this allows for the {@link Entity} to be
     * customized further prior to traditional "ticking" and processing by core
     * systems.</p>
     *
     * @param type The type
     * @return An entity, if one was created
     * @throws IllegalArgumentException If the position or entity type is not
     *     valid to create
     * @throws IllegalStateException If a constructor cannot be found
     * @see MutableEntityVolume#createEntity(EntityType, Vector3d)
     */
    default Entity createEntity(EntityType type) {
        return this.getWorld().createEntity(type, this.getPosition());
    }

    /**
     * Spawns an {@link Entity} using the already set properties (world,
     * position, rotation) and applicable {@link DataManipulator}s with the
     * specified {@link Cause} for spawning the entity.
     *
     * <p>Note that for the {@link Cause} to be useful in the expected
     * {@link SpawnEntityEvent}, a {@link SpawnType} should be provided in the
     * {@link EventContext} for other plugins to understand and have finer
     * control over the event.</p>
     *
     * <p>The requirements involve that all necessary setup of states and data
     * is already preformed on the entity retrieved from the various
     * {@link MutableEntityVolume#createEntity(EntityType,Vector3d)} methods.
     * Calling this will make the now-spawned entity able to be processed by
     * various systems.</p>
     *
     * <p>If the entity was unable to spawn, the entity is not removed, but it
     * should be taken note that there can be many reasons for a failure.</p>
     *
     * @param entity The entity to spawn
     * @return True if successful, false if not
     * @see MutableEntityVolume#spawnEntity(Entity)
     */
    default boolean spawnEntity(Entity entity) {
        return this.getWorld().spawnEntity(entity);
    }

    /**
     * Similar to {@link #spawnEntity(Entity)} except where multiple
     * entities can be attempted to be spawned with a customary {@link Cause}.
     * The recommended use is to easily process the entity spawns without
     * interference with the cause tracking system.
     *
     * @param entities The entities which spawned correctly, or empty if none
     * @return True if any of the entities were successfully spawned
     * @see MutableEntityVolume#spawnEntities(Iterable)
     */
    default Collection<Entity> spawnEntities(Iterable<? extends Entity> entities) {
        return this.getWorld().spawnEntities(entities);
    }

    /**
     * Gets the highest {@link Location} at this location.
     *
     * @return The highest location at this location
     * @see World#getHighestPositionAt(Vector3i)
     */
    default Location asHighestLocation() {
        return this.withBlockPosition(this.getWorld().getHighestPositionAt(this.getBlockPosition()));
    }

    @Override
    default DataTransactionResult remove(Class<? extends DataManipulator<?, ?>> containerClass) {
        return this.getWorld().remove(this.getBlockPosition(), containerClass);
    }

    @Override
    default DataTransactionResult remove(BaseValue<?> value) {
        return this.getWorld().remove(this.getBlockPosition(), value.getKey());
    }

    @Override
    default DataTransactionResult remove(Key<?> key) {
        return this.getWorld().remove(this.getBlockPosition(), key);
    }

    /**
     * Gets a snapshot of this block at the current point in time.
     *
     * <p>A snapshot is disconnected from the {@link World} that it was taken
     * from so changes to the original block do not affect the snapshot.</p>
     *
     * @return A snapshot
     */
    default BlockSnapshot createSnapshot() {
        return this.getWorld().createSnapshot(this.getBlockPosition());
    }

    /**
     * Gets a list of {@link ScheduledUpdate}s for the block at this location.
     *
     * @return A list of scheduled block updates on this location
     */
    default Collection<ScheduledUpdate<BlockType>> getScheduledBlockUpdates() {
        return this.getWorld().getScheduledBlockUpdates().getScheduledAt(this.getBlockPosition());
    }

    /**
     * Adds a new {@link ScheduledUpdate} for the block at this location.
     *
     * @param delay The delay before the scheduled update should be processed
     * @param temporalUnit The temporal unit of the delay
     * @return The newly created scheduled update
     */
    default ScheduledUpdate<BlockType> scheduleBlockUpdate(int delay, TemporalUnit temporalUnit) {
        return this.getWorld().getScheduledBlockUpdates().schedule(this.getBlockPosition(), getBlock().getType(), delay, temporalUnit);
    }

    /**
     * Adds a new {@link ScheduledUpdate} for the block at this location.
     *
     * @param delay The delay before the scheduled update should be processed
     * @param temporalUnit The temporal unit of the delay
     * @param priority The priority of the scheduled update
     * @return The newly created scheduled update
     */
    default ScheduledUpdate<BlockType> scheduleBlockUpdate(int delay, TemporalUnit temporalUnit, TaskPriority priority) {
        return this.getWorld().getScheduledBlockUpdates().schedule(this.getBlockPosition(), getBlock().getType(), delay, temporalUnit, priority);
    }

    /**
     * Adds a new {@link ScheduledUpdate} for the block at this location.
     *
     * @param delay The delay before the scheduled update should be processed
     * @return The newly created scheduled update
     */
    default ScheduledUpdate<BlockType> scheduleBlockUpdate(Duration delay) {
        return this.getWorld().getScheduledBlockUpdates().schedule(this.getBlockPosition(), getBlock().getType(), delay);
    }

    /**
     * Adds a new {@link ScheduledUpdate} for the block at this location.
     *
     * @param delay The delay before the scheduled update should be processed
     * @param priority The priority of the scheduled update
     * @return The newly created scheduled update
     */
    default ScheduledUpdate<BlockType> scheduleBlockUpdate(Duration delay, TaskPriority priority) {
        return this.getWorld().getScheduledBlockUpdates().schedule(this.getBlockPosition(), getBlock().getType(), delay, priority);
    }

    /**
     * Gets a list of {@link ScheduledUpdate}s for the fluid at this location.
     *
     * @return A list of scheduled fluid updates on this location
     */
    default Collection<ScheduledUpdate<FluidType>> getScheduledFluidUpdates() {
        return this.getWorld().getScheduledFluidUpdates().getScheduledAt(this.getBlockPosition());
    }

    /**
     * Adds a new {@link ScheduledUpdate} for the fluid at this location.
     *
     * @param delay The delay before the scheduled update should be processed
     * @param temporalUnit The temporal unit of the delay
     * @return The newly created scheduled update
     */
    default ScheduledUpdate<FluidType> scheduleFluidUpdate(int delay, TemporalUnit temporalUnit) {
        return this.getWorld().getScheduledFluidUpdates().schedule(this.getBlockPosition(), getFluid().getType(), delay, temporalUnit);
    }

    /**
     * Adds a new {@link ScheduledUpdate} for the fluid at this location.
     *
     * @param delay The delay before the scheduled update should be processed
     * @param temporalUnit The temporal unit of the delay
     * @param priority The priority of the scheduled update
     * @return The newly created scheduled update
     */
    default ScheduledUpdate<FluidType> scheduleFluidUpdate(int delay, TemporalUnit temporalUnit, TaskPriority priority) {
        return this.getWorld().getScheduledFluidUpdates().schedule(this.getBlockPosition(), this.getFluid().getType(), delay, temporalUnit, priority);
    }

    /**
     * Adds a new {@link ScheduledUpdate} for the fluid at this location.
     *
     * @param delay The delay before the scheduled update should be processed
     * @return The newly created scheduled update
     */
    default ScheduledUpdate<FluidType> scheduleFluidUpdate(Duration delay) {
        return this.getWorld().getScheduledFluidUpdates().schedule(this.getBlockPosition(), getFluid().getType(), delay);
    }

    /**
     * Adds a new {@link ScheduledUpdate} for the fluid at this location.
     *
     * @param delay The delay before the scheduled update should be processed
     * @param priority The priority of the scheduled update
     * @return The newly created scheduled update
     */
    default ScheduledUpdate<FluidType> scheduleFluidUpdate(Duration delay, TaskPriority priority) {
        return this.getWorld().getScheduledFluidUpdates().schedule(this.getBlockPosition(), this.getFluid().getType(), delay, priority);
    }

    @Override
    default <V> Optional<V> getProperty(Property<V> property) {
        return this.getWorld().getProperty(this.getBlockPosition(), property);
    }

    @Override
    default OptionalInt getIntProperty(Property<Integer> property) {
        return this.getWorld().getIntProperty(this.getBlockPosition(), property);
    }

    @Override
    default OptionalDouble getDoubleProperty(Property<Double> property) {
        return this.getWorld().getDoubleProperty(this.getBlockPosition(), property);
    }

    @Override
    default Map<Property<?>, ?> getProperties() {
        return this.getWorld().getProperties(this.getBlockPosition());
    }

    @Override
    default <V> Optional<V> getProperty(Direction direction, Property<V> property) {
        return this.getWorld().getProperty(this.getBlockPosition(), direction, property);
    }

    @Override
    default OptionalInt getIntProperty(Direction direction, Property<Integer> property) {
        return this.getWorld().getIntProperty(this.getBlockPosition(), direction, property);
    }

    @Override
    default OptionalDouble getDoubleProperty(Direction direction, Property<Double> property) {
        return this.getWorld().getDoubleProperty(this.getBlockPosition(), direction, property);
    }

    @Override
    default boolean validateRawData(DataView container) {
        return this.getWorld().validateRawData(this.getBlockPosition(), container);
    }

    @Override
    default void setRawData(DataView container) throws InvalidDataException {
        this.getWorld().setRawData(this.getBlockPosition(), container);
    }

    @Override
    default <T extends DataManipulator<?, ?>> Optional<T> get(Class<T> containerClass) {
        return this.getWorld().get(this.getBlockPosition(), containerClass);
    }

    @Override
    default <T> Optional<T> get(Key<? extends BaseValue<T>> key) {
        return this.getWorld().get(this.getBlockPosition(), key);
    }

    @Override
    default <T extends DataManipulator<?, ?>> Optional<T> getOrCreate(Class<T> containerClass) {
        return this.getWorld().getOrCreate(this.getBlockPosition(), containerClass);
    }

    @Override
    default <T> DataTransactionResult offer(Key<? extends BaseValue<T>> key, T value) {
        return this.getWorld().offer(this.getBlockPosition(), key, value);
    }

    @Override
    default DataTransactionResult offer(DataManipulator<?, ?> valueContainer, MergeFunction function) {
        return this.getWorld().offer(this.getBlockPosition(), valueContainer, function);
    }

    @Override
    default DataTransactionResult undo(DataTransactionResult result) {
        return this.getWorld().undo(this.getBlockPosition(), result);
    }

    @Override
    default boolean supports(Class<? extends DataManipulator<?, ?>> holderClass) {
        return this.getWorld().supports(this.getBlockPosition(), holderClass);
    }

    @Override
    default boolean supports(Key<?> key) {
        return this.getWorld().supports(this.getBlockPosition(), key);
    }

    @Override
    default DataTransactionResult copyFrom(DataHolder that, MergeFunction strategy) {
        return this.getWorld().copyFrom(this.getBlockPosition(), that, strategy);
    }

    @Override
    default Collection<DataManipulator<?, ?>> getContainers() {
        return this.getWorld().getManipulators(this.getBlockPosition());
    }

    @Override
    default <T, V extends BaseValue<T>> Optional<V> getValue(Key<V> key) {
        return this.getWorld().getValue(this.getBlockPosition(), key);
    }

    @Override
    default Set<Key<?>> getKeys() {
        return this.getWorld().getKeys(this.getBlockPosition());
    }

    @Override
    default Set<ImmutableValue<?>> getValues() {
        return this.getWorld().getValues(this.getBlockPosition());
    }

    interface Factory {
        Location create(World world, double x, double y, double z);

        Location create(World world, int x, int y, int z);
    }
}
