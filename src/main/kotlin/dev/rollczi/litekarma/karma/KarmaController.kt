package dev.rollczi.litekarma.karma

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

internal class KarmaController(private val karmaService: KarmaService) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        karmaService.refreshCache(event.player.uniqueId)
    }

}
