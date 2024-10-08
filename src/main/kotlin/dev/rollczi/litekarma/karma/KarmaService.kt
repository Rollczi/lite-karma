package dev.rollczi.litekarma.karma

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import dev.rollczi.litekarma.scheduler.Scheduler
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Server
import java.util.UUID
import java.util.concurrent.TimeUnit

internal class KarmaService(
    private val karmaRepository: KarmaRepository,
    private val scheduler: Scheduler,
    private val karmaConfig: KarmaConfig,
    private val server: Server,
    private val audienceProvider: BukkitAudiences
) {

    private val cache: LoadingCache<UUID, Karma> = CacheBuilder.newBuilder()
        .expireAfterWrite(3, TimeUnit.MINUTES)
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .refreshAfterWrite(3, TimeUnit.SECONDS)
        .build(CacheLoader.asyncReloading(CacheLoader.from { uuid -> karmaRepository.getKarma(uuid) }, scheduler))

    fun incrementKarma(uuid: UUID) {
        val player = server.getPlayer(uuid)

        scheduler.async {
            val karma = karmaRepository.incrementKarma(uuid, 1)

            cache.put(uuid, karma)

            if (player != null) {
                audienceProvider.player(player).sendMessage(karmaConfig.karmaMessage(karma.karma))
            }
        }
    }

    fun getCachedKarma(uuid: UUID): Karma? {
        val karma = cache.getIfPresent(uuid)

        if (karma == null) {
            refreshCache(uuid)
        }

        return karma
    }

    fun refreshCache(uuid: UUID) {
        scheduler.async {
            cache.refresh(uuid)
        }
    }

}