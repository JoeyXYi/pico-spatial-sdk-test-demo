package com.example.spatialsdktest.domain

/** UI-independent controls. Positions are Stage metres; rotation is in degrees. */
data class DemoState(
    val yaw: Int = 30,
    val sizePercent: Int = 100,
    val visible: Boolean = true,
    val interactionCount: Int = 0,
) {
    fun rotate(degrees: Int) = copy(
        yaw = Math.floorMod(yaw + degrees, 360),
        interactionCount = interactionCount + 1,
    )

    fun resize(delta: Int) = copy(
        sizePercent = (sizePercent + delta).coerceIn(50, 150),
        interactionCount = interactionCount + 1,
    )

    fun toggleVisibility() = copy(
        visible = !visible,
        interactionCount = interactionCount + 1,
    )

    fun reset() = DemoState()
}
