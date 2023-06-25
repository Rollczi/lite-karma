package dev.rollczi.litekarma.karma

import com.dzikoysk.sqiffy.SqiffyDatabase
import com.dzikoysk.sqiffy.dsl.eq
import java.util.*

internal class KarmaRepositorySqiffyImpl(private val sqiffyDatabase: SqiffyDatabase) : KarmaRepository{

    init {
        val changeLog = sqiffyDatabase.generateChangeLog(
            KarmaDefinition::class,
        )

        sqiffyDatabase.runMigrations(changeLog)
    }

    override fun incrementKarma(uuid: UUID, count: Int): Karma {
        val kramaCount = sqiffyDatabase.select(KarmaTable)
            .slice(KarmaTable.karma)
            .where { KarmaTable.uuid eq uuid }
            .map { it[KarmaTable.karma] }
            .firstOrNull()

        if (kramaCount != null) {
            sqiffyDatabase
                .update(KarmaTable) { it[KarmaTable.karma] = kramaCount + count }
                .where { KarmaTable.uuid eq uuid }
                .execute()

            return Karma(uuid, kramaCount + count)
        }

        sqiffyDatabase
            .insert(KarmaTable) {
                it[KarmaTable.uuid] = uuid
                it[KarmaTable.karma] = 1
            }.execute()

        return Karma(uuid, 1)
    }

    override fun getKarma(uuid: UUID): Karma {
        val kramaCount = sqiffyDatabase.select(KarmaTable)
            .slice(KarmaTable.karma)
            .where { KarmaTable.uuid eq uuid }
            .map { it[KarmaTable.karma] }
            .firstOrNull() ?: 0

        return Karma(uuid, kramaCount)
    }

}