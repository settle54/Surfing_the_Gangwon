pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "SurfingTheGangwon"
include(":app")
include(":presentation:main")
include(":presentation:together")
include(":data:together")
include(":domain:together")
include(":core:resource")
include(":core:navigation")
include(":presentation:home")
include(":core:ui")
include(":presentation:sessionReading")
include(":presentation:sessionWriting")
include(":presentation:login")
include(":presentation:myPage")
include(":core:util")
include(":core:retrofit")
include(":data:login")
include(":domain:login")
include(":core:auth")
include(":presentation:sessionStatus")
include(":core:model")
include(":data:sessionStatus")
include(":domain:sessionStatus")
include(":data:city")
include(":domain:city")
include(":data:user")
include(":data:sessionReading")
