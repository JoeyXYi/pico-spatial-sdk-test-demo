package com.example.spatialsdktest.content

import com.example.spatialsdktest.domain.DemoState
import com.pico.spatial.core.ecs.Entity
import com.pico.spatial.core.ecs.ModelEntity
import com.pico.spatial.core.ecs.TransformComponent
import com.pico.spatial.core.ecs.resource.MeshResource
import com.pico.spatial.core.ecs.resource.UnlitMaterial
import com.pico.spatial.core.math.Color4
import com.pico.spatial.core.math.EulerAngles
import com.pico.spatial.core.math.Vector3

/** Owns the procedural model and its resources; AttachmentPanel owns its own entity. */
class DemoScene : AutoCloseable {
    private var cube: ModelEntity? = null
    private var mesh: MeshResource? = null
    private var material: UnlitMaterial? = null

    fun create(): Entity {
        check(cube == null) { "Scene already created" }
        val geometry = MeshResource.createBox(Vector3(.22f)).also { mesh = it }
        val surface = UnlitMaterial.create().also {
            material = it
            it.setBaseColor(Color4(.15f, .65f, 1f, 1f))
        }
        return ModelEntity(geometry, surface).also { entity ->
            cube = entity
            entity.components[TransformComponent::class.java]?.setPosition(
                Vector3(0f, 1.85f, -1.5f),
            )
        }
    }

    fun render(state: DemoState) {
        cube?.apply {
            enabled = state.visible
            components[TransformComponent::class.java]?.apply {
                setEulerAngles(EulerAngles(20f, state.yaw.toFloat(), 0f))
                setScaleVector(Vector3(state.sizePercent / 100f))
            }
        }
    }

    override fun close() {
        cube?.destroy()
        cube = null
        mesh?.close()
        mesh = null
        material?.close()
        material = null
    }
}
