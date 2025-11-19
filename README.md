This is a Kotlin Multiplatform (KMP) project targeting Android and iOS, built using Compose Multiplatform.

🛰️ SPACE X Kotlin

Projekt byl vytvořen v rámci náborového řízení do společnosti Quanti.

Jedná se o plně funkční multiplatformní aplikaci, která byla navržena s důrazem na snadnou rozšiřitelnost a přehledné vrstvení kódu. Aplikace byla testována na fyzických zařízeních oubou platforem (Android i iOS).

Aplikace je napojena na SpaceX API. Z důvodu nefunkčnosti endpointu rockets/:id ve verzi API V3 byla použita novější verze V4
(https://api.spacexdata.com/v4).

⸻

🚀 Hlavní funkce

Přehled raket SpaceX

Aplikace zobrazuje seznam raket SpaceX včetně jejich detailních informací.
Data jsou načítána přes Ktor z veřejného API SpaceX a zobrazována společným UI vrstvou (Compose Multiplatform).

Interaktivní Flight Simulator

Součástí aplikace je jednoduchý simulátor, který reaguje na náklon zařízení:
    •    využívá akcelerometr na Androidu (Sensor Framework)
    •    využívá akcelerometr na iOS (CoreMotion)
    •    hodnoty jsou sjednoceny do společného formátu
    •    simulace kombinuje animace, obrázky a základní fyzikální model

Při implementaci byl použit princip FRP — jak při sledování stavu obrazovky ViewModelem, tak při streamování dat ze senzorů.

Multiplatformní lokalizace

Projekt obsahuje vlastní systém lokalizace:
    •    texty jsou uloženy v XML ve shared modulu
    •    View vrstva pracuje s lokalizovanými klíči
    •    přidání dalších jazyků je snadné a nevyžaduje zásah do platformních částí

Sdílená logika a architektura

Aplikace používá MVVM architekturu a unidirectional data flow.
Plně sdílené jsou:
    •    ViewModely
    •    stav obrazovek
    •    datové zdroje a repository
    •    networking
    •    lokalizace
    •    logika simulátoru

Typed-safe navigace

Navigace je postavena na org.jetbrains.androidx.navigation:
    •    plná typová bezpečnost – žádné stringové route názvy
    •    bezpečné předávání parametrů (např. ID rakety)
    •    jedna navigace pro Android i iOS
    
Použité technologie
    •    Kotlin Multiplatform
    •    Compose Multiplatform
    •    Coroutines
    •    Ktor (networking)
    •    Koin (dependency injection)
    •    JetBrains Navigation (typed-safe navigace)
    •    Coil & KamelImage (práce s obrázky + caching)
    •    Kotlin Serialization
    •    Resource API
    •    Sensor APIs (Android/iOS)
    •    MaterialTheme 3 (barvy, fonty, typografie)

Testování

Byly vytvořeny základní testy pokrývající:
    •    repository
    •    datasources
    •    ViewModely

Cílem bylo zajistit předvídatelnost stavu a správnost přechodů mezi UI states.

⸻
⸻
⸻
⸻

🛰️ SPACE X Kotlin

This project was created as part of the recruitment process for the company Quanti.

It is a fully functional multiplatform application, designed with a focus on extensibility, clear layering, and maintainability. The application has been tested on physical devices on both platforms (Android and iOS).

The app is connected to the SpaceX API. Since the rockets/:id endpoint in API version V3 was not functional, the application uses the newer V4 version
(https://api.spacexdata.com/v4).

⸻

🚀 Main Features

SpaceX Rockets Overview

The application displays a list of SpaceX rockets, including detailed information for each model.
Data is fetched via Ktor from the public SpaceX API and rendered through a shared UI layer powered by Compose Multiplatform.

⸻

Interactive Flight Simulator

The app includes a simple simulator that reacts to device tilt:
    •    uses the accelerometer on Android (Sensor Framework)
    •    uses the accelerometer on iOS (CoreMotion)
    •    sensor values are unified into a common format
    •    the simulation combines animations, images, and a basic physics model

FRP principles were applied both in ViewModel-driven UI state observation and in streaming accelerometer data to the UI.

⸻

Multiplatform Localization

The project contains a custom localization system:
    •    text resources are stored in XML inside the shared module
    •    the UI layer works with localized keys
    •    adding new languages is simple and does not require changes in platform-specific modules

⸻

Shared Logic and Architecture

The application uses MVVM architecture with a unidirectional data flow.
The following layers are fully shared across platforms:
    •    ViewModels
    •    screen state
    •    data sources and repositories
    •    networking
    •    localization
    •    simulator logic

⸻

Typed-safe Navigation

Navigation is implemented using org.jetbrains.androidx.navigation:
    •    full type safety — no string-based route names
    •    safe parameter passing (e.g., rocket ID)
    •    one navigation system shared by Android and iOS

⸻

Technologies Used
    •    Kotlin Multiplatform
    •    Compose Multiplatform
    •    Coroutines
    •    Ktor (networking)
    •    Koin (dependency injection)
    •    JetBrains Navigation (typed-safe navigation)
    •    Coil & KamelImage (image loading + caching)
    •    Kotlin Serialization
    •    Resource API
    •    Sensor APIs (Android/iOS)
    •    MaterialTheme 3 (colors, typography, theming)

⸻

Testing

Basic tests were created to cover:
    •    repositories
    •    data sources
    •    ViewModels

The goal was to ensure predictable state handling and correct transitions between UI states.
