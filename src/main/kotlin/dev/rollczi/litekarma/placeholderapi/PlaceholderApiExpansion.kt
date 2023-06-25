package dev.rollczi.litekarma.placeholderapi

import dev.rollczi.litekarma.LiteKarmaPluginVersion
import dev.rollczi.litekarma.karma.KarmaService
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer

internal class PlaceholderApiExpansion(private val karmaService: KarmaService) : PlaceholderExpansion() {

    override fun getIdentifier(): String {
        return "lite_karma"
    }

    override fun getAuthor(): String {
        return "Rollczi"
    }

    override fun getVersion(): String {
        return LiteKarmaPluginVersion.V_0_1_0
    }

    override fun onRequest(player: OfflinePlayer, params: String): String? {
        if (params.equals("count", ignoreCase = true)) {
            val (_, karma1) = karmaService.getCachedKarma(player.uniqueId) ?: return "0"
            return karma1.toString()
        }

        return null // Placeholder is unknown by the Expansion
    }

}
