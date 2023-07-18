pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LatestEffort"
include(":app")
include(":data")
include(":network")
include(":domain")
include(":core:testing")
include(":data:core")
include(":data:search")
include(":feature:search-media")
include(":core:util")
