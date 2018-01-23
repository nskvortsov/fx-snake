package FxSnake.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.vcs.GitVcsRoot

object FxSnake_HttpsGithubComNskvortsovFxSnakeRefsHeadsMaster : GitVcsRoot({
    uuid = "fc73480f-de95-4798-861e-2c322dcbd494"
    id = "FxSnake_HttpsGithubComNskvortsovFxSnakeRefsHeadsMaster"
    name = "https://github.com/nskvortsov/fx-snake#refs/heads/master"
    url = "git@github.com:nskvortsov/fx-snake.git"
    authMethod = uploadedKey {
        uploadedKey = "my_key"
        passphrase = "credentialsJSON:6db4058e-bc30-47a6-9843-04655b8a0d4b"
    }
})
