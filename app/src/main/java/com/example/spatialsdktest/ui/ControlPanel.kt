package com.example.spatialsdktest.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.spatialsdktest.domain.DemoState
import com.pico.spatial.ui.design.Button
import com.pico.spatial.ui.design.PicoTheme
import com.pico.spatial.ui.design.Text
import com.pico.spatial.ui.foundation.material.backgroundMaterial
import com.pico.spatial.ui.platform.Material

@Composable
fun ControlPanel(state: DemoState, status: String, onChange: (DemoState) -> Unit) {
    Column(
        modifier = Modifier.width(640.dp)
            .clip(RoundedCornerShape(28.dp))
            .backgroundMaterial(true, Material.Regular)
            .padding(28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Spatial SDK Test Demo", style = PicoTheme.typography.titleLarge)
        Text(status, color = PicoTheme.colorScheme.labelSecondary)
        Text("Rotation ${state.yaw}°   ·   Size ${state.sizePercent}%   ·   Actions ${state.interactionCount}")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { onChange(state.rotate(-30)) }) { Text("Rotate left") }
            Button(onClick = { onChange(state.rotate(30)) }) { Text("Rotate right") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { onChange(state.resize(-10)) }, enabled = state.sizePercent > 50) {
                Text("Smaller")
            }
            Button(onClick = { onChange(state.resize(10)) }, enabled = state.sizePercent < 150) {
                Text("Larger")
            }
            Button(onClick = { onChange(state.toggleVisibility()) }) {
                Text(if (state.visible) "Hide cube" else "Show cube")
            }
        }
        Button(onClick = { onChange(state.reset()) }) { Text("Reset demo") }
        Text(
            "Use the controller ray or system hand pointer to select a button. " +
                "Cube position: (0, 1.85, −1.5) m in Stage coordinates.",
            color = PicoTheme.colorScheme.labelSecondary,
        )
    }
}
