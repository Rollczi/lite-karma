package dev.rollczi.litekarma.config

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ConfigFile(val fileName: String)
