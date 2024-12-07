package com.example.halantwittercounter.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T: Any> Fragment.collect(flow: StateFlow<T>, body: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.collect { value ->
            body(value)
        }
    }
}