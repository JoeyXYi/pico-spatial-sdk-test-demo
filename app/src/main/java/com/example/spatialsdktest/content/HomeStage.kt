package com.example.spatialsdktest.content

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.spatialsdktest.domain.DemoState
import com.example.spatialsdktest.ui.ControlPanel
import com.pico.spatial.core.ecs.TransformComponent
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.ui.foundation.content.SpatialView

@Composable
fun HomeStage() {
    var state by remember { mutableStateOf(DemoState()) }
    var status by remember { mutableStateOf("Starting Spatial ECS…") }
    val scene = remember { DemoScene() }
    DisposableEffect(scene) {
        onDispose { scene.close() }
    }
    SpatialView(
        initial = { content, attachments ->
            // Mount controls first, so renderer errors can still be shown in the headset.
            attachments.entity("controls")?.let { panel ->
                panel.components[TransformComponent::class.java]?.setPosition(
                    Vector3(0f, 1.15f, -1.5f),
                )
                content.addEntity(panel)
            }
            try {
                content.addEntity(scene.create())
                scene.render(state)
                status = "Spatial ECS ready · SDK 6.0.0"
                Log.i("SpatialSdkDemo", "Scene initialized")
            } catch (error: Exception) {
                status = "Scene failed: ${error.javaClass.simpleName} — check logcat"
                Log.e("SpatialSdkDemo", "Scene initialization failed", error)
            }
        },
        update = { _, _ -> scene.render(state) },
        attachments = {
            AttachmentPanel(id = "controls") {
                ControlPanel(state, status) { state = it }
            }
        },
    )
}
