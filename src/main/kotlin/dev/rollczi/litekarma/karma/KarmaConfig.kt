package dev.rollczi.litekarma.karma

import dev.rollczi.litekarma.config.ConfigFile
import dev.rollczi.litekarma.legacy.LegacyColorProcessor
import net.dzikoysk.cdn.entity.Exclude
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

@ConfigFile("config.yml")
internal class KarmaConfig {

    @Exclude
    private val miniMessage: MiniMessage = MiniMessage.builder()
        .postProcessor(LegacyColorProcessor())
        .build()

    @JvmField var karma: String = "<#5f4bcd>Dodano <#9f4bcd>+1</#9f4bcd> karmy ({karma})!"

    fun karmaMessage(karmaCount: Int): Component {
        return miniMessage.deserialize(karma.replace("{karma}", karmaCount.toString()))
    }

}