# RetrofitMovies
Esta es mi primera aplicación móvil nativa usando el lenguaje de programación Kotlin, la aplicación es un repositorio de películas haciendo uso de la API de TMDB.
Algunas de las funciones claves de la aplicación:
* Observar películas por temporada, mas populares en el momento y estrenos próximos.
* Visualizar detalles de películas,
* Visualizar donde se pueden ver dichas películas (Ej. Netflix, Disney+, HBO, etc.).
* Calificar películas (Haciendo uso de inicio de sesión en el portal de TMDB).
* Búsqueda de películas basada en querys/términos (Con paginación).

Librerías utilizadas:
* Picasso (Cargar imágenes de la API en UI).
* Retrofit (Hacer las llamadas a la API).
* Converter-GSON (Convertir la información de las llamadas).
* Corrutinas (Hacer llamadas asíncronas para popular la pantalla principal).
* Whynotimagecarousel (Slider de la pantalla principal).
* Paging y Lifecycle (Paging en la pantalla de búsqueda).
* SharedPreferences (Controlar la sesión para acciones como calificar películas).

Posibles mejoras:

* Pasar todo a una arquitectura (MVVM preferiblemente): Soy consciente de que el orden de los archivos es desordenado, pero tratándose de mi primera aplicación con Kotlin fue un aspecto que no tome mucho en cuenta y se los beneficios que conlleva manejar arquitecturas.

* Añadir más validaciones: Aunque la aplicación esta hecha de forma de que no falle, considero que hay parte de código que implican información que debería tener más validaciones.

* Anadir un menú drawer: Esto con la finalidad de que el usuario pueda tener acceso a mas funciones como obtener sus listas de películas calificadas, películas favoritas o etc.

* Mejorar el performance y la UX: Funciones como la paginación fue uno de los aspectos que mas tiempo me llevo implementar y la implementación resultante no es del todo de mi agrado, para ello considero que tengo que aprender más sobre cuestiones como las corrutinas, ViewModel y el ciclo de vida en general de la aplicación  
