package dev.rollczi.litekarma

import com.dzikoysk.sqiffy.SqiffyDatabase
import dev.rollczi.litekarma.karma.KarmaRepositorySqiffyImpl
import dev.rollczi.litekarma.karma.KarmaService
import dev.rollczi.litekarma.karma.kill.KarmaKillController
import dev.rollczi.litekarma.placeholderapi.PlaceholderApiExpansion
import dev.rollczi.litekarma.scheduler.SchedulerBukkitImpl
import dev.rollczi.litekarma.config.ConfigService
import dev.rollczi.litekarma.database.SqiffyConfig
import dev.rollczi.litekarma.database.SqiffyFactory
import dev.rollczi.litekarma.karma.KarmaConfig
import dev.rollczi.litekarma.karma.KarmaController
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class LiteKarma(private val plugin: Plugin) {

    private lateinit var sqiffyDatabase: SqiffyDatabase
    private lateinit var audienceProvider: BukkitAudiences

    fun onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            plugin.logger.warning("PlaceholderAPI not found! Disabling plugin...")
            Bukkit.getPluginManager().disablePlugin(plugin)
            return
        }

        // basic setup
        val server = plugin.server
        val scheduler = SchedulerBukkitImpl(plugin)
        val configService = ConfigService(plugin.dataFolder)
        audienceProvider = BukkitAudiences.create(plugin)

        // database setup
        val sqiffyConfig = configService.load(SqiffyConfig::class.java)
        sqiffyDatabase = SqiffyFactory.createSqiffy(sqiffyConfig)

        // karma setup
        val karmaConfig = configService.load(KarmaConfig::class.java)
        val karmaService = KarmaService(KarmaRepositorySqiffyImpl(sqiffyDatabase), scheduler, karmaConfig, server, audienceProvider)

        listOf(KarmaController(karmaService), KarmaKillController(karmaService)).forEach {
            server.pluginManager.registerEvents(it, plugin)
        }

        val expansion = PlaceholderApiExpansion(karmaService)
        expansion.register()
    }

    fun onDisable() {
        sqiffyDatabase.close()
        audienceProvider.close()
    }

}