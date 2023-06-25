package dev.rollczi.litekarma.scheduler

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler

internal class SchedulerBukkitImpl(private val plugin: Plugin) : Scheduler {

    private val root: BukkitScheduler = plugin.server.scheduler

    override fun async(runnable: Runnable) {
        root.runTaskAsynchronously(plugin, runnable)
    }

    override fun sync(runnable: Runnable) {
        root.runTask(plugin, runnable)
    }
}
