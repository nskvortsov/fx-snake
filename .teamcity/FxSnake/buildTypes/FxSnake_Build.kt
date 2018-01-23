package FxSnake.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.ant
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

        myRunner {
            name = "This nae will be used by maven step"
            goals = "build whatever_goal"
            tasks = "more ant tasks"
        }
    }

    triggers {
        vcs {
        }
    }
})


fun BuildSteps.myRunner(config: MyConfigClass.() -> Unit): Unit {
    val actualConfig = MyConfigClass()
    actualConfig.config()
    maven {
        name = actualConfig.name
        goals = actualConfig.goals
    }

    ant {
        name = actualConfig.tasks
    }
}

class MyConfigClass {
    var name = "Default Name"
    var goals = "build"
    var tasks = "build test"
}
