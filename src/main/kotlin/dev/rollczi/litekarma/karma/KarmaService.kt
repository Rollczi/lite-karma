package dev.rollczi.litekarma.karma

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import dev.rollczi.litekarma.scheduler.Scheduler
import org.bukkit.Server
import java.util.UUID
import java.util.concurrent.TimeUnit

internal class KarmaService(
    private val karmaRepository: KarmaRepository,
    private val scheduler: Scheduler,
    private val karmaConfig: KarmaConfig,
    private val server: Server
) {

    private val cache: Cache<UUID, Karma>

    init {
        cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .expireAfterAccess(3, TimeUnit.MINUTES)
            .refreshAfterWrite(3, TimeUnit.SECONDS)
            .build(CacheLoader.asyncReloading(CacheLoader.from { uuid -> karmaRepository.getKarma(uuid) }, scheduler))
    }

    fun incrementKarma(uuid: UUID) {
        val player = server.getPlayer(uuid)

        scheduler.async {
            val karma = karmaRepository.incrementKarma(uuid, 1)

            cache.put(uuid, karma)
            player?.sendMessage(karmaConfig.karmaMessage(karma.karma))
        }
    }

    fun getCachedKarma(uuid: UUID): Karma? {
        return cache.getIfPresent(uuid)
    }

}