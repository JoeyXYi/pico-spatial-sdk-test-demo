package com.example.spatialsdktest.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class DemoStateTest {
    @Test fun rotationWrapsInBothDirections() {
        assertEquals(330, DemoState(yaw = 0).rotate(-30).yaw)
        assertEquals(0, DemoState(yaw = 330).rotate(30).yaw)
    }

    @Test fun repeatedResizeStaysWithinVisibleBounds() {
        var large = DemoState()
        var small = DemoState()
        repeat(50) {
            large = large.resize(10)
            small = small.resize(-10)
        }
        assertEquals(150, large.sizePercent)
        assertEquals(50, small.sizePercent)
    }

    @Test fun actionsAreCountedAndVisibilityCanBeRestored() {
        val hidden = DemoState().rotate(30).resize(10).toggleVisibility()
        assertFalse(hidden.visible)
        assertEquals(3, hidden.interactionCount)
        assertEquals(true, hidden.toggleVisibility().visible)
    }

    @Test fun resetRestoresTheEntireInitialState() {
        val changed = DemoState().rotate(-60).resize(40).toggleVisibility()
        assertEquals(DemoState(), changed.reset())
    }
}
