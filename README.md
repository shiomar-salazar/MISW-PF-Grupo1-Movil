# SportApp Plataforma Movil
Espacio de trabajo de la Aplicacion Movil del Equipo 1 de las Materias de MISW4501-2024-11 y MISW4502-2024-12

## Integrantes:

|   Nombre                         |   Correo                      | Codigo    | 
|----------------------------------|-------------------------------|-----------|
| Jhon Fredy Guzmán Caicedo        | jf.guzmanc1@uniandes.edu.co   | 202216872 |
| Haiber Humberto Galindo Sanchez  | h.galindos@uniandes.edu.co    | 202216850 |
| Jorge M. Carrillo                | jm.carrillo@uniandes.edu.co   | 200426097 |
| Shiomar Alberto Salazar Castillo | s.salazarc@uniandes.edu.co    | 202213359 |

## Informacion Relevante del Proyecto

### Flujo de Trabajo
Para este repositorio se utilizara un proceso de GitFlow Modificado, en donde se tendran 3  tipos de ramas:

* Rama ```main```: Rama principal en donde vivira el codigo mas actualizado de la Aplicacion Movil de SportApp, la cual esta protegida para que solo mediante un Pull Request Validado se pueda meter nuevo codigo.
* Ramas ``` hotfix_``` en donde estan los cambios menores o correcciones realizadas despues de hacer merge de una las ramas de Historias de Usuario.
* Ramas de ```HU-M``` Ramas para el desarrollo de las Historias de Usuario planeadas.

En el siguiente diagrama se puede observar este Flujo de Trabajo:

![FlujoTrabajo_Movil](https://github.com/shiomar-salazar/MISW-PF-Grupo1-Movil/assets/111320185/f6505f8f-2835-4306-be84-0fe2806e23e1)

### Flujo de Integracion y Despliegue Continuo:
Para este reppsitorio se tiene implementado un sistema de CI/CD basado en GitHub, Github Actions y GCP Firebase, consistendo de las siguientes caracteristicas:

* La integracion Continua arranca cada que exista un nuevo Pull Request a la rama ```main```.
* La integracion continua hace un build completo de la aplicacion.
* Si el build es exitoso procede a realizar todas las pruebas (tanto unitarias como de integracion) sobre un emulador.
* Si las pruebas son exitosas se generan los reportes (incluidos los de cobertura de codigo y las APK creadas) y se se suben como artefacto a la ejecucion del pipeline.
* Si este proceso se considera exitoso, se da por concluido el proceso de Integracion Continua y el Pull Request esta listo para hacer merge.
* Si la Rama origen del Pull Request inicia con ```Relese``` es porque esta destinada para hacer Release de Final de Sprint.
* Si el Pull Request es de ```Release``` se inicia el proceso de Despliegue Continuo.
* El Despliegue Continuo ejecuta la Pruebas de Instrumentacion de la Aplicacion usando la herramientas ofrecidas por GCP Firebase.
* Si las pruebas anteriores son exitosas, el Despliegue Continuo ejecuta la Pruebas Exploratorias de la Aplicacion usando la herramienta de Robo ofrecida por GCP Firebase.
* Si las Pruebas Exploratorias son exitosas se procede a descarga de los artefactos geenrados por la integracion continua.
* Se procede a realizar la compilacion de la APK en su version Release.
* Se procede a Realizar el release de Final de Sprint incluyendo la APK de release y los artefactos y reportes generados por la Integracion Continua.
* Si el proceso de creacion de Release en GitHub es exitoso se termina el proceso de Despliegue Continuo.

En el siguiente Diagrama se puede observar el flujo descrito anteriormente:

![FlujoCICD_Movil](https://github.com/shiomar-salazar/MISW-PF-Grupo1-Movil/assets/111320185/3d28dd87-46b7-4e83-a16f-e30e7a1df014)

### Generacion de APK de manera local
Para esta plataforma se puede generar la APK sin la necesidad de tener Android Studio instalado, mediante el uso de la linea de comandos, para esto se deben seguir los siguientes pasos:

1. Clonar el repositorio.
2. Abrir una terminal desde la carpeta raiz del proyecto.
3. Ejecutar el siguiente comando: 
```
./gradlew build --stacktrace
```
4. La aplicacion en formato APK se generara en el siguiente directorio: ```.../app/build/outputs/apk/release```

### Ejecucion de Pruebas Unitarias de manera local
Para esta plataforma se pueden ejecutar las pruebas unitarias sin necesidad de tener Android Studio instalado, mediante el uso de la linea de comandos, para esto se deben seguir los siguientes pasos:

1. Clonar el repositorio.
2. Abrir una terminal desde la carpeta raiz del proyecto.
3. Ejecutar el siguiente comando: 
```
./gradlew test --stacktrace
```
4. Revisar el reporte que se genera en el siguiguiente directorio: ```.../app/build/reports/tests/testDebugUnitTest/index.html```

### Ejecucion de Pruebas de Integracion (Instrumentacion) de manera local
Para esta plataforma se pueden ejecutar las pruebas de integracion (llamadas en Android pruebas de Instrumentacion) sin necesidad de tener Android Studio instalado, mediante el uso de la linea de comandos, para esto se deben seguir los siguientes pasos:

1. Clonar el repositorio.
2. Abrir una terminal desde la carpeta raiz del proyecto.
3. Conectar un dispositivo Android con modo desarrollador y las opciones de USB-Debug activadas. [ver mas](https://chromium.googlesource.com/chromium/src.git/+/65.0.3283.0/docs/android_test_instructions.md#physical-device-setup)
4. Ejecutar el siguiente comando: 
```
./gradlew connectedDebugAndroidTest --stacktrace
```
5. Revisar el reporte que se genera en el siguiguiente directorio: ```.../app/build/reports/androidTests/connected/debug/index.html```

### Reporte de Cobertura de Codigo
Para esta plataforma (y por limitacion actuales de Android Studio que necesita las pruebas de Instrumentacion para generar los reportes de coverage), los reportes de cobertura de la pruebas se debe correr de manera manual, es por esto se debe seguir los siguientes pasos apra la generacion de dicho reporte:

1. Clonar el repositorio.
2. Abrir una terminal desde la carpeta raiz del proyecto.
3. Ejecutar el siguiente comando: 
```
./gradlew testDebugUnitTest connectedDebugAndroidTest testDebugUnitTestCoverage createDebugCoverageReport --stacktrace
```
4. Revisar el reporte que se genera en el siguiguiente directorio: ```.../app/build/reports/coverage/androidTest/debug/connected/index.html```

## Estructura del Proyecto

```
MISW-PF-Grupo1-Movil
├─ .git
├─ .github
│  └─ workflows
│     ├─ gcp_android_test_config.yaml
│     └─ Grupo1_CI_CD.yml
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
├─ .vscode
│  └─ settings.json
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
│     │           └─ test
│     │              ├─ AlimentacionResultTest.kt
│     │              ├─ EntrenamientoResultTest.kt
│     │              ├─ LoginTest.kt
│     │              ├─ PlanAlimentacionCreateTest.kt
│     │              └─ PlanEntrenamientoCreateTest.kt
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
│     │  │        │  ├─ LoginNetworkService.kt
│     │  │        │  └─ NetworkServiceAdapter.kt
│     │  │        ├─ repositories
│     │  │        │  ├─ AlimentacionRepository.kt
│     │  │        │  ├─ EntrenamientoRepository.kt
│     │  │        │  ├─ PlanAlimentacionRepository.kt
│     │  │        │  └─ PlanEntrenamientoRepository.kt
│     │  │        ├─ ui
│     │  │        │  ├─ adapters
│     │  │        │  ├─ AlimentacionResult.kt
│     │  │        │  ├─ EntrenamientoMenu.kt
│     │  │        │  ├─ EntrenamientoResult.kt
│     │  │        │  ├─ Home.kt
│     │  │        │  ├─ MainActivity.kt
│     │  │        │  ├─ MainFragment.kt
│     │  │        │  ├─ PlanAlimentacionCreate.kt
│     │  │        │  ├─ PlanAlimentacionDetail.kt
│     │  │        │  ├─ PlanEntrenamientoCreate.kt
│     │  │        │  └─ PlanEntrenamientoDetail.kt
│     │  │        ├─ validator
│     │  │        │  ├─ base
│     │  │        │  │  ├─ BaseValidator.kt
│     │  │        │  │  ├─ IValidator.kt
│     │  │        │  │  └─ ValidateResult.kt
│     │  │        │  ├─ EmailValidator.kt
│     │  │        │  ├─ EmptyValidator.kt
│     │  │        │  ├─ PasswordValidator.kt
│     │  │        │  ├─ PlanAlimentacionValidator.kt
│     │  │        │  └─ TimeValidator.kt
│     │  │        └─ viewmodels
│     │  │           ├─ AlimentacionResultViewModel.kt
│     │  │           ├─ EntrenamientoMenuViewModel.kt
│     │  │           ├─ EntrenamientoResultViewModel.kt
│     │  │           ├─ HomeViewModel.kt
│     │  │           ├─ PlanAlimentacionViewModel.kt
│     │  │           └─ PlanEntrenamientoViewModel.kt
│     │  └─ res
│     │     ├─ color
│     │     │  └─ text_input_box_stroke.xml
│     │     ├─ drawable
│     │     │  ├─ background.jpg
│     │     │  ├─ ic_launcher_background.xml
│     │     │  ├─ ic_launcher_foreground.xml
│     │     │  └─ logo.jpg
│     │     ├─ layout
│     │     │  ├─ alimentacion_result_fragment.xml
│     │     │  ├─ entrenamiento_menu_fragment.xml
│     │     │  ├─ entrenamiento_result_fragment.xml
│     │     │  ├─ home_fragment.xml
│     │     │  ├─ main_activity.xml
│     │     │  ├─ main_fragment.xml
│     │     │  ├─ plan_alimentacion_create_fragment.xml
│     │     │  ├─ plan_alimentacion_detail_fragment.xml
│     │     │  ├─ plan_entrenamiento_create_fragment.xml
│     │     │  └─ plan_entrenamiento_detail_fragment.xml
│     │     ├─ layout-v28
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
│     │     ├─ utils
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
│                 ├─ CaloriesValidatorUnitTest.kt
│                 ├─ ExampleUnitTest.kt
│                 ├─ PasswordValidatorUnitTest.kt
│                 ├─ TimeValidatorUnitTest.kt
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