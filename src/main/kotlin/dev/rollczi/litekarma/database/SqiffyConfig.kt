package dev.rollczi.litekarma.database

import dev.rollczi.litekarma.config.ConfigFile

@ConfigFile("database.yml")
internal class SqiffyConfig {

    @JvmField var host: String = "localhost"
    @JvmField var port: Int = 3306
    @JvmField var database: String = "litekarma"
    @JvmField var user: String = "root"
    @JvmField var password: String = ""
    @JvmField var otherParams: String = "?useSSL=false"
    @JvmField var driver: String = "com.mysql.cj.jdbc.Driver"
    @JvmField var driverUrl: String = "jdbc:mysql:"

    internal fun toUrl(): String {
        return "$driverUrl//$host:$port/$database$otherParams"
    }

}