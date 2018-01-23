package FxSnake.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

object FxSnake_Build : BuildType({
    uuid = "a2c5c2da-0622-4241-9552-40c67e6df20f"
    id = "FxSnake_Build"
    name = "Build"

    vcs {
        root(FxSnake.vcsRoots.FxSnake_HttpsGithubComNskvortsovFxSnakeRefsHeadsMaster)

    }

    steps {
        maven {
            goals = "clean test"
            mavenVersion = defaultProvidedVersion()
        }
    }

    triggers {
        vcs {
        }
    }
})
