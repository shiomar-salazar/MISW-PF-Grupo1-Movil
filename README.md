# SportApp Plataforma Movil
Espacio de trabajo de la Aplicacion Movil del Equipo 1 de las Materias de MISW4501-2024-11 y MISW4502-2024-12

### Integrantes:

|   Nombre                         |   Correo                      | Codigo    | 
|----------------------------------|-------------------------------|-----------|
| Jhon Fredy Guzmán Caicedo        | jf.guzmanc1@uniandes.edu.co   | 202216872 |
| Haiber Humberto Galindo Sanchez  | h.galindos@uniandes.edu.co    | 202216850 |
| Jorge M. Carrillo                | jm.carrillo@uniandes.edu.co   | 200426097 |
| Shiomar Alberto Salazar Castillo | s.salazarc@uniandes.edu.co    | 202213359 |

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
* La integracion Continua ejecuta en paralelo las pruebas unitarias y las pruebas de Instrumentacion en GCP Firebase.
* Si las Pruebas son exitosas se procede a la construccion del paquete APK de la aplicacion Movil.
* Si la creacion del paquete APK es exitoso se procede a subir ese APK como Artefacto de GitHub Actions haciendolo disponible para su descarga e Instalaccion Manual.
* Si este proceso de hacer disponible el Paquete APK es exitoso, se considera concluido el proceso de Integracion Continua y el Pull Request esta listo para hacer merge.
* Si la Rama origen del Pull Request inicia con ```Relese``` es porque esta destinada para hacer Release de Fin de Sprint.
* Si el Pull Request es de ```Release``` se inicia el proceso de Despliegue Continuo.
* El Despliegue Continuo ejecuta la Pruebas Exploratorias de la Aplicacion usando la herramienta de Robo ofrecida por GCP Firebase.
* Si las Pruebas Exploratorias son exitosas se procede a descarga la Aplicacion anteriormente guardada (del proceso de Integracion Continua) e incluir esta APK dentro de los artefactos de uun nuevo Release que se realiza.
* Si el proceso de creacion de Release en GitHub es exitoso se termina el proceso de Despliegue Continuo.

En el siguiente Diagrama se puede observar el flujo descrito anteriormente:

![FlujoCICD_Movil](https://github.com/shiomar-salazar/MISW-PF-Grupo1-Movil/assets/111320185/38aaf833-1663-49d0-8994-e051e1b633a7)


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
│     │  │        │  └─ NetworkServiceAdapter.kt
│     │  │        ├─ repositories
│     │  │        │  ├─ LoginRepository.kt
│     │  │        │  ├─ PlanAlimentacionRepository.kt
│     │  │        │  └─ PlanEntrenamientoRepository.kt
│     │  │        ├─ ui
│     │  │        │  ├─ adapters
│     │  │        │  ├─ Home.kt
│     │  │        │  ├─ MainActivity.kt
│     │  │        │  ├─ MainFragment.kt
│     │  │        │  ├─ PlanAlimentacionCreate.kt
│     │  │        │  ├─ PlanEntrenamientoCreate.kt
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
│     │  │        │  ├─ PasswordValidator.kt
│     │  │        │  └─ PlanAlimentacionValidator.kt
│     │  │        └─ viewmodels
│     │  │           ├─ HomeViewModel.kt
│     │  │           ├─ MainViewModel.kt
│     │  │           ├─ PlanAlimentacionViewModel.kt
│     │  │           └─ PlanEntrenamientoCreateViewModel.kt
│     │  └─ res
│     │     ├─ color
│     │     │  └─ text_input_box_stroke.xml
│     │     ├─ drawable
│     │     │  ├─ background.jpg
│     │     │  ├─ ic_launcher_background.xml
│     │     │  ├─ ic_launcher_foreground.xml
│     │     │  └─ logo.jpg
│     │     ├─ layout
│     │     │  ├─ home_fragment.xml
│     │     │  ├─ main_activity.xml
│     │     │  ├─ main_fragment.xml
│     │     │  ├─ planentrenamiento_crear_fragment.xml
│     │     │  └─ plan_alimentacion_create_fragment.xml
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
│                 └─ UsernameValidatorUnitTest.kt
├─ gradle
│  ├─ libs.versions.toml
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradle.properties
├─ gradlew
├─ gradlew.bat
├─ README.md
└─ robo-test.yaml

```
