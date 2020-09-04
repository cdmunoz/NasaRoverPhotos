package co.cdmunoz.nasaroverphotos.di

fun configureTestAppModules(testBaseUrl: String) =
    listOf(testNetworkModule(testBaseUrl), repositoryModule, viewModelModule, mockWebServerModule)