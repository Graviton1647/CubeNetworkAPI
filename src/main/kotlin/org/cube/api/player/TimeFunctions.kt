package org.cube.api.player

import java.util.concurrent.TimeUnit

fun toSeconds(mins : Int) : Long = TimeUnit.MINUTES.toSeconds(mins.toLong())

