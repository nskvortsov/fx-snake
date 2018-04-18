package FxSnake.patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with uuid = 'a2c5c2da-0622-4241-9552-40c67e6df20f' (id = 'FxSnake_Build')
accordingly and delete the patch script.
*/
changeBuildType("a2c5c2da-0622-4241-9552-40c67e6df20f") {
    expectSteps {
        maven {
            goals = "clean test"
            mavenVersion = defaultProvidedVersion()
        }
        maven {
            name = "This nae will be used by maven step"
            goals = "build whatever_goal"
            mavenVersion = custom {
            }
        }
        ant {
            name = "more ant tasks"
            mode = antFile {
            }
        }
    }
    steps {
        insert(1) {
            step {
                name = "testUpload"
                type = "smb2-deploy-runner"
                param("jetbrains.buildServer.deployer.username", """LABS\Nikita.Skvortsov""")
                param("jetbrains.buildServer.deployer.sourcePath", "target/**")
                param("jetbrains.buildServer.deployer.targetUrl", """\\unit-1101\myShare\mavenResults""")
                param("secure:jetbrains.buildServer.deployer.password", "credentialsJSON:19391a2d-b113-4130-8ba2-c518c9c8cce4")
            }
        }
        items.removeAt(2)
        items.removeAt(2)
    }
}
