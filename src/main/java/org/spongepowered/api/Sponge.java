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
package org.spongepowered.api;

import static com.google.common.base.Preconditions.checkState;

import com.google.inject.Inject;
import org.spongepowered.api.asset.AssetManager;
import org.spongepowered.api.command.manager.CommandManager;
import org.spongepowered.api.config.ConfigManager;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;
import org.spongepowered.api.network.ChannelRegistrar;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.registry.GameRegistry;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.service.ServiceProvider;
import org.spongepowered.api.sql.SqlManager;
import org.spongepowered.api.util.metric.MetricsConfigManager;
import org.spongepowered.api.world.ServerLocation;
import org.spongepowered.api.world.TeleportHelper;

/**
 * A static all access class granting static access to various systems
 * for the API.
 */
public final class Sponge {

    @Inject private static Game game;

    /**
     * Gets the {@link Game} instance. There is ever only going
     * to be a single game instance at any given time.
     *
     * @return The game instance
     */
    public static Game getGame() {
        checkState(game != null, "Sponge has not been initialized!");
        return Sponge.game;
    }

    /**
     * Returns the current platform, or implementation, this {@link Game}
     * is running on.
     *
     * @return The current implementation
     */
    public static Platform getPlatform() {
        return getGame().getPlatform();
    }

    /**
     * Gets the {@link GameRegistry} instance.
     *
     * @return The game registry instance
     */
    public static GameRegistry getRegistry() {
        return getGame().getRegistry();
    }

    /**
     * Gets the {@link DataManager} instance.
     *
     * @return The data manager instance
     */
    public static DataManager getDataManager() {
        return getGame().getDataManager();
    }

    /**
     * Gets the {@link PluginManager} instance.
     *
     * @return The plugin manager instance
     */
    public static PluginManager getPluginManager() {
        return getGame().getPluginManager();
    }

    /**
     * Gets the {@link EventManager} instance.
     *
     * @return The event manager instance
     */
    public static EventManager getEventManager() {
        return getGame().getEventManager();
    }

    /**
     * Gets the {@link AssetManager} instance.
     *
     * @return The asset manager instance
     */
    public static AssetManager getAssetManager() {
        return getGame().getAssetManager();
    }

    /**
     * Gets the {@link ConfigManager} used to load and manage configuration files
     * for plugins.
     *
     * @return The configuration manager
     */
    public static ConfigManager getConfigManager() {
        return getGame().getConfigManager();
    }

    /**
     * Gets the {@link ChannelRegistrar} for creating network channels.
     *
     * @return The channel registrar
     */
    public static ChannelRegistrar getChannelRegistrar() {
        return getGame().getChannelRegistrar();
    }

    /**
     * Gets the {@link TeleportHelper}, used to find safe {@link ServerLocation}s.
     *
     * @return The teleport helper
     */
    public static TeleportHelper getTeleportHelper() {
        return getGame().getTeleportHelper();
    }

    /**
     * Gets whether a {@link Server} instance is available without throwing an
     * exception from calling {@link #getServer()}.
     *
     * @see Game#isServerAvailable()
     * @return True if the server instance is available
     */
    public static boolean isServerAvailable() {
        return getGame().isServerAvailable();
    }

    /**
     * Gets the {@link Server} instance from the {@link Game} instance.
     *
     * <p>Note: During various {@link LifecycleEvent events}, a {@link Server} instance
     * may <strong>NOT</strong> be available. Calling {@link Game#getServer()} during one
     * will throw an exception. To double check, call {@link #isServerAvailable()}</p>
     *
     * @see Game#getServer()
     * @see Game#isServerAvailable()
     * @return The server instance
     */
    public static Server getServer() {
        return getGame().getServer();
    }

    /**
     * Gets whether a {@link Client} instance is available without throwing an
     * exception from calling {@link #getClient()}.
     *
     * @see Game#isClientAvailable()
     * @return True if the client instance is available
     */
    public static boolean isClientAvailable() {
        return getGame().isClientAvailable();
    }

    /**
     * Gets the {@link Client} instance from the {@link Game} instance.
     *
     * <p>Note: Not all implementations support a client, consult your
     * vendor for further information.</p>
     *
     * @see Game#getClient()
     * @see Game#isClientAvailable()
     * @return The client instance
     */
    public static Client getClient() {
        return getGame().getClient();
    }

    /**
     * Gets the {@link SystemSubject} instance from the {@link Game} instance.
     *
     * @see Game#getSystemSubject() ()
     * @return The system subject
     */
    public static SystemSubject getSystemSubject() {
        return getGame().getSystemSubject();
    }

    /**
     * Gets the {@link MetricsConfigManager} instance, allowing data/metric gathering
     * systems to determine whether they have permission to gather server
     * metrics.
     *
     * @return The {@link MetricsConfigManager} instance
     */
    public static MetricsConfigManager getMetricsConfigManager() {
        return getGame().getMetricsConfigManager();
    }

    /**
     * Gets the {@link Scheduler} used to schedule async tasks.
     *
     * @return The async scheduler
     */
    public static Scheduler getAsyncScheduler() {
        return getGame().getAsyncScheduler();
    }

    /**
     * Gets the {@link CommandManager} for registering and executing commands.
     *
     * @return The {@link CommandManager} instance.
     */
    public static CommandManager getCommandManager() {
        return getGame().getCommandManager();
    }

    /**
     * Gets the {@link SqlManager} for grabbing sql data sources.
     *
     * @return The {@link SqlManager} instance.
     */
    public static SqlManager getSqlManager() {
        return getGame().getSqlManager();
    }

    /**
     * Gets the {@link ServiceProvider} for providing services.
     *
     * @return The service provider.
     */
    public static ServiceProvider getServiceProvider() {
        return getGame().getServiceProvider();
    }
}
