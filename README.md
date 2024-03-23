# SportApp Plataforma Movil
Espacio de trabajo de la Aplicacion Movil del Equipo 1 de las Materias de MISW4501-2024-11 y MISW4502-2024-12

### Pasos para Contruccion Manual de la Aplicacion


### Pasos para la Ejecucion de Pruebas Automatizadas de la Aplicacion


### Pasos para la Contruccion Automatica de la App (CI/CD)


### Estructura del Proyecto

```
MISW-PF-Grupo1-Movil
├─ .git
├─ .github
│  └─ workflows
│     └─ android.yml
├─ .gitignore
├─ .idea
│  ├─ .name
│  ├─ androidTestResultsUserPreferences.xml
│  ├─ appInsightsSettings.xml
│  ├─ compiler.xml
│  ├─ deploymentTargetDropDown.xml
│  ├─ gradle.xml
│  ├─ inspectionProfiles
│  │  └─ Project_Default.xml
│  ├─ kotlinc.xml
│  ├─ migrations.xml
│  ├─ misc.xml
│  ├─ modules
│  │  └─ app
│  └─ vcs.xml
├─ app
│  ├─ .gitignore
│  ├─ google-services.json
│  ├─ proguard-rules.pro
│  └─ src
│     ├─ androidTest
│     │  └─ java
│     │     └─ com
│     │        └─ sportapp_grupo1
│     │           ├─ ExampleInstrumentedTest.kt
│     │           ├─ test
│     │           │  └─ LoginTest.kt
│     │           └─ utils
│     │              └─ CustomAssertions.kt
│     ├─ main
│     │  ├─ AndroidManifest.xml
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ sportapp_grupo1
│     │  │        ├─ models
│     │  │        │  ├─ Alimentacion.kt
│     │  │        │  ├─ Entrenamiento.kt
│     │  │        │  ├─ PlanAlimentacion.kt
│     │  │        │  ├─ PlanEntrenamiento.kt
│     │  │        │  └─ User.kt
│     │  │        ├─ network
│     │  │        │  ├─ CacheManager.kt
│     │  │        │  └─ NetworkServiceAdapter.kt
│     │  │        ├─ repositories
│     │  │        │  └─ LoginRepository.kt
│     │  │        ├─ ui
│     │  │        │  ├─ adapters
│     │  │        │  ├─ Home.kt
│     │  │        │  ├─ MainActivity.kt
│     │  │        │  ├─ MainFragment.kt
│     │  │        │  └─ theme
│     │  │        │     ├─ Color.kt
│     │  │        │     ├─ Theme.kt
│     │  │        │     └─ Type.kt
│     │  │        ├─ validator
│     │  │        │  ├─ base
│     │  │        │  │  ├─ BaseValidator.kt
│     │  │        │  │  ├─ IValidator.kt
│     │  │        │  │  └─ ValidateResult.kt
│     │  │        │  ├─ EmailValidator.kt
│     │  │        │  ├─ EmptyValidator.kt
│     │  │        │  └─ PasswordValidator.kt
│     │  │        └─ viewmodels
│     │  │           └─ MainViewModel.kt
│     │  └─ res
│     │     ├─ drawable
│     │     │  ├─ background.jpg
│     │     │  ├─ ic_launcher_background.xml
│     │     │  ├─ ic_launcher_foreground.xml
│     │     │  └─ logo.jpg
│     │     ├─ layout
│     │     │  ├─ fragment_home.xml
│     │     │  ├─ main_activity.xml
│     │     │  └─ main_fragment.xml
│     │     ├─ mipmap-anydpi-v26
│     │     │  ├─ ic_launcher.xml
│     │     │  └─ ic_launcher_round.xml
│     │     ├─ mipmap-hdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ mipmap-mdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ mipmap-xhdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ mipmap-xxhdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ mipmap-xxxhdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ navigation
│     │     │  └─ nav_graph.xml
│     │     ├─ values
│     │     │  ├─ colors.xml
│     │     │  ├─ strings.xml
│     │     │  └─ themes.xml
│     │     └─ xml
│     │        ├─ backup_rules.xml
│     │        ├─ data_extraction_rules.xml
│     │        └─ network_security_config.xml
│     └─ test
│        └─ java
│           ├─ android
│           │  ├─ text
│           │  │  └─ TextUtils.java
│           │  └─ util
│           │     └─ Patterns.java
│           └─ com
│              └─ sportapp_grupo1
│                 ├─ ExampleUnitTest.kt
│                 ├─ PasswordValidatorUnitTest.kt
│                 └─ UsernameValidatorUnitTest.kt
├─ gradle
│  ├─ libs.versions.toml
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradle.properties
├─ gradlew
├─ gradlew.bat
└─ README.md

```