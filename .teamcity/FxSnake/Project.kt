package FxSnake

import FxSnake.buildTypes.*
import FxSnake.vcsRoots.*
import FxSnake.vcsRoots.FxSnake_HttpsGithubComNskvortsovFxSnakeRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.Project
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "97766766-3d88-430d-8f89-483beb5259ce"
    id = "FxSnake"
    parentId = "_Root"
    name = "Fx Snake"

    vcsRoot(FxSnake_HttpsGithubComNskvortsovFxSnakeRefsHeadsMaster)

    buildType(FxSnake_Build)

    features {
        versionedSettings {
            id = "PROJECT_EXT_2"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.USE_CURRENT_SETTINGS
            rootExtId = FxSnake_HttpsGithubComNskvortsovFxSnakeRefsHeadsMaster.id
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }
})
