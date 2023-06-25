package dev.rollczi.litekarma.karma.kill

import dev.rollczi.litekarma.karma.KarmaService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

internal class KarmaKillController(private val karmaService: KarmaService) : Listener {

    @EventHandler
    fun onKill(event: PlayerDeathEvent) {
        val player = event.entity
        val killer = player.killer ?: return

        karmaService.incrementKarma(killer.uniqueId)
    }

}