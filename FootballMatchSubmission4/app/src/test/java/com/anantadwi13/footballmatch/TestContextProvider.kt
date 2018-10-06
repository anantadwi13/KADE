package com.anantadwi13.footballmatch

import com.anantadwi13.footballmatch.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider :CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}