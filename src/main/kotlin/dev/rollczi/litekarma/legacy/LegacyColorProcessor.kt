package dev.rollczi.litekarma.legacy

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import java.util.function.BiFunction
import java.util.function.UnaryOperator
import java.util.regex.MatchResult
import java.util.regex.Pattern

class LegacyColorProcessor : UnaryOperator<Component> {

    override fun apply(component: Component): Component {
        return component.replaceText(REPLACEMENT_CONFIG)
    }

    private class LegacyReplace : BiFunction<MatchResult, TextComponent.Builder, ComponentLike?> {
        override fun apply(match: MatchResult, builder: TextComponent.Builder): ComponentLike {
            return RESET_ITALIC.append(LEGACY_SERIALIZER.deserialize(match.group()))
        }
    }

    companion object {
        private val LEGACY_PATTERN = Pattern.compile(".*")
        private val LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build()

        private val REPLACEMENT_CONFIG: TextReplacementConfig = TextReplacementConfig.builder()
            .match(LEGACY_PATTERN)
            .replacement(LegacyReplace())
            .build()

        private val RESET_ITALIC: Component = Component.empty()
            .decoration(TextDecoration.ITALIC, false)
    }
}
