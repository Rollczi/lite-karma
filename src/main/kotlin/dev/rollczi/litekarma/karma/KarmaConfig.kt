package dev.rollczi.litekarma.karma

import dev.rollczi.litekarma.config.ConfigFile

@ConfigFile("config.yml")
internal class KarmaConfig {

    @JvmField var karma: String = "<#5f4bcd>Dodano <#9f4bcd>+1</#9f4bcd> karmy ({karma})!"

    fun karmaMessage(karmaCount: Int): String {
        return karma.replace("{karma}", karmaCount.toString())
    }

}