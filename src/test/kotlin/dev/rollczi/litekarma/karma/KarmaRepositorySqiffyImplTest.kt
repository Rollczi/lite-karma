package dev.rollczi.litekarma.karma

import dev.rollczi.litekarma.support.SqiffyContainer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.UUID

@Testcontainers
class KarmaRepositorySqiffyImplTest {

    @Container
    private val sqiffyContainer = SqiffyContainer()

    @Test
    fun `should increment karma`() {
        val repository = KarmaRepositorySqiffyImpl(sqiffyContainer.sqiffyDatabase)
        val uuid = UUID.randomUUID()
        val karma = repository.incrementKarma(uuid, 1)

        assertEquals(1, karma.karma)
        assertEquals(uuid, karma.uuid)

        repository.incrementKarma(uuid, 2)

        val karma2 = repository.getKarma(uuid)
        assertEquals(3, karma2.karma)
        assertEquals(uuid, karma2.uuid)
    }


}