package dev.rollczi.litekarma.scheduler

import java.util.concurrent.Executor

internal interface Scheduler : Executor {

    fun async(runnable: Runnable)

    fun sync(runnable: Runnable)

    override fun execute(command: Runnable) {
        async(command)
    }
}
