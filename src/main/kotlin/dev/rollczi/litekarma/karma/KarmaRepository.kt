package dev.rollczi.litekarma.karma

import java.util.UUID

internal interface KarmaRepository {

    fun incrementKarma(uuid: UUID, count: Int): Karma

    fun getKarma(uuid: UUID): Karma

}
