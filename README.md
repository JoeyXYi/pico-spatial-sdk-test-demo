# PICO Spatial SDK Android Test Demo

用于验证 **PICO Spatial SDK** 的原生 Android/Kotlin 测试项目。使用 SDK **6.0.0**、Android SDK 35、Kotlin 2.1.20 和 Gradle 8.13。目标运行环境为支持 Spatial Runtime 的 **PICO OS 6 设备或 PICO Emulator**。

## 功能

- `DefaultStage` 创建透视背景的 Mixed Stage。
- `SpatialView` + ECS `ModelEntity` 渲染边长 22 cm 的程序化 3D 方块，无外部模型下载。
- `AttachmentPanel` + `PicoTheme` + SpatialUI `Button` 构建空间控制面板。
- 左右旋转（每次 30°）、缩放（50–150%）、显示/隐藏、重置及交互计数。
- 场景创建失败时在面板显示错误，并写入 `SpatialSdkDemo` logcat 标签。
- 退出时销毁方块并释放 mesh/material；UI 附件由 SDK 管理。

## 构建

准备 JDK 21、Android SDK Platform 35、Build Tools 35.0.0；首次构建需要访问 Google Maven、Maven Central 和 PICO 官方模板指定的 Maven 仓库。无需复制 SDK AAR 到仓库。

在 Android Studio 打开仓库，配置 Android SDK；或设置 `ANDROID_HOME` 后执行：

```bash
sh gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

Windows 使用 `gradlew.bat`。APK：`app/build/outputs/apk/debug/app-debug.apk`。

GitHub Actions 使用相同命令；在仓库 **Actions → Android CI → 最新成功任务 → Artifacts** 下载 APK 和测试报告。

## 安装和验证

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.example.spatialsdktest/.platform.LaunchActivity
adb logcat -s SpatialSdkDemo AndroidRuntime
```

1. 启动后进入 Mixed Stage；朝 Stage 的 -Z 方向查看方块和面板。
2. 用手柄射线或系统手势指针点击 `Rotate left/right`，确认模型旋转和计数变化。
3. 点击 `Smaller/Larger`，确认模型缩放且到达上下限时按钮禁用。
4. `Hide cube/Show cube` 应切换可见性；面板始终保持可操作。
5. `Reset demo` 恢复 30°、100%、可见、计数 0；退出并重新进入可重新创建场景。

方块固定在 Stage `(0, 1.85, -1.5)` 米，面板位于 `(0, 1.15, -1.5)` 米；本 demo 不实现跟随头部或自动重新定位。普通 Android 手机没有 PICO Spatial Runtime，不能用来验证空间渲染。

## 验证范围

`DemoStateTest` 覆盖双向旋转回绕、重复缩放边界、交互计数/可见性和完整重置。GitHub Actions 编译 APK、运行 JVM 测试并执行 Android lint。真机/模拟器中的空间渲染和系统指针输入需要按上述步骤人工验证。

## 代码入口

| 文件 | 职责 |
| --- | --- |
| `Main.kt` | 注册 DefaultStage、PicoTheme |
| `platform/SpatialApplication.kt` | 启动 Spatial DSL |
| `content/HomeStage.kt` | SpatialView、UI 附件和状态同步 |
| `content/DemoScene.kt` | ECS 几何、变换和资源释放 |
| `ui/ControlPanel.kt` | 空间面板与测试控件 |
| `domain/DemoState.kt` | 无 SDK 依赖的状态变换 |

源文件位于 `app/src/main/java/com/example/spatialsdktest/`。

## 参考

- [PICO Spatial SDK 官方文档](https://developer.picoxr.com/document/?platform=spatial)
- [PICO 官方示例](https://github.com/Pico-Developer/PICO-Spatial-SDK-Samples)
- 工程骨架由官方 `pico-cli project create --template stage --sdk 6.0.0` 生成；测试场景和交互逻辑为本项目实现。
