3.1. User Stories
Las User Stories son clave en metodologías ágiles porque traducen los requisitos funcionales desde la mirada del usuario. Cada historia especifica una necesidad concreta, lo que permite planificar, priorizar y construir el sistema de forma iterativa. Así se asegura que cada función aporte valor real y permanezca alineada con las expectativas del usuario final.



Story ID

User

Priority

Epic

US01

Operador de transporte

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Monitorear temperatura en tiempo real

Description

Como operador de transporte, quiero monitorear la temperatura del contenedor en tiempo real durante todo el trayecto, para asegurar que las vacunas y medicamentos se mantengan dentro del rango permitido.

Acceptance Criteria

Escenario 01: Temperatura dentro del rango permitido

Dado que el sensor de temperatura registra valores dentro del rango configurado,
Cuando el sistema procesa los datos,
Entonces el LED se mantiene en color verde
Y no se activan alertas sonoras.
Escenario 02: Temperatura fuera del rango permitido

Dado que el sensor de temperatura registra valores fuera del rango configurado,
Cuando el sistema detecta la desviación,
Entonces se activa LED rojo y buzzer
Y se genera un flag crítico en el sistema.


Story ID

User

Priority

Epic

US02

Operador de transporte

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Monitorear humedad del contenedor

Description

Como operador de transporte, quiero monitorear los niveles de humedad dentro del contenedor, para evitar que la humedad excesiva dane los productos biomedicos sensibles.

Acceptance Criteria

Escenario 01: Humedad dentro del rango permitido

Dado que el sensor de humedad registra valores dentro del rango configurado (30%-70%),
Cuando el sistema procesa los datos,
Entonces no se genera ninguna alerta relacionada con humedad,
Y el sistema mantiene el estado normal del envio.
Escenario 02: Humedad fuera del rango permitido

Dado que el sensor de humedad registra valores por debajo o por encima del rango,
Cuando el sistema detecta la desviacion,
Entonces se activa un flag preventivo,
Y se envia una notificacion a la app del supervisor.










Story ID

User

Priority

Epic

US03

Operador de transporte

Media

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Detectar vibraciones o impactos

Description

Como operador de transporte, quiero detectar vibraciones o impactos anormales durante el traslado, para identificar si los productos han sufrido golpes que puedan comprometer su integridad.

Acceptance Criteria

Escenario 01: Vibracion dentro del umbral normal

Dado que el sensor de vibracion registra valores dentro del umbral normal,
Cuando el sistema evalua los datos,
Entonces no se genera ningun flag,
Y el envio continua sin incidentes de impacto.
Escenario 02: Vibracion que supera el umbral permitido

Dado que el sensor de vibracion registra un impacto o vibracion excesiva,
Cuando el sistema detecta la anomalia,
Entonces se registra el evento con timestamp,
Y se anade al log de incidentes del envio.

Story ID

User

Priority

Epic

US04

Supervisor de logistica

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Geolocalizar contenedor en tiempo real

Description

Como supervisor de logistica, quiero conocer la ubicacion GPS del contenedor en tiempo real, para monitorear que el transporte siga la ruta planificada.

Acceptance Criteria

Escenario 01: Visualizacion de ubicacion en mapa

Dado que el contenedor tiene conexion a internet,
Cuando el supervisor accede al dashboard web,
Entonces puede ver la ubicacion exacta del contenedor en un mapa interactivo,
Y validar que este dentro de la ruta esperada.
Escenario 02: Actualizacion de ubicacion en intervalos regulares

Dado que el contenedor esta en movimiento,
Cuando el sistema recibe datos GPS,
Entonces actualiza la posicion en el mapa cada 30 segundos o segun intervalo configurado,
Y mantiene trazabilidad continua del trayecto.

Story ID

User

Priority

Epic

US05

Supervisor de logistica

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Activar enfriamiento automatico (Peltier)

Description

Como supervisor de logistica, quiero que el sistema active automaticamente el enfriamiento (Peltier) cuando la temperatura supere el rango permitido, para proteger los productos sin intervencion manual.

Acceptance Criteria

Escenario 01: Activacion automatica de enfriamiento

Dado que la temperatura supera el rango permitido en mas de 1 C,
Cuando el sistema detecta la condicion,
Entonces activa el modulo Peltier, disipador y ventilador automaticamente,
Y protege la integridad de los productos durante el traslado.
Escenario 02: Desactivacion al normalizar temperatura

Dado que el enfriamiento esta activo y la temperatura vuelve al rango permitido,
Cuando se mantiene estable dentro del rango por 2 minutos,
Entonces el sistema desactiva automaticamente el enfriamiento,
Y vuelve al modo de monitoreo normal.

Story ID

User

Priority

Epic

US06

Administrador

Media

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Bloquear apertura del contenedor (Servo)

Description

Como administrador, quiero que el sistema bloquee automaticamente la apertura del contenedor cuando se detecte una condicion critica, para evitar que se acceda a productos en estado vulnerable.

Acceptance Criteria

Escenario 01: Bloqueo automatico por condicion critica

Dado que se detecta un flag critico (temperatura extrema o apertura no autorizada),
Cuando el sistema activa la alerta,
Entonces el servo bloquea fisicamente la apertura del contenedor,
Y se restringe el acceso hasta autorizacion valida.
Escenario 02: Desbloqueo por override autorizado

Dado que el contenedor esta bloqueado por condicion critica,
Cuando un usuario autorizado ejecuta override mediante boton fisico o app,
Entonces el sistema desbloquea el contenedor y registra la accion en el log,
Y deja trazabilidad del evento de desbloqueo.

Story ID

User

Priority

Epic

US07

Operador de transporte

Alta

EP02 - Alertas y Notificaciones

Title

Recibir alerta visual mediante LED

Description

Como operador de transporte, quiero recibir alertas visuales mediante LEDs de colores, para identificar rapidamente el estado del contenedor sin necesidad de mirar una pantalla.

Acceptance Criteria

Escenario 01: LED verde para estado normal

Dado que todas las variables estan dentro del rango permitido,
Cuando el sistema evalua las condiciones,
Entonces el LED se mantiene en color verde fijo,
Y el operador identifica que no hay riesgo actual.
Escenario 02: LED rojo para condicion critica

Dado que al menos una variable critica esta fuera del rango permitido,
Cuando el sistema detecta la condicion,
Entonces el LED parpadea en color rojo,
Y se indica una accion inmediata del operador.

Story ID

User

Priority

Epic

US08

Operador de transporte

Alta

EP02 - Alertas y Notificaciones

Title

Recibir alerta sonora mediante Buzzer

Description

Como operador de transporte, quiero recibir alertas sonoras cuando se detecte una condicion critica, para actuar inmediatamente incluso si no estoy mirando el contenedor.

Acceptance Criteria

Escenario 01: Activacion de buzzer en condicion critica

Dado que se detecta un flag critico,
Cuando el sistema activa las alertas,
Entonces el buzzer emite un sonido intermitente,
Y el operador es advertido incluso sin contacto visual.
Escenario 02: Desactivacion manual del buzzer

Dado que el buzzer esta activo por una condicion critica,
Cuando el operador presiona el boton fisico de silencio o confirma la alerta en app,
Entonces el buzzer se desactiva pero el LED rojo continua parpadeando,
Y la condicion permanece visible hasta normalizarse.

Story ID

User

Priority

Epic

US09

Supervisor de logistica

Alta

EP02 - Alertas y Notificaciones

Title

Detectar salida de geocerca

Description

Como supervisor de logistica, quiero recibir una alerta cuando el contenedor salga de la ruta o geocerca definida, para identificar desviaciones no planificadas.

Acceptance Criteria

Escenario 01: Alerta por salida de geocerca

Dado que se ha definido una geocerca para la ruta del envio,
Cuando el GPS del contenedor detecta que la ubicacion esta fuera de la geocerca,
Entonces el sistema genera un flag preventivo y envia una notificacion al supervisor,
Y se activa seguimiento reforzado del trayecto.
Escenario 02: Registro de desviacion en el log

Dado que se detecto una salida de geocerca,
Cuando el supervisor revisa el historial del envio,
Entonces puede ver el punto exacto de desviacion con timestamp y duracion,
Y usar la evidencia para analisis posterior.

Story ID

User

Priority

Epic

US10

Supervisor de logistica

Alta

EP02 - Alertas y Notificaciones

Title

Detectar apertura no autorizada del contenedor

Description

Como supervisor de logistica, quiero recibir una alerta cuando el contenedor se abra sin autorizacion, para identificar posibles manipulaciones indebidas de los productos.

Acceptance Criteria

Escenario 01: Alerta por apertura no autorizada

Dado que el contenedor esta cerrado y el sistema en modo de transporte,
Cuando se detecta la apertura del contenedor sin un override previo,
Entonces se activa flag critico, LED rojo, buzzer y notificacion remota,
Y se registra un evento de seguridad en el sistema.
Escenario 02: Registro de apertura autorizada

Dado que un usuario autorizado ejecuta override antes de abrir,
Cuando se detecta la apertura del contenedor,
Entonces se registra como apertura autorizada y no se activan alertas,
Y se conserva la trazabilidad de la autorizacion.

Story ID

User

Priority

Epic

US11

Supervisor de logistica

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Visualizar dashboard con mapa de envios activos

Description

Como supervisor de logistica, quiero visualizar un dashboard web con un mapa que muestre todos los envios activos, para supervisar su ubicacion y estado de manera centralizada.

Acceptance Criteria

Escenario 01: Mapa con todos los envios activos

Dado que hay multiples envios en curso,
Cuando el supervisor accede al dashboard,
Entonces ve un mapa con marcadores de cada contenedor activo y su estado (verde/normal, amarillo/preventivo, rojo/critico),
Y puede priorizar acciones sobre los envios criticos.
Escenario 02: Filtros por region y estado

Dado que el supervisor quiere enfocarse en una region especifica,
Cuando aplica filtros por region o estado,
Entonces el mapa muestra solo los envios que cumplen con los criterios seleccionados,
Y mejora la supervision operativa por segmento.

Story ID

User

Priority

Epic

US12

Supervisor de logistica

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Consultar logs historicos de envios

Description

Como supervisor de logistica, quiero consultar los logs historicos de envios anteriores, para analizar incidentes pasados y mejorar rutas futuras.

Acceptance Criteria

Escenario 01: Visualizacion de historial por envio

Dado que el supervisor selecciona un envio completado,
Cuando accede a los detalles del envio,
Entonces puede ver el log completo de temperatura, humedad, vibracion, ubicacion y eventos de apertura,
Y analizar incidentes con contexto completo.
Escenario 02: Exportacion de logs

Dado que el supervisor necesita compartir los logs con un auditor,
Cuando selecciona la opcion de exportacion,
Entonces el sistema genera un archivo CSV o PDF con todos los datos del envio,
Y facilita auditoria y cumplimiento documental.

Story ID

User

Priority

Epic

US13

Operador de transporte

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Recibir notificaciones push en app movil

Description

Como operador de transporte, quiero recibir notificaciones push en mi aplicacion movil cuando se detecte una condicion critica, para actuar rapidamente incluso si no estoy cerca del contenedor.

Acceptance Criteria

Escenario 01: Notificacion push por flag critico

Dado que se detecta un flag critico en el contenedor,
Cuando el sistema procesa el evento,
Entonces envia una notificacion push a la app movil del operador asignado al envio,
Y permite reaccion inmediata ante el incidente.
Escenario 02: Contenido de la notificacion

Dado que el operador recibe la notificacion,
Cuando abre la app,
Entonces puede ver que variable causo el flag, el valor registrado y la accion recomendada,
Y decidir la respuesta operativa adecuada.

Story ID

User

Priority

Epic

US14

Administrador

Media

EP03 - Dashboard Web y Aplicacion Movil

Title

Gestionar usuarios y roles desde dashboard

Description

Como administrador, quiero gestionar usuarios y asignar roles desde el dashboard web, para controlar quien tiene acceso a que funcionalidades del sistema.

Acceptance Criteria

Escenario 01: Crear nuevo usuario

Dado que el administrador esta en el panel de gestion de usuarios,
Cuando ingresa nombre, correo y selecciona un rol (Operador, Supervisor, ONG),
Entonces el sistema crea el usuario y envia un correo con instrucciones de acceso,
Y deja el registro de alta en el sistema.
Escenario 02: Modificar rol de usuario existente

Dado que un usuario cambia de puesto o responsabilidades,
Cuando el administrador modifica su rol,
Entonces el sistema actualiza los permisos del usuario sin afectar sus datos historicos,
Y aplica la nueva politica de acceso de inmediato.

Story ID

User

Priority

Epic

US15

Operador de transporte

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Almacenar datos localmente durante transporte offline

Description

Como operador de transporte, quiero que el sistema almacene todos los datos de sensores localmente cuando no hay conexion a internet, para no perder informacion durante trayectos en zonas sin cobertura.

Acceptance Criteria

Escenario 01: Almacenamiento automatico en tarjeta SD

Dado que el contenedor esta en una zona sin conectividad,
Cuando los sensores capturan datos,
Entonces el sistema guarda toda la informacion en la memoria interna o tarjeta SD,
Y evita perdida de datos durante el trayecto.
Escenario 02: Capacidad de almacenamiento suficiente

Dado que el trayecto dura hasta 7 dias sin conectividad,
Cuando el sistema almacena datos continuamente,
Entonces la tarjeta SD tiene capacidad suficiente para retener todos los datos sin sobrescribir,
Y conserva la trazabilidad completa del envio.

Story ID

User

Priority

Epic

US16

Supervisor de logistica

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Sincronizar datos con la nube automaticamente

Description

Como supervisor de logistica, quiero que los datos almacenados localmente se sincronicen automaticamente con la nube cuando haya conectividad disponible, para tener trazabilidad completa y respaldo de la informacion.

Acceptance Criteria

Escenario 01: Sincronizacion automatica al detectar WiFi

Dado que el contenedor regresa a una zona con conexion a internet,
Cuando el sistema detecta una red WiFi configurada o datos moviles,
Entonces inicia automaticamente la sincronizacion de datos pendientes con la nube,
Y mantiene actualizado el historial centralizado.
Escenario 02: Resumen de sincronizacion

Dado que la sincronizacion ha finalizado,
Cuando el supervisor revisa el dashboard,
Entonces puede ver un resumen de los datos sincronizados y confirmar que no hubo perdida de informacion,
Y valida la integridad del respaldo en nube.

Story ID

User

Priority

Epic

US17

Administrador de ONG

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Generar reporte de trazabilidad para donantes

Description

Como administrador de ONG, quiero generar un reporte de trazabilidad completo de un envio o proyecto, para presentar evidencia a donantes internacionales sobre el uso adecuado de los recursos.

Acceptance Criteria

Escenario 01: Generacion de reporte por envio

Dado que un envio ha sido completado y sincronizado,
Cuando el administrador selecciona el envio y la opcion "Generar reporte",
Entonces el sistema crea un documento PDF con graficos de temperatura, mapa de ruta, incidentes y estado final,
Y deja evidencia formal para auditoria de donantes.
Escenario 02: Reporte consolidado por proyecto

Dado que un proyecto incluye multiples envios,
Cuando el administrador selecciona el proyecto y un rango de fechas,
Entonces el sistema genera un reporte consolidado con resumen de todos los envios y estadisticas agregadas,
Y facilita la rendicion de cuentas del proyecto.

Story ID

User

Priority

Epic

US18

Administrador

Media

EP05 - Gestion de Usuarios y Roles

Title

Ejecutar override manual mediante boton fisico

Description

Como operador autorizado, quiero ejecutar override manual mediante botones fisicos en el contenedor, para desbloquear la caja o silenciar alertas en situaciones de emergencia cuando no tengo acceso a la app.

Acceptance Criteria

Escenario 01: Desbloqueo por override fisico

Dado que el contenedor esta bloqueado por una condicion critica,
Cuando el operador autorizado presiona el boton de override y ingresa su codigo,
Entonces el servo desbloquea el contenedor y se registra la accion con el ID del operador,
Y queda constancia para seguimiento posterior.
Escenario 02: Silenciar buzzer mediante boton fisico

Dado que el buzzer esta activo por una alerta,
Cuando el operador presiona el boton de silencio,
Entonces el buzzer se desactiva pero el LED rojo continua parpadeando,
Y la alerta visual se mantiene hasta resolver la condicion.

Story ID

User

Priority

Epic

US19

Supervisor de logistica

Media

EP05 - Gestion de Usuarios y Roles

Title

Ejecutar override remoto desde app o web

Description

Como supervisor de logistica, quiero ejecutar override remoto desde la app o web, para autorizar el desbloqueo del contenedor a distancia sin necesidad de que el operador este fisicamente presente.

Acceptance Criteria

Escenario 01: Override remoto por supervisor

Dado que el operador solicita autorizacion para abrir el contenedor en una zona remota,
Cuando el supervisor recibe la solicitud en la app y la aprueba,
Entonces el sistema envia una senal al contenedor para desbloquear el servo temporalmente,
Y habilita la apertura bajo control remoto autorizado.
Escenario 02: Registro de override remoto

Dado que se ejecuto un override remoto,
Cuando se revisa el log del envio,
Entonces se registra quien autorizo, desde que dispositivo, en que momento y la duracion del desbloqueo,
Y se conserva trazabilidad completa de la aprobacion.

Story ID

User

Priority

Epic

US20

Administrador

Alta

EP05 - Gestion de Usuarios y Roles

Title

Configurar rangos permitidos por tipo de producto

Description

Como administrador, quiero configurar los rangos permitidos de temperatura, humedad y vibracion segun el tipo de producto (vacunas, insulina, sangre), para que el sistema evalue correctamente las condiciones segun los requerimientos especificos de cada producto.

Acceptance Criteria

Escenario 01: Seleccion de producto predefinido

Dado que el administrador esta configurando un nuevo envio,
Cuando selecciona un tipo de producto de la lista predefinida (ej. Vacuna Pfizer, Insulina),
Entonces el sistema carga automaticamente los rangos permitidos recomendados por el fabricante,
Y aplica los parametros al envio configurado.
Escenario 02: Configuracion manual de rangos personalizados

Dado que el producto no esta en la lista predefinida,
Cuando el administrador ingresa valores manuales para temperatura minima, maxima y otros parametros,
Entonces el sistema guarda la configuracion y la aplica a todos los envios de ese producto,
Y conserva la plantilla para futuras operaciones.


Technical Stories

En esta sección se describen las historias técnicas que desarrollamos para implementar las funcionalidades clave. Cada historia define tareas específicas que el equipo de desarrollo debe realizar, como crear endpoints, supervisar el sistema, generar reportes, entre otros.


Story ID

User

Priority

Epic

TS01

Developer

Alta

EP06 - Backend y API

Title

API Sincronización de datos desde dispositivo IoT

Description

Como desarrollador, necesito exponer un endpoint para que el dispositivo CryoGuard sincronice los datos almacenados localmente (temperatura, humedad, vibración, GPS, eventos de apertura) con la nube cuando haya conectividad disponible, asegurando la integridad y trazabilidad de la información.

Acceptance Criteria

Escenario 01: Sincronización exitosa de datos

Dado que el dispositivo tiene datos pendientes en su almacenamiento local (tarjeta SD),
Cuando detecta conexión a internet y envía los datos al endpoint de sincronización,
Entonces el backend recibe, valida y almacena los datos en la base de datos,
Y retorna código 201 con confirmación de recepción.
Escenario 02: Datos duplicados o ya sincronizados

Dado que el dispositivo envía un lote de datos ya sincronizados previamente,
Cuando el endpoint recibe datos con IDs duplicados,
Entonces el backend responde con código 200 indicando que ya existen,
Y no duplica registros en la base de datos.
Escenario 03: Error de conexión durante sincronización

Dado que el dispositivo está enviando datos y la conexión se interrumpe,
Cuando el endpoint no recibe el lote completo,
Entonces el backend no almacena datos parciales,
Y el dispositivo reintenta el envío completo en la siguiente sincronización.

Story ID

User

Priority

Epic

TS02

Developer

Alta

EP06 - Backend y API

Title

API Gestión de flags y alertas

Description

Como desarrollador, necesito exponer endpoints para que el dispositivo IoT envíe flags generados localmente (edge computing) y para que la web/app consulten alertas activas, permitiendo la supervisión en tiempo real de condiciones críticas.

Acceptance Criteria

Escenario 01: Recepción de flag crítico desde dispositivo

Dado que el dispositivo detecta una condición fuera de rango (ej. temperatura > 8°C),
Cuando envía el flag al endpoint correspondiente,
Entonces el backend registra el flag con timestamp, tipo, valor y ubicación GPS,
Y activa el proceso de notificaciones push a usuarios suscritos al envío.
Escenario 02: Consulta de flags activos por usuario

Dado que un supervisor accede al dashboard,
Cuando consume el endpoint GET /flags?estado=activo,
Entonces el backend retorna la lista de flags activos ordenados por prioridad,
Y solo incluye envíos asociados al usuario (por región o rol).
Escenario 03: Confirmación de flag (override)

Dado que un usuario autorizado confirma un flag desde la app,
Cuando consume el endpoint PUT /flags/{id}/confirmar,
Entonces el backend actualiza el estado del flag a confirmado,
Y registra quién lo confirmó, desde qué dispositivo y en qué momento.


Story ID

User

Priority

Epic

TS03

Developer

Alta

EP06 - Backend y API

Title

API Gestión de usuarios y roles

Description

Como desarrollador, necesito exponer endpoints para gestionar usuarios (crear, modificar, eliminar) y asignar roles (Operador, Supervisor, Administrador, ONG), asegurando autenticación segura y control de acceso basado en permisos.

Acceptance Criteria

Escenario 01: Registro exitoso de usuario

Dado que el administrador o el registro público está disponible,
Cuando el usuario envía email, contraseña válida y datos básicos,
Entonces el backend crea la cuenta en la base de datos y retorna token JWT o código 201,
Y la contraseña se almacena hasheada (bcrypt/argon2).
Escenario 02: Registro con email duplicado o datos inválidos

Dado que el endpoint de registro recibe datos,
Cuando el email ya existe o la contraseña no cumple requisitos,
Entonces el backend responde con código 409 (duplicado) o 400 (validación),
Y retorna un mensaje claro con el error específico.
Escenario 03: Autenticación de usuario (login)

Dado que un usuario tiene una cuenta activa,
Cuando envía sus credenciales al endpoint /auth/login,
Entonces el backend valida credenciales y retorna JWT con expiración configurada,
Y el rol/permisos del usuario quedan codificados en el token.
Escenario 04: Asignación de rol por administrador

Dado que un administrador gestiona usuarios desde el dashboard,
Cuando asigna o modifica el rol de un usuario,
Entonces el backend actualiza permisos del usuario,
Y registra la acción en un log de auditoría.

Story ID

User

Priority

Epic

TS04

Developer

Media

EP06 - Backend y API

Title

API Generación de reportes de trazabilidad

Description

Como desarrollador, necesito exponer endpoints para generar reportes de trazabilidad (por envío, por proyecto o por rango de fechas) en formatos PDF y Excel, para que supervisores y ONGs presenten evidencia a donantes y entes reguladores.

Acceptance Criteria

Escenario 01: Generación de reporte por ID de envío

Dado que un supervisor selecciona un envío completado,
Cuando consume GET /reportes/envio/{id}?formato=pdf,
Entonces el backend genera un PDF con gráficos, mapa de ruta, incidentes y estado final,
Y retorna el archivo para descarga.
Escenario 02: Generación de reporte consolidado por proyecto

Dado que una ONG necesita reportar múltiples envíos de un proyecto,
Cuando consume POST /reportes/proyecto con rango de fechas y lista de envíos,
Entonces el backend genera un consolidado con resumen estadístico y porcentaje de éxito,
Y retorna el archivo en formato PDF o Excel.
Escenario 03: Cache de reportes para evitar regeneración

Dado que el mismo reporte se solicita múltiples veces sin cambios de datos,
Cuando el backend recibe una solicitud idéntica (mismos parámetros),
Entonces retorna el reporte en caché sin regenerar documento,
Y reduce tiempo de respuesta y carga del servidor.





Story ID

User

Priority

Epic

TS05

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar lectura de sensores (temp, humedad, vibración, GPS, apertura)

Description

Como desarrollador embedded, necesito implementar la lectura de todos los sensores del dispositivo CryoGuard, asegurando captura de datos en intervalos configurables y almacenamiento local en tarjeta SD.

Acceptance Criteria

Escenario 01: Lectura periódica de sensores

Dado que el dispositivo está encendido y en modo operación,
Cuando el temporizador alcanza el intervalo configurado (ej. cada 30 segundos),
Entonces el sistema lee todos los sensores,
Y almacena datos en tarjeta SD con timestamp y GPS actual.
Escenario 02: Almacenamiento local en tarjeta SD

Dado que el dispositivo está en zona sin conectividad,
Cuando los sensores capturan datos,
Entonces los datos se escriben en la tarjeta SD en formato CSV o JSON,
Y se mantienen hasta la siguiente sincronización exitosa.
Escenario 03: Detección de fallo de sensor

Dado que un sensor no responde o envía datos inválidos,
Cuando el sistema intenta leerlo,
Entonces registra un error interno y continúa con los sensores restantes,
Y genera un flag de mantenimiento para el supervisor.



Story ID

User

Priority

Epic

TS06

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar reglas de edge computing para generación de flags

Description

Como desarrollador embedded, necesito implementar un motor de reglas que evalúe localmente los datos de sensores y genere flags preventivos o críticos cuando las variables salgan de rango, activando actuadores automáticamente.

Acceptance Criteria

Escenario 01: Evaluación de temperatura fuera de rango

Dado que el rango permitido es 2°C a 8°C,
Cuando el sensor registra 9°C durante más de 30 segundos,
Entonces el sistema genera un flag crítico,
Y activa LED rojo, buzzer y Peltier automáticamente.
Escenario 02: Evaluación de temperatura cercana al límite

Dado que el rango permitido es 2°C a 8°C,
Cuando el sensor registra 7.5°C cercano al límite superior,
Entonces el sistema genera un flag preventivo,
Y activa LED amarillo sin buzzer.
Escenario 03: Evaluación de vibración excesiva

Dado que existe un umbral máximo de vibración configurado,
Cuando el sensor registra un valor superior al umbral,
Entonces el sistema genera un flag de vibración,
Y registra el evento sin activar enfriamiento.
Escenario 04: Evaluación de apertura no autorizada

Dado que el contenedor está cerrado y no hay override activo,
Cuando el sensor de apertura detecta que la caja se abrió,
Entonces el sistema genera un flag crítico de apertura no autorizada,
Y activa LED rojo, buzzer y bloqueo de servo.

Story ID

User

Priority

Epic

TS07

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar control de actuadores (Peltier, Servo, LED, Buzzer)

Description

Como desarrollador embedded, necesito implementar el control de actuadores del dispositivo por reglas automáticas y por comandos manuales (botones físicos o remotos).

Acceptance Criteria

Escenario 01: Activación automática de Peltier por temperatura alta

Dado que se detectó un flag crítico por temperatura alta,
Cuando el sistema evalúa la regla correspondiente,
Entonces activa módulo Peltier, disipador y ventilador,
Y mantiene enfriamiento hasta normalización sostenida por 2 minutos.
Escenario 02: Activación de servo para bloqueo de apertura

Dado que se detectó un flag crítico o apertura no autorizada,
Cuando el sistema activa el bloqueo,
Entonces el servo bloquea físicamente la apertura del contenedor,
Y solo se desbloquea con override autorizado.
Escenario 03: Control de LEDs según estado

Dado que no hay flags activos, cuando se evalúa estado normal, entonces LED verde fijo.
Dado que hay flag preventivo activo, cuando la condición es cercana al límite, entonces LED amarillo fijo.
Dado que hay flag crítico activo, cuando la condición está fuera de rango, entonces LED rojo parpadeante.
Y el patrón visual refleja de forma inmediata la severidad actual.
Escenario 04: Control de buzzer para alertas sonoras

Dado que hay un flag crítico activo,
Cuando se activa la alerta sonora,
Entonces el buzzer emite pitido intermitente y puede silenciarse por botón físico,
Pero el LED rojo continúa parpadeando hasta normalizar la condición.

Story ID

User

Priority

Epic

TS08

Embedded Developer

Media

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar botones físicos para override y control manual

Description

Como desarrollador embedded, necesito implementar lectura de botones físicos (override, silencio, prueba de LEDs, reset) para permitir acciones manuales sin depender de app o web.

Acceptance Criteria

Escenario 01: Override manual mediante botón físico

Dado que el contenedor está bloqueado por un flag crítico,
Cuando el operador autorizado presiona el botón de override por 3 segundos,
Entonces el sistema desbloquea el servo por 30 segundos,
Y registra la acción en el log con timestamp e identificador.
Escenario 02: Silencio de buzzer mediante botón físico

Dado que el buzzer está activo por un flag crítico,
Cuando el operador presiona el botón de silencio,
Entonces el buzzer se desactiva inmediatamente,
Pero el LED rojo continúa parpadeando hasta normalizar la condición.
Escenario 03: Prueba de LEDs y buzzer

Dado que el operador quiere verificar actuadores,
Cuando presiona el botón de prueba,
Entonces el sistema enciende LED rojo, amarillo y verde en secuencia,
Y activa el buzzer por 1 segundo.

Story ID

User

Priority

Epic

TS09

Frontend Developer

Alta

EP08 - Dashboard Web

Title

Implementar dashboard con mapa interactivo de envíos activos

Description

Como desarrollador frontend, necesito implementar un dashboard web con mapa interactivo para visualizar contenedores activos y su estado en tiempo real.

Acceptance Criteria

Escenario 01: Carga inicial del mapa con envíos activos

Dado que un supervisor autenticado accede al dashboard,
Cuando la página se carga,
Entonces el frontend consume GET /envios/activos y renderiza marcadores activos,
Y cada marcador muestra color según estado del envío.
Escenario 02: Actualización en tiempo real de posiciones

Dado que el dashboard está abierto y hay envíos en movimiento,
Cuando el backend recibe nuevos datos de ubicación,
Entonces el frontend actualiza posiciones sin recargar la página,
Mediante WebSockets o polling cada 10 segundos.
Escenario 03: Click en marcador para ver detalles del envío

Dado que el supervisor hace click en un marcador,
Cuando se abre un panel lateral,
Entonces muestra ID, producto, temperatura actual, flags activos y último dato de humedad,
Y permite supervisión operativa contextual por envío.

Story ID

User

Priority

Epic

TS10

Frontend Developer

Alta

EP08 - Dashboard Web

Title

Implementar tabla de logs históricos con filtros y exportación

Description

Como desarrollador frontend, necesito implementar tabla paginada de logs históricos con filtros por fecha/región/producto/estado y exportación a CSV/PDF.

Acceptance Criteria

Escenario 01: Visualización de tabla de logs

Dado que un supervisor accede a la sección de historial,
Cuando la página se carga,
Entonces el frontend consume GET /logs?page=1&limit=50 y renderiza tabla de resultados,
Y muestra columnas de envío, fecha, producto, promedio, incidentes y estado final.
Escenario 02: Filtros combinados

Dado que el supervisor aplica filtros combinados,
Cuando hace click en Aplicar filtros,
Entonces el frontend consume endpoint con parámetros de filtro,
Y actualiza la tabla con resultados filtrados.
Escenario 03: Exportación a CSV/PDF

Dado que el supervisor necesita exportar los logs filtrados,
Cuando hace click en Exportar y selecciona CSV,
Entonces el frontend descarga archivo con los datos de la tabla actual,
Y respeta filtros aplicados en la exportación.

Story ID

User

Priority

Epic

TS11

Frontend Developer

Media

EP08 - Dashboard Web

Title

Implementar panel de administración de usuarios y roles

Description

Como desarrollador frontend, necesito implementar un panel para crear, modificar, eliminar usuarios y asignar roles (Operador, Supervisor, Administrador, ONG).

Acceptance Criteria

Escenario 01: Listado de usuarios con paginación

Dado que un administrador accede al panel de usuarios,
Cuando la página se carga,
Entonces el frontend consume GET /admin/usuarios y renderiza la tabla,
Y muestra nombre, email, rol, fecha y estado.
Escenario 02: Creación de nuevo usuario

Dado que el administrador hace click en Nuevo usuario,
Cuando completa formulario y confirma,
Entonces el frontend envía POST /admin/usuarios,
Y actualiza la tabla con el nuevo registro.
Escenario 03: Edición y eliminación de usuarios

Dado que el administrador selecciona un usuario de la tabla,
Cuando edita rol o deshabilita cuenta,
Entonces el frontend envía PUT o DELETE según corresponda,
Y refresca la tabla reflejando los cambios.

Story ID

User

Priority

Epic

TS12

Mobile Developer

Alta

EP09 - Aplicación Móvil

Title

Implementar autenticación y pantalla de inicio en app móvil

Description

Como desarrollador móvil (iOS/Android), necesito implementar login/registro y pantalla de inicio para que operadores y supervisores accedan y vean sus envíos asignados.

Acceptance Criteria

Escenario 01: Login con email y contraseña

Dado que el usuario tiene una cuenta activa,
Cuando ingresa credenciales y presiona Iniciar sesión,
Entonces la app consume POST /auth/login y almacena JWT de forma segura,
Y navega a la pantalla de inicio.
Escenario 02: Almacenamiento seguro de token

Dado que el usuario cierra y abre nuevamente la app,
Cuando el token aún no ha expirado,
Entonces la app restaura sesión automáticamente,
Y evita solicitar login nuevamente.
Escenario 03: Pantalla de inicio con resumen de envíos

Dado que el usuario está autenticado y tiene envíos asignados,
Cuando la app carga la pantalla de inicio,
Entonces consume GET /envios/asignados y muestra lista de envíos activos,
Y presenta ID, destino, temperatura y estado.

Story ID

User

Priority

Epic

TS13

Mobile Developer

Alta

EP09 - Aplicación Móvil

Title

Implementar recepción de notificaciones push

Description

Como desarrollador móvil, necesito implementar recepción de notificaciones push (FCM/APNs) para alertas en tiempo real por flags críticos de envíos asignados.

Acceptance Criteria

Escenario 01: Registro del dispositivo para notificaciones push

Dado que el usuario inicia sesión por primera vez,
Cuando la app solicita permisos y el usuario los acepta,
Entonces la app obtiene el token push y lo envía al backend,
Y queda asociado al usuario autenticado.
Escenario 02: Recepción de notificación por flag crítico

Dado que el usuario tiene sesión activa o app en segundo plano,
Cuando backend envía notificación por flag crítico asignado,
Entonces la app muestra notificación en el centro del dispositivo,
Y al tocarla abre detalle del envío.
Escenario 03: Contenido de la notificación

Dado que el usuario recibe una notificación push,
Cuando la visualiza,
Entonces el título indica Alerta CryoGuard - Flag Crítico,
Y el cuerpo muestra tipo de flag, valor e ID de envío.


Story ID

User

Priority

Epic

TS14

Mobile Developer

Media

EP09 - Aplicación Móvil

Title

Implementar override remoto desde app móvil

Description

Como desarrollador móvil, necesito implementar override remoto para que supervisores autorizados desbloqueen contenedor a distancia o gestionen alertas cuando lo solicite el operador.

Acceptance Criteria

Escenario 01: Solicitud de override por operador

Dado que el contenedor está bloqueado y el operador necesita abrirlo,
Cuando el operador solicita autorización desde app o por comunicación externa,
Entonces el supervisor recibe una notificación push con la solicitud,
Y puede revisar contexto del envío antes de aprobar.
Escenario 02: Aprobación de override por supervisor

Dado que el supervisor recibe la solicitud de override,
Cuando abre la app, selecciona envío y presiona Autorizar desbloqueo,
Entonces la app consume POST /envios/{id}/override-remoto y backend envía comando al IoT,
Y registra la acción en el log con ID del supervisor.
Escenario 03: Confirmación de override exitoso

Dado que el comando de override fue enviado exitosamente,
Cuando el dispositivo confirma la recepción,
Entonces la app muestra mensaje Contenedor desbloqueado temporalmente,
Y el operador puede abrir la caja.

Las User Stories son clave en metodologías ágiles porque traducen los requisitos funcionales desde la mirada del usuario. Cada historia especifica una necesidad concreta, lo que permite planificar, priorizar y construir el sistema de forma iterativa. Así se asegura que cada función aporte valor real y permanezca alineada con las expectativas del usuario final.



Story ID

User

Priority

Epic

US01

Operador de transporte

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Monitorear temperatura en tiempo real

Description

Como operador de transporte, quiero monitorear la temperatura del contenedor en tiempo real durante todo el trayecto, para asegurar que las vacunas y medicamentos se mantengan dentro del rango permitido.

Acceptance Criteria

Escenario 01: Temperatura dentro del rango permitido

Dado que el sensor de temperatura registra valores dentro del rango configurado,
Cuando el sistema procesa los datos,
Entonces el LED se mantiene en color verde
Y no se activan alertas sonoras.
Escenario 02: Temperatura fuera del rango permitido

Dado que el sensor de temperatura registra valores fuera del rango configurado,
Cuando el sistema detecta la desviación,
Entonces se activa LED rojo y buzzer
Y se genera un flag crítico en el sistema.


Story ID

User

Priority

Epic

US02

Operador de transporte

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Monitorear humedad del contenedor

Description

Como operador de transporte, quiero monitorear los niveles de humedad dentro del contenedor, para evitar que la humedad excesiva dane los productos biomedicos sensibles.

Acceptance Criteria

Escenario 01: Humedad dentro del rango permitido

Dado que el sensor de humedad registra valores dentro del rango configurado (30%-70%),
Cuando el sistema procesa los datos,
Entonces no se genera ninguna alerta relacionada con humedad,
Y el sistema mantiene el estado normal del envio.
Escenario 02: Humedad fuera del rango permitido

Dado que el sensor de humedad registra valores por debajo o por encima del rango,
Cuando el sistema detecta la desviacion,
Entonces se activa un flag preventivo,
Y se envia una notificacion a la app del supervisor.










Story ID

User

Priority

Epic

US03

Operador de transporte

Media

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Detectar vibraciones o impactos

Description

Como operador de transporte, quiero detectar vibraciones o impactos anormales durante el traslado, para identificar si los productos han sufrido golpes que puedan comprometer su integridad.

Acceptance Criteria

Escenario 01: Vibracion dentro del umbral normal

Dado que el sensor de vibracion registra valores dentro del umbral normal,
Cuando el sistema evalua los datos,
Entonces no se genera ningun flag,
Y el envio continua sin incidentes de impacto.
Escenario 02: Vibracion que supera el umbral permitido

Dado que el sensor de vibracion registra un impacto o vibracion excesiva,
Cuando el sistema detecta la anomalia,
Entonces se registra el evento con timestamp,
Y se anade al log de incidentes del envio.

Story ID

User

Priority

Epic

US04

Supervisor de logistica

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Geolocalizar contenedor en tiempo real

Description

Como supervisor de logistica, quiero conocer la ubicacion GPS del contenedor en tiempo real, para monitorear que el transporte siga la ruta planificada.

Acceptance Criteria

Escenario 01: Visualizacion de ubicacion en mapa

Dado que el contenedor tiene conexion a internet,
Cuando el supervisor accede al dashboard web,
Entonces puede ver la ubicacion exacta del contenedor en un mapa interactivo,
Y validar que este dentro de la ruta esperada.
Escenario 02: Actualizacion de ubicacion en intervalos regulares

Dado que el contenedor esta en movimiento,
Cuando el sistema recibe datos GPS,
Entonces actualiza la posicion en el mapa cada 30 segundos o segun intervalo configurado,
Y mantiene trazabilidad continua del trayecto.

Story ID

User

Priority

Epic

US05

Supervisor de logistica

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Activar enfriamiento automatico (Peltier)

Description

Como supervisor de logistica, quiero que el sistema active automaticamente el enfriamiento (Peltier) cuando la temperatura supere el rango permitido, para proteger los productos sin intervencion manual.

Acceptance Criteria

Escenario 01: Activacion automatica de enfriamiento

Dado que la temperatura supera el rango permitido en mas de 1 C,
Cuando el sistema detecta la condicion,
Entonces activa el modulo Peltier, disipador y ventilador automaticamente,
Y protege la integridad de los productos durante el traslado.
Escenario 02: Desactivacion al normalizar temperatura

Dado que el enfriamiento esta activo y la temperatura vuelve al rango permitido,
Cuando se mantiene estable dentro del rango por 2 minutos,
Entonces el sistema desactiva automaticamente el enfriamiento,
Y vuelve al modo de monitoreo normal.

Story ID

User

Priority

Epic

US06

Administrador

Media

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Bloquear apertura del contenedor (Servo)

Description

Como administrador, quiero que el sistema bloquee automaticamente la apertura del contenedor cuando se detecte una condicion critica, para evitar que se acceda a productos en estado vulnerable.

Acceptance Criteria

Escenario 01: Bloqueo automatico por condicion critica

Dado que se detecta un flag critico (temperatura extrema o apertura no autorizada),
Cuando el sistema activa la alerta,
Entonces el servo bloquea fisicamente la apertura del contenedor,
Y se restringe el acceso hasta autorizacion valida.
Escenario 02: Desbloqueo por override autorizado

Dado que el contenedor esta bloqueado por condicion critica,
Cuando un usuario autorizado ejecuta override mediante boton fisico o app,
Entonces el sistema desbloquea el contenedor y registra la accion en el log,
Y deja trazabilidad del evento de desbloqueo.

Story ID

User

Priority

Epic

US07

Operador de transporte

Alta

EP02 - Alertas y Notificaciones

Title

Recibir alerta visual mediante LED

Description

Como operador de transporte, quiero recibir alertas visuales mediante LEDs de colores, para identificar rapidamente el estado del contenedor sin necesidad de mirar una pantalla.

Acceptance Criteria

Escenario 01: LED verde para estado normal

Dado que todas las variables estan dentro del rango permitido,
Cuando el sistema evalua las condiciones,
Entonces el LED se mantiene en color verde fijo,
Y el operador identifica que no hay riesgo actual.
Escenario 02: LED rojo para condicion critica

Dado que al menos una variable critica esta fuera del rango permitido,
Cuando el sistema detecta la condicion,
Entonces el LED parpadea en color rojo,
Y se indica una accion inmediata del operador.

Story ID

User

Priority

Epic

US08

Operador de transporte

Alta

EP02 - Alertas y Notificaciones

Title

Recibir alerta sonora mediante Buzzer

Description

Como operador de transporte, quiero recibir alertas sonoras cuando se detecte una condicion critica, para actuar inmediatamente incluso si no estoy mirando el contenedor.

Acceptance Criteria

Escenario 01: Activacion de buzzer en condicion critica

Dado que se detecta un flag critico,
Cuando el sistema activa las alertas,
Entonces el buzzer emite un sonido intermitente,
Y el operador es advertido incluso sin contacto visual.
Escenario 02: Desactivacion manual del buzzer

Dado que el buzzer esta activo por una condicion critica,
Cuando el operador presiona el boton fisico de silencio o confirma la alerta en app,
Entonces el buzzer se desactiva pero el LED rojo continua parpadeando,
Y la condicion permanece visible hasta normalizarse.

Story ID

User

Priority

Epic

US09

Supervisor de logistica

Alta

EP02 - Alertas y Notificaciones

Title

Detectar salida de geocerca

Description

Como supervisor de logistica, quiero recibir una alerta cuando el contenedor salga de la ruta o geocerca definida, para identificar desviaciones no planificadas.

Acceptance Criteria

Escenario 01: Alerta por salida de geocerca

Dado que se ha definido una geocerca para la ruta del envio,
Cuando el GPS del contenedor detecta que la ubicacion esta fuera de la geocerca,
Entonces el sistema genera un flag preventivo y envia una notificacion al supervisor,
Y se activa seguimiento reforzado del trayecto.
Escenario 02: Registro de desviacion en el log

Dado que se detecto una salida de geocerca,
Cuando el supervisor revisa el historial del envio,
Entonces puede ver el punto exacto de desviacion con timestamp y duracion,
Y usar la evidencia para analisis posterior.

Story ID

User

Priority

Epic

US10

Supervisor de logistica

Alta

EP02 - Alertas y Notificaciones

Title

Detectar apertura no autorizada del contenedor

Description

Como supervisor de logistica, quiero recibir una alerta cuando el contenedor se abra sin autorizacion, para identificar posibles manipulaciones indebidas de los productos.

Acceptance Criteria

Escenario 01: Alerta por apertura no autorizada

Dado que el contenedor esta cerrado y el sistema en modo de transporte,
Cuando se detecta la apertura del contenedor sin un override previo,
Entonces se activa flag critico, LED rojo, buzzer y notificacion remota,
Y se registra un evento de seguridad en el sistema.
Escenario 02: Registro de apertura autorizada

Dado que un usuario autorizado ejecuta override antes de abrir,
Cuando se detecta la apertura del contenedor,
Entonces se registra como apertura autorizada y no se activan alertas,
Y se conserva la trazabilidad de la autorizacion.

Story ID

User

Priority

Epic

US11

Supervisor de logistica

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Visualizar dashboard con mapa de envios activos

Description

Como supervisor de logistica, quiero visualizar un dashboard web con un mapa que muestre todos los envios activos, para supervisar su ubicacion y estado de manera centralizada.

Acceptance Criteria

Escenario 01: Mapa con todos los envios activos

Dado que hay multiples envios en curso,
Cuando el supervisor accede al dashboard,
Entonces ve un mapa con marcadores de cada contenedor activo y su estado (verde/normal, amarillo/preventivo, rojo/critico),
Y puede priorizar acciones sobre los envios criticos.
Escenario 02: Filtros por region y estado

Dado que el supervisor quiere enfocarse en una region especifica,
Cuando aplica filtros por region o estado,
Entonces el mapa muestra solo los envios que cumplen con los criterios seleccionados,
Y mejora la supervision operativa por segmento.

Story ID

User

Priority

Epic

US12

Supervisor de logistica

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Consultar logs historicos de envios

Description

Como supervisor de logistica, quiero consultar los logs historicos de envios anteriores, para analizar incidentes pasados y mejorar rutas futuras.

Acceptance Criteria

Escenario 01: Visualizacion de historial por envio

Dado que el supervisor selecciona un envio completado,
Cuando accede a los detalles del envio,
Entonces puede ver el log completo de temperatura, humedad, vibracion, ubicacion y eventos de apertura,
Y analizar incidentes con contexto completo.
Escenario 02: Exportacion de logs

Dado que el supervisor necesita compartir los logs con un auditor,
Cuando selecciona la opcion de exportacion,
Entonces el sistema genera un archivo CSV o PDF con todos los datos del envio,
Y facilita auditoria y cumplimiento documental.

Story ID

User

Priority

Epic

US13

Operador de transporte

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Recibir notificaciones push en app movil

Description

Como operador de transporte, quiero recibir notificaciones push en mi aplicacion movil cuando se detecte una condicion critica, para actuar rapidamente incluso si no estoy cerca del contenedor.

Acceptance Criteria

Escenario 01: Notificacion push por flag critico

Dado que se detecta un flag critico en el contenedor,
Cuando el sistema procesa el evento,
Entonces envia una notificacion push a la app movil del operador asignado al envio,
Y permite reaccion inmediata ante el incidente.
Escenario 02: Contenido de la notificacion

Dado que el operador recibe la notificacion,
Cuando abre la app,
Entonces puede ver que variable causo el flag, el valor registrado y la accion recomendada,
Y decidir la respuesta operativa adecuada.

Story ID

User

Priority

Epic

US14

Administrador

Media

EP03 - Dashboard Web y Aplicacion Movil

Title

Gestionar usuarios y roles desde dashboard

Description

Como administrador, quiero gestionar usuarios y asignar roles desde el dashboard web, para controlar quien tiene acceso a que funcionalidades del sistema.

Acceptance Criteria

Escenario 01: Crear nuevo usuario

Dado que el administrador esta en el panel de gestion de usuarios,
Cuando ingresa nombre, correo y selecciona un rol (Operador, Supervisor, ONG),
Entonces el sistema crea el usuario y envia un correo con instrucciones de acceso,
Y deja el registro de alta en el sistema.
Escenario 02: Modificar rol de usuario existente

Dado que un usuario cambia de puesto o responsabilidades,
Cuando el administrador modifica su rol,
Entonces el sistema actualiza los permisos del usuario sin afectar sus datos historicos,
Y aplica la nueva politica de acceso de inmediato.

Story ID

User

Priority

Epic

US15

Operador de transporte

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Almacenar datos localmente durante transporte offline

Description

Como operador de transporte, quiero que el sistema almacene todos los datos de sensores localmente cuando no hay conexion a internet, para no perder informacion durante trayectos en zonas sin cobertura.

Acceptance Criteria

Escenario 01: Almacenamiento automatico en tarjeta SD

Dado que el contenedor esta en una zona sin conectividad,
Cuando los sensores capturan datos,
Entonces el sistema guarda toda la informacion en la memoria interna o tarjeta SD,
Y evita perdida de datos durante el trayecto.
Escenario 02: Capacidad de almacenamiento suficiente

Dado que el trayecto dura hasta 7 dias sin conectividad,
Cuando el sistema almacena datos continuamente,
Entonces la tarjeta SD tiene capacidad suficiente para retener todos los datos sin sobrescribir,
Y conserva la trazabilidad completa del envio.

Story ID

User

Priority

Epic

US16

Supervisor de logistica

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Sincronizar datos con la nube automaticamente

Description

Como supervisor de logistica, quiero que los datos almacenados localmente se sincronicen automaticamente con la nube cuando haya conectividad disponible, para tener trazabilidad completa y respaldo de la informacion.

Acceptance Criteria

Escenario 01: Sincronizacion automatica al detectar WiFi

Dado que el contenedor regresa a una zona con conexion a internet,
Cuando el sistema detecta una red WiFi configurada o datos moviles,
Entonces inicia automaticamente la sincronizacion de datos pendientes con la nube,
Y mantiene actualizado el historial centralizado.
Escenario 02: Resumen de sincronizacion

Dado que la sincronizacion ha finalizado,
Cuando el supervisor revisa el dashboard,
Entonces puede ver un resumen de los datos sincronizados y confirmar que no hubo perdida de informacion,
Y valida la integridad del respaldo en nube.

Story ID

User

Priority

Epic

US17

Administrador de ONG

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Generar reporte de trazabilidad para donantes

Description

Como administrador de ONG, quiero generar un reporte de trazabilidad completo de un envio o proyecto, para presentar evidencia a donantes internacionales sobre el uso adecuado de los recursos.

Acceptance Criteria

Escenario 01: Generacion de reporte por envio

Dado que un envio ha sido completado y sincronizado,
Cuando el administrador selecciona el envio y la opcion "Generar reporte",
Entonces el sistema crea un documento PDF con graficos de temperatura, mapa de ruta, incidentes y estado final,
Y deja evidencia formal para auditoria de donantes.
Escenario 02: Reporte consolidado por proyecto

Dado que un proyecto incluye multiples envios,
Cuando el administrador selecciona el proyecto y un rango de fechas,
Entonces el sistema genera un reporte consolidado con resumen de todos los envios y estadisticas agregadas,
Y facilita la rendicion de cuentas del proyecto.

Story ID

User

Priority

Epic

US18

Administrador

Media

EP05 - Gestion de Usuarios y Roles

Title

Ejecutar override manual mediante boton fisico

Description

Como operador autorizado, quiero ejecutar override manual mediante botones fisicos en el contenedor, para desbloquear la caja o silenciar alertas en situaciones de emergencia cuando no tengo acceso a la app.

Acceptance Criteria

Escenario 01: Desbloqueo por override fisico

Dado que el contenedor esta bloqueado por una condicion critica,
Cuando el operador autorizado presiona el boton de override y ingresa su codigo,
Entonces el servo desbloquea el contenedor y se registra la accion con el ID del operador,
Y queda constancia para seguimiento posterior.
Escenario 02: Silenciar buzzer mediante boton fisico

Dado que el buzzer esta activo por una alerta,
Cuando el operador presiona el boton de silencio,
Entonces el buzzer se desactiva pero el LED rojo continua parpadeando,
Y la alerta visual se mantiene hasta resolver la condicion.

Story ID

User

Priority

Epic

US19

Supervisor de logistica

Media

EP05 - Gestion de Usuarios y Roles

Title

Ejecutar override remoto desde app o web

Description

Como supervisor de logistica, quiero ejecutar override remoto desde la app o web, para autorizar el desbloqueo del contenedor a distancia sin necesidad de que el operador este fisicamente presente.

Acceptance Criteria

Escenario 01: Override remoto por supervisor

Dado que el operador solicita autorizacion para abrir el contenedor en una zona remota,
Cuando el supervisor recibe la solicitud en la app y la aprueba,
Entonces el sistema envia una senal al contenedor para desbloquear el servo temporalmente,
Y habilita la apertura bajo control remoto autorizado.
Escenario 02: Registro de override remoto

Dado que se ejecuto un override remoto,
Cuando se revisa el log del envio,
Entonces se registra quien autorizo, desde que dispositivo, en que momento y la duracion del desbloqueo,
Y se conserva trazabilidad completa de la aprobacion.

Story ID

User

Priority

Epic

US20

Administrador

Alta

EP05 - Gestion de Usuarios y Roles

Title

Configurar rangos permitidos por tipo de producto

Description

Como administrador, quiero configurar los rangos permitidos de temperatura, humedad y vibracion segun el tipo de producto (vacunas, insulina, sangre), para que el sistema evalue correctamente las condiciones segun los requerimientos especificos de cada producto.

Acceptance Criteria

Escenario 01: Seleccion de producto predefinido

Dado que el administrador esta configurando un nuevo envio,
Cuando selecciona un tipo de producto de la lista predefinida (ej. Vacuna Pfizer, Insulina),
Entonces el sistema carga automaticamente los rangos permitidos recomendados por el fabricante,
Y aplica los parametros al envio configurado.
Escenario 02: Configuracion manual de rangos personalizados

Dado que el producto no esta en la lista predefinida,
Cuando el administrador ingresa valores manuales para temperatura minima, maxima y otros parametros,
Entonces el sistema guarda la configuracion y la aplica a todos los envios de ese producto,
Y conserva la plantilla para futuras operaciones.


Technical Stories

En esta sección se describen las historias técnicas que desarrollamos para implementar las funcionalidades clave. Cada historia define tareas específicas que el equipo de desarrollo debe realizar, como crear endpoints, supervisar el sistema, generar reportes, entre otros.


Story ID

User

Priority

Epic

TS01

Developer

Alta

EP06 - Backend y API

Title

API Sincronización de datos desde dispositivo IoT

Description

Como desarrollador, necesito exponer un endpoint para que el dispositivo CryoGuard sincronice los datos almacenados localmente (temperatura, humedad, vibración, GPS, eventos de apertura) con la nube cuando haya conectividad disponible, asegurando la integridad y trazabilidad de la información.

Acceptance Criteria

Escenario 01: Sincronización exitosa de datos

Dado que el dispositivo tiene datos pendientes en su almacenamiento local (tarjeta SD),
Cuando detecta conexión a internet y envía los datos al endpoint de sincronización,
Entonces el backend recibe, valida y almacena los datos en la base de datos,
Y retorna código 201 con confirmación de recepción.
Escenario 02: Datos duplicados o ya sincronizados

Dado que el dispositivo envía un lote de datos ya sincronizados previamente,
Cuando el endpoint recibe datos con IDs duplicados,
Entonces el backend responde con código 200 indicando que ya existen,
Y no duplica registros en la base de datos.
Escenario 03: Error de conexión durante sincronización

Dado que el dispositivo está enviando datos y la conexión se interrumpe,
Cuando el endpoint no recibe el lote completo,
Entonces el backend no almacena datos parciales,
Y el dispositivo reintenta el envío completo en la siguiente sincronización.

Story ID

User

Priority

Epic

TS02

Developer

Alta

EP06 - Backend y API

Title

API Gestión de flags y alertas

Description

Como desarrollador, necesito exponer endpoints para que el dispositivo IoT envíe flags generados localmente (edge computing) y para que la web/app consulten alertas activas, permitiendo la supervisión en tiempo real de condiciones críticas.

Acceptance Criteria

Escenario 01: Recepción de flag crítico desde dispositivo

Dado que el dispositivo detecta una condición fuera de rango (ej. temperatura > 8°C),
Cuando envía el flag al endpoint correspondiente,
Entonces el backend registra el flag con timestamp, tipo, valor y ubicación GPS,
Y activa el proceso de notificaciones push a usuarios suscritos al envío.
Escenario 02: Consulta de flags activos por usuario

Dado que un supervisor accede al dashboard,
Cuando consume el endpoint GET /flags?estado=activo,
Entonces el backend retorna la lista de flags activos ordenados por prioridad,
Y solo incluye envíos asociados al usuario (por región o rol).
Escenario 03: Confirmación de flag (override)

Dado que un usuario autorizado confirma un flag desde la app,
Cuando consume el endpoint PUT /flags/{id}/confirmar,
Entonces el backend actualiza el estado del flag a confirmado,
Y registra quién lo confirmó, desde qué dispositivo y en qué momento.


Story ID

User

Priority

Epic

TS03

Developer

Alta

EP06 - Backend y API

Title

API Gestión de usuarios y roles

Description

Como desarrollador, necesito exponer endpoints para gestionar usuarios (crear, modificar, eliminar) y asignar roles (Operador, Supervisor, Administrador, ONG), asegurando autenticación segura y control de acceso basado en permisos.

Acceptance Criteria

Escenario 01: Registro exitoso de usuario

Dado que el administrador o el registro público está disponible,
Cuando el usuario envía email, contraseña válida y datos básicos,
Entonces el backend crea la cuenta en la base de datos y retorna token JWT o código 201,
Y la contraseña se almacena hasheada (bcrypt/argon2).
Escenario 02: Registro con email duplicado o datos inválidos

Dado que el endpoint de registro recibe datos,
Cuando el email ya existe o la contraseña no cumple requisitos,
Entonces el backend responde con código 409 (duplicado) o 400 (validación),
Y retorna un mensaje claro con el error específico.
Escenario 03: Autenticación de usuario (login)

Dado que un usuario tiene una cuenta activa,
Cuando envía sus credenciales al endpoint /auth/login,
Entonces el backend valida credenciales y retorna JWT con expiración configurada,
Y el rol/permisos del usuario quedan codificados en el token.
Escenario 04: Asignación de rol por administrador

Dado que un administrador gestiona usuarios desde el dashboard,
Cuando asigna o modifica el rol de un usuario,
Entonces el backend actualiza permisos del usuario,
Y registra la acción en un log de auditoría.

Story ID

User

Priority

Epic

TS04

Developer

Media

EP06 - Backend y API

Title

API Generación de reportes de trazabilidad

Description

Como desarrollador, necesito exponer endpoints para generar reportes de trazabilidad (por envío, por proyecto o por rango de fechas) en formatos PDF y Excel, para que supervisores y ONGs presenten evidencia a donantes y entes reguladores.

Acceptance Criteria

Escenario 01: Generación de reporte por ID de envío

Dado que un supervisor selecciona un envío completado,
Cuando consume GET /reportes/envio/{id}?formato=pdf,
Entonces el backend genera un PDF con gráficos, mapa de ruta, incidentes y estado final,
Y retorna el archivo para descarga.
Escenario 02: Generación de reporte consolidado por proyecto

Dado que una ONG necesita reportar múltiples envíos de un proyecto,
Cuando consume POST /reportes/proyecto con rango de fechas y lista de envíos,
Entonces el backend genera un consolidado con resumen estadístico y porcentaje de éxito,
Y retorna el archivo en formato PDF o Excel.
Escenario 03: Cache de reportes para evitar regeneración

Dado que el mismo reporte se solicita múltiples veces sin cambios de datos,
Cuando el backend recibe una solicitud idéntica (mismos parámetros),
Entonces retorna el reporte en caché sin regenerar documento,
Y reduce tiempo de respuesta y carga del servidor.





Story ID

User

Priority

Epic

TS05

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar lectura de sensores (temp, humedad, vibración, GPS, apertura)

Description

Como desarrollador embedded, necesito implementar la lectura de todos los sensores del dispositivo CryoGuard, asegurando captura de datos en intervalos configurables y almacenamiento local en tarjeta SD.

Acceptance Criteria

Escenario 01: Lectura periódica de sensores

Dado que el dispositivo está encendido y en modo operación,
Cuando el temporizador alcanza el intervalo configurado (ej. cada 30 segundos),
Entonces el sistema lee todos los sensores,
Y almacena datos en tarjeta SD con timestamp y GPS actual.
Escenario 02: Almacenamiento local en tarjeta SD

Dado que el dispositivo está en zona sin conectividad,
Cuando los sensores capturan datos,
Entonces los datos se escriben en la tarjeta SD en formato CSV o JSON,
Y se mantienen hasta la siguiente sincronización exitosa.
Escenario 03: Detección de fallo de sensor

Dado que un sensor no responde o envía datos inválidos,
Cuando el sistema intenta leerlo,
Entonces registra un error interno y continúa con los sensores restantes,
Y genera un flag de mantenimiento para el supervisor.



Story ID

User

Priority

Epic

TS06

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar reglas de edge computing para generación de flags

Description

Como desarrollador embedded, necesito implementar un motor de reglas que evalúe localmente los datos de sensores y genere flags preventivos o críticos cuando las variables salgan de rango, activando actuadores automáticamente.

Acceptance Criteria

Escenario 01: Evaluación de temperatura fuera de rango

Dado que el rango permitido es 2°C a 8°C,
Cuando el sensor registra 9°C durante más de 30 segundos,
Entonces el sistema genera un flag crítico,
Y activa LED rojo, buzzer y Peltier automáticamente.
Escenario 02: Evaluación de temperatura cercana al límite

Dado que el rango permitido es 2°C a 8°C,
Cuando el sensor registra 7.5°C cercano al límite superior,
Entonces el sistema genera un flag preventivo,
Y activa LED amarillo sin buzzer.
Escenario 03: Evaluación de vibración excesiva

Dado que existe un umbral máximo de vibración configurado,
Cuando el sensor registra un valor superior al umbral,
Entonces el sistema genera un flag de vibración,
Y registra el evento sin activar enfriamiento.
Escenario 04: Evaluación de apertura no autorizada

Dado que el contenedor está cerrado y no hay override activo,
Cuando el sensor de apertura detecta que la caja se abrió,
Entonces el sistema genera un flag crítico de apertura no autorizada,
Y activa LED rojo, buzzer y bloqueo de servo.

Story ID

User

Priority

Epic

TS07

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar control de actuadores (Peltier, Servo, LED, Buzzer)

Description

Como desarrollador embedded, necesito implementar el control de actuadores del dispositivo por reglas automáticas y por comandos manuales (botones físicos o remotos).

Acceptance Criteria

Escenario 01: Activación automática de Peltier por temperatura alta

Dado que se detectó un flag crítico por temperatura alta,
Cuando el sistema evalúa la regla correspondiente,
Entonces activa módulo Peltier, disipador y ventilador,
Y mantiene enfriamiento hasta normalización sostenida por 2 minutos.
Escenario 02: Activación de servo para bloqueo de apertura

Dado que se detectó un flag crítico o apertura no autorizada,
Cuando el sistema activa el bloqueo,
Entonces el servo bloquea físicamente la apertura del contenedor,
Y solo se desbloquea con override autorizado.
Escenario 03: Control de LEDs según estado

Dado que no hay flags activos, cuando se evalúa estado normal, entonces LED verde fijo.
Dado que hay flag preventivo activo, cuando la condición es cercana al límite, entonces LED amarillo fijo.
Dado que hay flag crítico activo, cuando la condición está fuera de rango, entonces LED rojo parpadeante.
Y el patrón visual refleja de forma inmediata la severidad actual.
Escenario 04: Control de buzzer para alertas sonoras

Dado que hay un flag crítico activo,
Cuando se activa la alerta sonora,
Entonces el buzzer emite pitido intermitente y puede silenciarse por botón físico,
Pero el LED rojo continúa parpadeando hasta normalizar la condición.

Story ID

User

Priority

Epic

TS08

Embedded Developer

Media

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar botones físicos para override y control manual

Description

Como desarrollador embedded, necesito implementar lectura de botones físicos (override, silencio, prueba de LEDs, reset) para permitir acciones manuales sin depender de app o web.

Acceptance Criteria

Escenario 01: Override manual mediante botón físico

Dado que el contenedor está bloqueado por un flag crítico,
Cuando el operador autorizado presiona el botón de override por 3 segundos,
Entonces el sistema desbloquea el servo por 30 segundos,
Y registra la acción en el log con timestamp e identificador.
Escenario 02: Silencio de buzzer mediante botón físico

Dado que el buzzer está activo por un flag crítico,
Cuando el operador presiona el botón de silencio,
Entonces el buzzer se desactiva inmediatamente,
Pero el LED rojo continúa parpadeando hasta normalizar la condición.
Escenario 03: Prueba de LEDs y buzzer

Dado que el operador quiere verificar actuadores,
Cuando presiona el botón de prueba,
Entonces el sistema enciende LED rojo, amarillo y verde en secuencia,
Y activa el buzzer por 1 segundo.

Story ID

User

Priority

Epic

TS09

Frontend Developer

Alta

EP08 - Dashboard Web

Title

Implementar dashboard con mapa interactivo de envíos activos

Description

Como desarrollador frontend, necesito implementar un dashboard web con mapa interactivo para visualizar contenedores activos y su estado en tiempo real.

Acceptance Criteria

Escenario 01: Carga inicial del mapa con envíos activos

Dado que un supervisor autenticado accede al dashboard,
Cuando la página se carga,
Entonces el frontend consume GET /envios/activos y renderiza marcadores activos,
Y cada marcador muestra color según estado del envío.
Escenario 02: Actualización en tiempo real de posiciones

Dado que el dashboard está abierto y hay envíos en movimiento,
Cuando el backend recibe nuevos datos de ubicación,
Entonces el frontend actualiza posiciones sin recargar la página,
Mediante WebSockets o polling cada 10 segundos.
Escenario 03: Click en marcador para ver detalles del envío

Dado que el supervisor hace click en un marcador,
Cuando se abre un panel lateral,
Entonces muestra ID, producto, temperatura actual, flags activos y último dato de humedad,
Y permite supervisión operativa contextual por envío.

Story ID

User

Priority

Epic

TS10

Frontend Developer

Alta

EP08 - Dashboard Web

Title

Implementar tabla de logs históricos con filtros y exportación

Description

Como desarrollador frontend, necesito implementar tabla paginada de logs históricos con filtros por fecha/región/producto/estado y exportación a CSV/PDF.

Acceptance Criteria

Escenario 01: Visualización de tabla de logs

Dado que un supervisor accede a la sección de historial,
Cuando la página se carga,
Entonces el frontend consume GET /logs?page=1&limit=50 y renderiza tabla de resultados,
Y muestra columnas de envío, fecha, producto, promedio, incidentes y estado final.
Escenario 02: Filtros combinados

Dado que el supervisor aplica filtros combinados,
Cuando hace click en Aplicar filtros,
Entonces el frontend consume endpoint con parámetros de filtro,
Y actualiza la tabla con resultados filtrados.
Escenario 03: Exportación a CSV/PDF

Dado que el supervisor necesita exportar los logs filtrados,
Cuando hace click en Exportar y selecciona CSV,
Entonces el frontend descarga archivo con los datos de la tabla actual,
Y respeta filtros aplicados en la exportación.

Story ID

User

Priority

Epic

TS11

Frontend Developer

Media

EP08 - Dashboard Web

Title

Implementar panel de administración de usuarios y roles

Description

Como desarrollador frontend, necesito implementar un panel para crear, modificar, eliminar usuarios y asignar roles (Operador, Supervisor, Administrador, ONG).

Acceptance Criteria

Escenario 01: Listado de usuarios con paginación

Dado que un administrador accede al panel de usuarios,
Cuando la página se carga,
Entonces el frontend consume GET /admin/usuarios y renderiza la tabla,
Y muestra nombre, email, rol, fecha y estado.
Escenario 02: Creación de nuevo usuario

Dado que el administrador hace click en Nuevo usuario,
Cuando completa formulario y confirma,
Entonces el frontend envía POST /admin/usuarios,
Y actualiza la tabla con el nuevo registro.
Escenario 03: Edición y eliminación de usuarios

Dado que el administrador selecciona un usuario de la tabla,
Cuando edita rol o deshabilita cuenta,
Entonces el frontend envía PUT o DELETE según corresponda,
Y refresca la tabla reflejando los cambios.

Story ID

User

Priority

Epic

TS12

Mobile Developer

Alta

EP09 - Aplicación Móvil

Title

Implementar autenticación y pantalla de inicio en app móvil

Description

Como desarrollador móvil (iOS/Android), necesito implementar login/registro y pantalla de inicio para que operadores y supervisores accedan y vean sus envíos asignados.

Acceptance Criteria

Escenario 01: Login con email y contraseña

Dado que el usuario tiene una cuenta activa,
Cuando ingresa credenciales y presiona Iniciar sesión,
Entonces la app consume POST /auth/login y almacena JWT de forma segura,
Y navega a la pantalla de inicio.
Escenario 02: Almacenamiento seguro de token

Dado que el usuario cierra y abre nuevamente la app,
Cuando el token aún no ha expirado,
Entonces la app restaura sesión automáticamente,
Y evita solicitar login nuevamente.
Escenario 03: Pantalla de inicio con resumen de envíos

Dado que el usuario está autenticado y tiene envíos asignados,
Cuando la app carga la pantalla de inicio,
Entonces consume GET /envios/asignados y muestra lista de envíos activos,
Y presenta ID, destino, temperatura y estado.

Story ID

User

Priority

Epic

TS13

Mobile Developer

Alta

EP09 - Aplicación Móvil

Title

Implementar recepción de notificaciones push

Description

Como desarrollador móvil, necesito implementar recepción de notificaciones push (FCM/APNs) para alertas en tiempo real por flags críticos de envíos asignados.

Acceptance Criteria

Escenario 01: Registro del dispositivo para notificaciones push

Dado que el usuario inicia sesión por primera vez,
Cuando la app solicita permisos y el usuario los acepta,
Entonces la app obtiene el token push y lo envía al backend,
Y queda asociado al usuario autenticado.
Escenario 02: Recepción de notificación por flag crítico

Dado que el usuario tiene sesión activa o app en segundo plano,
Cuando backend envía notificación por flag crítico asignado,
Entonces la app muestra notificación en el centro del dispositivo,
Y al tocarla abre detalle del envío.
Escenario 03: Contenido de la notificación

Dado que el usuario recibe una notificación push,
Cuando la visualiza,
Entonces el título indica Alerta CryoGuard - Flag Crítico,
Y el cuerpo muestra tipo de flag, valor e ID de envío.


Story ID

User

Priority

Epic

TS14

Mobile Developer

Media

EP09 - Aplicación Móvil

Title

Implementar override remoto desde app móvil

Description

Como desarrollador móvil, necesito implementar override remoto para que supervisores autorizados desbloqueen contenedor a distancia o gestionen alertas cuando lo solicite el operador.

Acceptance Criteria

Escenario 01: Solicitud de override por operador

Dado que el contenedor está bloqueado y el operador necesita abrirlo,
Cuando el operador solicita autorización desde app o por comunicación externa,
Entonces el supervisor recibe una notificación push con la solicitud,
Y puede revisar contexto del envío antes de aprobar.
Escenario 02: Aprobación de override por supervisor

Dado que el supervisor recibe la solicitud de override,
Cuando abre la app, selecciona envío y presiona Autorizar desbloqueo,
Entonces la app consume POST /envios/{id}/override-remoto y backend envía comando al IoT,
Y registra la acción en el log con ID del supervisor.
Escenario 03: Confirmación de override exitoso

Dado que el comando de override fue enviado exitosamente,
Cuando el dispositivo confirma la recepción,
Entonces la app muestra mensaje Contenedor desbloqueado temporalmente,
Y el operador puede abrir la caja.

Las User Stories son clave en metodologías ágiles porque traducen los requisitos funcionales desde la mirada del usuario. Cada historia especifica una necesidad concreta, lo que permite planificar, priorizar y construir el sistema de forma iterativa. Así se asegura que cada función aporte valor real y permanezca alineada con las expectativas del usuario final.



Story ID

User

Priority

Epic

US01

Operador de transporte

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Monitorear temperatura en tiempo real

Description

Como operador de transporte, quiero monitorear la temperatura del contenedor en tiempo real durante todo el trayecto, para asegurar que las vacunas y medicamentos se mantengan dentro del rango permitido.

Acceptance Criteria

Escenario 01: Temperatura dentro del rango permitido

Dado que el sensor de temperatura registra valores dentro del rango configurado,
Cuando el sistema procesa los datos,
Entonces el LED se mantiene en color verde
Y no se activan alertas sonoras.
Escenario 02: Temperatura fuera del rango permitido

Dado que el sensor de temperatura registra valores fuera del rango configurado,
Cuando el sistema detecta la desviación,
Entonces se activa LED rojo y buzzer
Y se genera un flag crítico en el sistema.


Story ID

User

Priority

Epic

US02

Operador de transporte

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Monitorear humedad del contenedor

Description

Como operador de transporte, quiero monitorear los niveles de humedad dentro del contenedor, para evitar que la humedad excesiva dane los productos biomedicos sensibles.

Acceptance Criteria

Escenario 01: Humedad dentro del rango permitido

Dado que el sensor de humedad registra valores dentro del rango configurado (30%-70%),
Cuando el sistema procesa los datos,
Entonces no se genera ninguna alerta relacionada con humedad,
Y el sistema mantiene el estado normal del envio.
Escenario 02: Humedad fuera del rango permitido

Dado que el sensor de humedad registra valores por debajo o por encima del rango,
Cuando el sistema detecta la desviacion,
Entonces se activa un flag preventivo,
Y se envia una notificacion a la app del supervisor.










Story ID

User

Priority

Epic

US03

Operador de transporte

Media

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Detectar vibraciones o impactos

Description

Como operador de transporte, quiero detectar vibraciones o impactos anormales durante el traslado, para identificar si los productos han sufrido golpes que puedan comprometer su integridad.

Acceptance Criteria

Escenario 01: Vibracion dentro del umbral normal

Dado que el sensor de vibracion registra valores dentro del umbral normal,
Cuando el sistema evalua los datos,
Entonces no se genera ningun flag,
Y el envio continua sin incidentes de impacto.
Escenario 02: Vibracion que supera el umbral permitido

Dado que el sensor de vibracion registra un impacto o vibracion excesiva,
Cuando el sistema detecta la anomalia,
Entonces se registra el evento con timestamp,
Y se anade al log de incidentes del envio.

Story ID

User

Priority

Epic

US04

Supervisor de logistica

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Geolocalizar contenedor en tiempo real

Description

Como supervisor de logistica, quiero conocer la ubicacion GPS del contenedor en tiempo real, para monitorear que el transporte siga la ruta planificada.

Acceptance Criteria

Escenario 01: Visualizacion de ubicacion en mapa

Dado que el contenedor tiene conexion a internet,
Cuando el supervisor accede al dashboard web,
Entonces puede ver la ubicacion exacta del contenedor en un mapa interactivo,
Y validar que este dentro de la ruta esperada.
Escenario 02: Actualizacion de ubicacion en intervalos regulares

Dado que el contenedor esta en movimiento,
Cuando el sistema recibe datos GPS,
Entonces actualiza la posicion en el mapa cada 30 segundos o segun intervalo configurado,
Y mantiene trazabilidad continua del trayecto.

Story ID

User

Priority

Epic

US05

Supervisor de logistica

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Activar enfriamiento automatico (Peltier)

Description

Como supervisor de logistica, quiero que el sistema active automaticamente el enfriamiento (Peltier) cuando la temperatura supere el rango permitido, para proteger los productos sin intervencion manual.

Acceptance Criteria

Escenario 01: Activacion automatica de enfriamiento

Dado que la temperatura supera el rango permitido en mas de 1 C,
Cuando el sistema detecta la condicion,
Entonces activa el modulo Peltier, disipador y ventilador automaticamente,
Y protege la integridad de los productos durante el traslado.
Escenario 02: Desactivacion al normalizar temperatura

Dado que el enfriamiento esta activo y la temperatura vuelve al rango permitido,
Cuando se mantiene estable dentro del rango por 2 minutos,
Entonces el sistema desactiva automaticamente el enfriamiento,
Y vuelve al modo de monitoreo normal.

Story ID

User

Priority

Epic

US06

Administrador

Media

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Bloquear apertura del contenedor (Servo)

Description

Como administrador, quiero que el sistema bloquee automaticamente la apertura del contenedor cuando se detecte una condicion critica, para evitar que se acceda a productos en estado vulnerable.

Acceptance Criteria

Escenario 01: Bloqueo automatico por condicion critica

Dado que se detecta un flag critico (temperatura extrema o apertura no autorizada),
Cuando el sistema activa la alerta,
Entonces el servo bloquea fisicamente la apertura del contenedor,
Y se restringe el acceso hasta autorizacion valida.
Escenario 02: Desbloqueo por override autorizado

Dado que el contenedor esta bloqueado por condicion critica,
Cuando un usuario autorizado ejecuta override mediante boton fisico o app,
Entonces el sistema desbloquea el contenedor y registra la accion en el log,
Y deja trazabilidad del evento de desbloqueo.

Story ID

User

Priority

Epic

US07

Operador de transporte

Alta

EP02 - Alertas y Notificaciones

Title

Recibir alerta visual mediante LED

Description

Como operador de transporte, quiero recibir alertas visuales mediante LEDs de colores, para identificar rapidamente el estado del contenedor sin necesidad de mirar una pantalla.

Acceptance Criteria

Escenario 01: LED verde para estado normal

Dado que todas las variables estan dentro del rango permitido,
Cuando el sistema evalua las condiciones,
Entonces el LED se mantiene en color verde fijo,
Y el operador identifica que no hay riesgo actual.
Escenario 02: LED rojo para condicion critica

Dado que al menos una variable critica esta fuera del rango permitido,
Cuando el sistema detecta la condicion,
Entonces el LED parpadea en color rojo,
Y se indica una accion inmediata del operador.

Story ID

User

Priority

Epic

US08

Operador de transporte

Alta

EP02 - Alertas y Notificaciones

Title

Recibir alerta sonora mediante Buzzer

Description

Como operador de transporte, quiero recibir alertas sonoras cuando se detecte una condicion critica, para actuar inmediatamente incluso si no estoy mirando el contenedor.

Acceptance Criteria

Escenario 01: Activacion de buzzer en condicion critica

Dado que se detecta un flag critico,
Cuando el sistema activa las alertas,
Entonces el buzzer emite un sonido intermitente,
Y el operador es advertido incluso sin contacto visual.
Escenario 02: Desactivacion manual del buzzer

Dado que el buzzer esta activo por una condicion critica,
Cuando el operador presiona el boton fisico de silencio o confirma la alerta en app,
Entonces el buzzer se desactiva pero el LED rojo continua parpadeando,
Y la condicion permanece visible hasta normalizarse.

Story ID

User

Priority

Epic

US09

Supervisor de logistica

Alta

EP02 - Alertas y Notificaciones

Title

Detectar salida de geocerca

Description

Como supervisor de logistica, quiero recibir una alerta cuando el contenedor salga de la ruta o geocerca definida, para identificar desviaciones no planificadas.

Acceptance Criteria

Escenario 01: Alerta por salida de geocerca

Dado que se ha definido una geocerca para la ruta del envio,
Cuando el GPS del contenedor detecta que la ubicacion esta fuera de la geocerca,
Entonces el sistema genera un flag preventivo y envia una notificacion al supervisor,
Y se activa seguimiento reforzado del trayecto.
Escenario 02: Registro de desviacion en el log

Dado que se detecto una salida de geocerca,
Cuando el supervisor revisa el historial del envio,
Entonces puede ver el punto exacto de desviacion con timestamp y duracion,
Y usar la evidencia para analisis posterior.

Story ID

User

Priority

Epic

US10

Supervisor de logistica

Alta

EP02 - Alertas y Notificaciones

Title

Detectar apertura no autorizada del contenedor

Description

Como supervisor de logistica, quiero recibir una alerta cuando el contenedor se abra sin autorizacion, para identificar posibles manipulaciones indebidas de los productos.

Acceptance Criteria

Escenario 01: Alerta por apertura no autorizada

Dado que el contenedor esta cerrado y el sistema en modo de transporte,
Cuando se detecta la apertura del contenedor sin un override previo,
Entonces se activa flag critico, LED rojo, buzzer y notificacion remota,
Y se registra un evento de seguridad en el sistema.
Escenario 02: Registro de apertura autorizada

Dado que un usuario autorizado ejecuta override antes de abrir,
Cuando se detecta la apertura del contenedor,
Entonces se registra como apertura autorizada y no se activan alertas,
Y se conserva la trazabilidad de la autorizacion.

Story ID

User

Priority

Epic

US11

Supervisor de logistica

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Visualizar dashboard con mapa de envios activos

Description

Como supervisor de logistica, quiero visualizar un dashboard web con un mapa que muestre todos los envios activos, para supervisar su ubicacion y estado de manera centralizada.

Acceptance Criteria

Escenario 01: Mapa con todos los envios activos

Dado que hay multiples envios en curso,
Cuando el supervisor accede al dashboard,
Entonces ve un mapa con marcadores de cada contenedor activo y su estado (verde/normal, amarillo/preventivo, rojo/critico),
Y puede priorizar acciones sobre los envios criticos.
Escenario 02: Filtros por region y estado

Dado que el supervisor quiere enfocarse en una region especifica,
Cuando aplica filtros por region o estado,
Entonces el mapa muestra solo los envios que cumplen con los criterios seleccionados,
Y mejora la supervision operativa por segmento.

Story ID

User

Priority

Epic

US12

Supervisor de logistica

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Consultar logs historicos de envios

Description

Como supervisor de logistica, quiero consultar los logs historicos de envios anteriores, para analizar incidentes pasados y mejorar rutas futuras.

Acceptance Criteria

Escenario 01: Visualizacion de historial por envio

Dado que el supervisor selecciona un envio completado,
Cuando accede a los detalles del envio,
Entonces puede ver el log completo de temperatura, humedad, vibracion, ubicacion y eventos de apertura,
Y analizar incidentes con contexto completo.
Escenario 02: Exportacion de logs

Dado que el supervisor necesita compartir los logs con un auditor,
Cuando selecciona la opcion de exportacion,
Entonces el sistema genera un archivo CSV o PDF con todos los datos del envio,
Y facilita auditoria y cumplimiento documental.

Story ID

User

Priority

Epic

US13

Operador de transporte

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Recibir notificaciones push en app movil

Description

Como operador de transporte, quiero recibir notificaciones push en mi aplicacion movil cuando se detecte una condicion critica, para actuar rapidamente incluso si no estoy cerca del contenedor.

Acceptance Criteria

Escenario 01: Notificacion push por flag critico

Dado que se detecta un flag critico en el contenedor,
Cuando el sistema procesa el evento,
Entonces envia una notificacion push a la app movil del operador asignado al envio,
Y permite reaccion inmediata ante el incidente.
Escenario 02: Contenido de la notificacion

Dado que el operador recibe la notificacion,
Cuando abre la app,
Entonces puede ver que variable causo el flag, el valor registrado y la accion recomendada,
Y decidir la respuesta operativa adecuada.

Story ID

User

Priority

Epic

US14

Administrador

Media

EP03 - Dashboard Web y Aplicacion Movil

Title

Gestionar usuarios y roles desde dashboard

Description

Como administrador, quiero gestionar usuarios y asignar roles desde el dashboard web, para controlar quien tiene acceso a que funcionalidades del sistema.

Acceptance Criteria

Escenario 01: Crear nuevo usuario

Dado que el administrador esta en el panel de gestion de usuarios,
Cuando ingresa nombre, correo y selecciona un rol (Operador, Supervisor, ONG),
Entonces el sistema crea el usuario y envia un correo con instrucciones de acceso,
Y deja el registro de alta en el sistema.
Escenario 02: Modificar rol de usuario existente

Dado que un usuario cambia de puesto o responsabilidades,
Cuando el administrador modifica su rol,
Entonces el sistema actualiza los permisos del usuario sin afectar sus datos historicos,
Y aplica la nueva politica de acceso de inmediato.

Story ID

User

Priority

Epic

US15

Operador de transporte

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Almacenar datos localmente durante transporte offline

Description

Como operador de transporte, quiero que el sistema almacene todos los datos de sensores localmente cuando no hay conexion a internet, para no perder informacion durante trayectos en zonas sin cobertura.

Acceptance Criteria

Escenario 01: Almacenamiento automatico en tarjeta SD

Dado que el contenedor esta en una zona sin conectividad,
Cuando los sensores capturan datos,
Entonces el sistema guarda toda la informacion en la memoria interna o tarjeta SD,
Y evita perdida de datos durante el trayecto.
Escenario 02: Capacidad de almacenamiento suficiente

Dado que el trayecto dura hasta 7 dias sin conectividad,
Cuando el sistema almacena datos continuamente,
Entonces la tarjeta SD tiene capacidad suficiente para retener todos los datos sin sobrescribir,
Y conserva la trazabilidad completa del envio.

Story ID

User

Priority

Epic

US16

Supervisor de logistica

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Sincronizar datos con la nube automaticamente

Description

Como supervisor de logistica, quiero que los datos almacenados localmente se sincronicen automaticamente con la nube cuando haya conectividad disponible, para tener trazabilidad completa y respaldo de la informacion.

Acceptance Criteria

Escenario 01: Sincronizacion automatica al detectar WiFi

Dado que el contenedor regresa a una zona con conexion a internet,
Cuando el sistema detecta una red WiFi configurada o datos moviles,
Entonces inicia automaticamente la sincronizacion de datos pendientes con la nube,
Y mantiene actualizado el historial centralizado.
Escenario 02: Resumen de sincronizacion

Dado que la sincronizacion ha finalizado,
Cuando el supervisor revisa el dashboard,
Entonces puede ver un resumen de los datos sincronizados y confirmar que no hubo perdida de informacion,
Y valida la integridad del respaldo en nube.

Story ID

User

Priority

Epic

US17

Administrador de ONG

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Generar reporte de trazabilidad para donantes

Description

Como administrador de ONG, quiero generar un reporte de trazabilidad completo de un envio o proyecto, para presentar evidencia a donantes internacionales sobre el uso adecuado de los recursos.

Acceptance Criteria

Escenario 01: Generacion de reporte por envio

Dado que un envio ha sido completado y sincronizado,
Cuando el administrador selecciona el envio y la opcion "Generar reporte",
Entonces el sistema crea un documento PDF con graficos de temperatura, mapa de ruta, incidentes y estado final,
Y deja evidencia formal para auditoria de donantes.
Escenario 02: Reporte consolidado por proyecto

Dado que un proyecto incluye multiples envios,
Cuando el administrador selecciona el proyecto y un rango de fechas,
Entonces el sistema genera un reporte consolidado con resumen de todos los envios y estadisticas agregadas,
Y facilita la rendicion de cuentas del proyecto.

Story ID

User

Priority

Epic

US18

Administrador

Media

EP05 - Gestion de Usuarios y Roles

Title

Ejecutar override manual mediante boton fisico

Description

Como operador autorizado, quiero ejecutar override manual mediante botones fisicos en el contenedor, para desbloquear la caja o silenciar alertas en situaciones de emergencia cuando no tengo acceso a la app.

Acceptance Criteria

Escenario 01: Desbloqueo por override fisico

Dado que el contenedor esta bloqueado por una condicion critica,
Cuando el operador autorizado presiona el boton de override y ingresa su codigo,
Entonces el servo desbloquea el contenedor y se registra la accion con el ID del operador,
Y queda constancia para seguimiento posterior.
Escenario 02: Silenciar buzzer mediante boton fisico

Dado que el buzzer esta activo por una alerta,
Cuando el operador presiona el boton de silencio,
Entonces el buzzer se desactiva pero el LED rojo continua parpadeando,
Y la alerta visual se mantiene hasta resolver la condicion.

Story ID

User

Priority

Epic

US19

Supervisor de logistica

Media

EP05 - Gestion de Usuarios y Roles

Title

Ejecutar override remoto desde app o web

Description

Como supervisor de logistica, quiero ejecutar override remoto desde la app o web, para autorizar el desbloqueo del contenedor a distancia sin necesidad de que el operador este fisicamente presente.

Acceptance Criteria

Escenario 01: Override remoto por supervisor

Dado que el operador solicita autorizacion para abrir el contenedor en una zona remota,
Cuando el supervisor recibe la solicitud en la app y la aprueba,
Entonces el sistema envia una senal al contenedor para desbloquear el servo temporalmente,
Y habilita la apertura bajo control remoto autorizado.
Escenario 02: Registro de override remoto

Dado que se ejecuto un override remoto,
Cuando se revisa el log del envio,
Entonces se registra quien autorizo, desde que dispositivo, en que momento y la duracion del desbloqueo,
Y se conserva trazabilidad completa de la aprobacion.

Story ID

User

Priority

Epic

US20

Administrador

Alta

EP05 - Gestion de Usuarios y Roles

Title

Configurar rangos permitidos por tipo de producto

Description

Como administrador, quiero configurar los rangos permitidos de temperatura, humedad y vibracion segun el tipo de producto (vacunas, insulina, sangre), para que el sistema evalue correctamente las condiciones segun los requerimientos especificos de cada producto.

Acceptance Criteria

Escenario 01: Seleccion de producto predefinido

Dado que el administrador esta configurando un nuevo envio,
Cuando selecciona un tipo de producto de la lista predefinida (ej. Vacuna Pfizer, Insulina),
Entonces el sistema carga automaticamente los rangos permitidos recomendados por el fabricante,
Y aplica los parametros al envio configurado.
Escenario 02: Configuracion manual de rangos personalizados

Dado que el producto no esta en la lista predefinida,
Cuando el administrador ingresa valores manuales para temperatura minima, maxima y otros parametros,
Entonces el sistema guarda la configuracion y la aplica a todos los envios de ese producto,
Y conserva la plantilla para futuras operaciones.


Technical Stories

En esta sección se describen las historias técnicas que desarrollamos para implementar las funcionalidades clave. Cada historia define tareas específicas que el equipo de desarrollo debe realizar, como crear endpoints, supervisar el sistema, generar reportes, entre otros.


Story ID

User

Priority

Epic

TS01

Developer

Alta

EP06 - Backend y API

Title

API Sincronización de datos desde dispositivo IoT

Description

Como desarrollador, necesito exponer un endpoint para que el dispositivo CryoGuard sincronice los datos almacenados localmente (temperatura, humedad, vibración, GPS, eventos de apertura) con la nube cuando haya conectividad disponible, asegurando la integridad y trazabilidad de la información.

Acceptance Criteria

Escenario 01: Sincronización exitosa de datos

Dado que el dispositivo tiene datos pendientes en su almacenamiento local (tarjeta SD),
Cuando detecta conexión a internet y envía los datos al endpoint de sincronización,
Entonces el backend recibe, valida y almacena los datos en la base de datos,
Y retorna código 201 con confirmación de recepción.
Escenario 02: Datos duplicados o ya sincronizados

Dado que el dispositivo envía un lote de datos ya sincronizados previamente,
Cuando el endpoint recibe datos con IDs duplicados,
Entonces el backend responde con código 200 indicando que ya existen,
Y no duplica registros en la base de datos.
Escenario 03: Error de conexión durante sincronización

Dado que el dispositivo está enviando datos y la conexión se interrumpe,
Cuando el endpoint no recibe el lote completo,
Entonces el backend no almacena datos parciales,
Y el dispositivo reintenta el envío completo en la siguiente sincronización.

Story ID

User

Priority

Epic

TS02

Developer

Alta

EP06 - Backend y API

Title

API Gestión de flags y alertas

Description

Como desarrollador, necesito exponer endpoints para que el dispositivo IoT envíe flags generados localmente (edge computing) y para que la web/app consulten alertas activas, permitiendo la supervisión en tiempo real de condiciones críticas.

Acceptance Criteria

Escenario 01: Recepción de flag crítico desde dispositivo

Dado que el dispositivo detecta una condición fuera de rango (ej. temperatura > 8°C),
Cuando envía el flag al endpoint correspondiente,
Entonces el backend registra el flag con timestamp, tipo, valor y ubicación GPS,
Y activa el proceso de notificaciones push a usuarios suscritos al envío.
Escenario 02: Consulta de flags activos por usuario

Dado que un supervisor accede al dashboard,
Cuando consume el endpoint GET /flags?estado=activo,
Entonces el backend retorna la lista de flags activos ordenados por prioridad,
Y solo incluye envíos asociados al usuario (por región o rol).
Escenario 03: Confirmación de flag (override)

Dado que un usuario autorizado confirma un flag desde la app,
Cuando consume el endpoint PUT /flags/{id}/confirmar,
Entonces el backend actualiza el estado del flag a confirmado,
Y registra quién lo confirmó, desde qué dispositivo y en qué momento.


Story ID

User

Priority

Epic

TS03

Developer

Alta

EP06 - Backend y API

Title

API Gestión de usuarios y roles

Description

Como desarrollador, necesito exponer endpoints para gestionar usuarios (crear, modificar, eliminar) y asignar roles (Operador, Supervisor, Administrador, ONG), asegurando autenticación segura y control de acceso basado en permisos.

Acceptance Criteria

Escenario 01: Registro exitoso de usuario

Dado que el administrador o el registro público está disponible,
Cuando el usuario envía email, contraseña válida y datos básicos,
Entonces el backend crea la cuenta en la base de datos y retorna token JWT o código 201,
Y la contraseña se almacena hasheada (bcrypt/argon2).
Escenario 02: Registro con email duplicado o datos inválidos

Dado que el endpoint de registro recibe datos,
Cuando el email ya existe o la contraseña no cumple requisitos,
Entonces el backend responde con código 409 (duplicado) o 400 (validación),
Y retorna un mensaje claro con el error específico.
Escenario 03: Autenticación de usuario (login)

Dado que un usuario tiene una cuenta activa,
Cuando envía sus credenciales al endpoint /auth/login,
Entonces el backend valida credenciales y retorna JWT con expiración configurada,
Y el rol/permisos del usuario quedan codificados en el token.
Escenario 04: Asignación de rol por administrador

Dado que un administrador gestiona usuarios desde el dashboard,
Cuando asigna o modifica el rol de un usuario,
Entonces el backend actualiza permisos del usuario,
Y registra la acción en un log de auditoría.

Story ID

User

Priority

Epic

TS04

Developer

Media

EP06 - Backend y API

Title

API Generación de reportes de trazabilidad

Description

Como desarrollador, necesito exponer endpoints para generar reportes de trazabilidad (por envío, por proyecto o por rango de fechas) en formatos PDF y Excel, para que supervisores y ONGs presenten evidencia a donantes y entes reguladores.

Acceptance Criteria

Escenario 01: Generación de reporte por ID de envío

Dado que un supervisor selecciona un envío completado,
Cuando consume GET /reportes/envio/{id}?formato=pdf,
Entonces el backend genera un PDF con gráficos, mapa de ruta, incidentes y estado final,
Y retorna el archivo para descarga.
Escenario 02: Generación de reporte consolidado por proyecto

Dado que una ONG necesita reportar múltiples envíos de un proyecto,
Cuando consume POST /reportes/proyecto con rango de fechas y lista de envíos,
Entonces el backend genera un consolidado con resumen estadístico y porcentaje de éxito,
Y retorna el archivo en formato PDF o Excel.
Escenario 03: Cache de reportes para evitar regeneración

Dado que el mismo reporte se solicita múltiples veces sin cambios de datos,
Cuando el backend recibe una solicitud idéntica (mismos parámetros),
Entonces retorna el reporte en caché sin regenerar documento,
Y reduce tiempo de respuesta y carga del servidor.





Story ID

User

Priority

Epic

TS05

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar lectura de sensores (temp, humedad, vibración, GPS, apertura)

Description

Como desarrollador embedded, necesito implementar la lectura de todos los sensores del dispositivo CryoGuard, asegurando captura de datos en intervalos configurables y almacenamiento local en tarjeta SD.

Acceptance Criteria

Escenario 01: Lectura periódica de sensores

Dado que el dispositivo está encendido y en modo operación,
Cuando el temporizador alcanza el intervalo configurado (ej. cada 30 segundos),
Entonces el sistema lee todos los sensores,
Y almacena datos en tarjeta SD con timestamp y GPS actual.
Escenario 02: Almacenamiento local en tarjeta SD

Dado que el dispositivo está en zona sin conectividad,
Cuando los sensores capturan datos,
Entonces los datos se escriben en la tarjeta SD en formato CSV o JSON,
Y se mantienen hasta la siguiente sincronización exitosa.
Escenario 03: Detección de fallo de sensor

Dado que un sensor no responde o envía datos inválidos,
Cuando el sistema intenta leerlo,
Entonces registra un error interno y continúa con los sensores restantes,
Y genera un flag de mantenimiento para el supervisor.



Story ID

User

Priority

Epic

TS06

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar reglas de edge computing para generación de flags

Description

Como desarrollador embedded, necesito implementar un motor de reglas que evalúe localmente los datos de sensores y genere flags preventivos o críticos cuando las variables salgan de rango, activando actuadores automáticamente.

Acceptance Criteria

Escenario 01: Evaluación de temperatura fuera de rango

Dado que el rango permitido es 2°C a 8°C,
Cuando el sensor registra 9°C durante más de 30 segundos,
Entonces el sistema genera un flag crítico,
Y activa LED rojo, buzzer y Peltier automáticamente.
Escenario 02: Evaluación de temperatura cercana al límite

Dado que el rango permitido es 2°C a 8°C,
Cuando el sensor registra 7.5°C cercano al límite superior,
Entonces el sistema genera un flag preventivo,
Y activa LED amarillo sin buzzer.
Escenario 03: Evaluación de vibración excesiva

Dado que existe un umbral máximo de vibración configurado,
Cuando el sensor registra un valor superior al umbral,
Entonces el sistema genera un flag de vibración,
Y registra el evento sin activar enfriamiento.
Escenario 04: Evaluación de apertura no autorizada

Dado que el contenedor está cerrado y no hay override activo,
Cuando el sensor de apertura detecta que la caja se abrió,
Entonces el sistema genera un flag crítico de apertura no autorizada,
Y activa LED rojo, buzzer y bloqueo de servo.

Story ID

User

Priority

Epic

TS07

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar control de actuadores (Peltier, Servo, LED, Buzzer)

Description

Como desarrollador embedded, necesito implementar el control de actuadores del dispositivo por reglas automáticas y por comandos manuales (botones físicos o remotos).

Acceptance Criteria

Escenario 01: Activación automática de Peltier por temperatura alta

Dado que se detectó un flag crítico por temperatura alta,
Cuando el sistema evalúa la regla correspondiente,
Entonces activa módulo Peltier, disipador y ventilador,
Y mantiene enfriamiento hasta normalización sostenida por 2 minutos.
Escenario 02: Activación de servo para bloqueo de apertura

Dado que se detectó un flag crítico o apertura no autorizada,
Cuando el sistema activa el bloqueo,
Entonces el servo bloquea físicamente la apertura del contenedor,
Y solo se desbloquea con override autorizado.
Escenario 03: Control de LEDs según estado

Dado que no hay flags activos, cuando se evalúa estado normal, entonces LED verde fijo.
Dado que hay flag preventivo activo, cuando la condición es cercana al límite, entonces LED amarillo fijo.
Dado que hay flag crítico activo, cuando la condición está fuera de rango, entonces LED rojo parpadeante.
Y el patrón visual refleja de forma inmediata la severidad actual.
Escenario 04: Control de buzzer para alertas sonoras

Dado que hay un flag crítico activo,
Cuando se activa la alerta sonora,
Entonces el buzzer emite pitido intermitente y puede silenciarse por botón físico,
Pero el LED rojo continúa parpadeando hasta normalizar la condición.

Story ID

User

Priority

Epic

TS08

Embedded Developer

Media

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar botones físicos para override y control manual

Description

Como desarrollador embedded, necesito implementar lectura de botones físicos (override, silencio, prueba de LEDs, reset) para permitir acciones manuales sin depender de app o web.

Acceptance Criteria

Escenario 01: Override manual mediante botón físico

Dado que el contenedor está bloqueado por un flag crítico,
Cuando el operador autorizado presiona el botón de override por 3 segundos,
Entonces el sistema desbloquea el servo por 30 segundos,
Y registra la acción en el log con timestamp e identificador.
Escenario 02: Silencio de buzzer mediante botón físico

Dado que el buzzer está activo por un flag crítico,
Cuando el operador presiona el botón de silencio,
Entonces el buzzer se desactiva inmediatamente,
Pero el LED rojo continúa parpadeando hasta normalizar la condición.
Escenario 03: Prueba de LEDs y buzzer

Dado que el operador quiere verificar actuadores,
Cuando presiona el botón de prueba,
Entonces el sistema enciende LED rojo, amarillo y verde en secuencia,
Y activa el buzzer por 1 segundo.

Story ID

User

Priority

Epic

TS09

Frontend Developer

Alta

EP08 - Dashboard Web

Title

Implementar dashboard con mapa interactivo de envíos activos

Description

Como desarrollador frontend, necesito implementar un dashboard web con mapa interactivo para visualizar contenedores activos y su estado en tiempo real.

Acceptance Criteria

Escenario 01: Carga inicial del mapa con envíos activos

Dado que un supervisor autenticado accede al dashboard,
Cuando la página se carga,
Entonces el frontend consume GET /envios/activos y renderiza marcadores activos,
Y cada marcador muestra color según estado del envío.
Escenario 02: Actualización en tiempo real de posiciones

Dado que el dashboard está abierto y hay envíos en movimiento,
Cuando el backend recibe nuevos datos de ubicación,
Entonces el frontend actualiza posiciones sin recargar la página,
Mediante WebSockets o polling cada 10 segundos.
Escenario 03: Click en marcador para ver detalles del envío

Dado que el supervisor hace click en un marcador,
Cuando se abre un panel lateral,
Entonces muestra ID, producto, temperatura actual, flags activos y último dato de humedad,
Y permite supervisión operativa contextual por envío.

Story ID

User

Priority

Epic

TS10

Frontend Developer

Alta

EP08 - Dashboard Web

Title

Implementar tabla de logs históricos con filtros y exportación

Description

Como desarrollador frontend, necesito implementar tabla paginada de logs históricos con filtros por fecha/región/producto/estado y exportación a CSV/PDF.

Acceptance Criteria

Escenario 01: Visualización de tabla de logs

Dado que un supervisor accede a la sección de historial,
Cuando la página se carga,
Entonces el frontend consume GET /logs?page=1&limit=50 y renderiza tabla de resultados,
Y muestra columnas de envío, fecha, producto, promedio, incidentes y estado final.
Escenario 02: Filtros combinados

Dado que el supervisor aplica filtros combinados,
Cuando hace click en Aplicar filtros,
Entonces el frontend consume endpoint con parámetros de filtro,
Y actualiza la tabla con resultados filtrados.
Escenario 03: Exportación a CSV/PDF

Dado que el supervisor necesita exportar los logs filtrados,
Cuando hace click en Exportar y selecciona CSV,
Entonces el frontend descarga archivo con los datos de la tabla actual,
Y respeta filtros aplicados en la exportación.

Story ID

User

Priority

Epic

TS11

Frontend Developer

Media

EP08 - Dashboard Web

Title

Implementar panel de administración de usuarios y roles

Description

Como desarrollador frontend, necesito implementar un panel para crear, modificar, eliminar usuarios y asignar roles (Operador, Supervisor, Administrador, ONG).

Acceptance Criteria

Escenario 01: Listado de usuarios con paginación

Dado que un administrador accede al panel de usuarios,
Cuando la página se carga,
Entonces el frontend consume GET /admin/usuarios y renderiza la tabla,
Y muestra nombre, email, rol, fecha y estado.
Escenario 02: Creación de nuevo usuario

Dado que el administrador hace click en Nuevo usuario,
Cuando completa formulario y confirma,
Entonces el frontend envía POST /admin/usuarios,
Y actualiza la tabla con el nuevo registro.
Escenario 03: Edición y eliminación de usuarios

Dado que el administrador selecciona un usuario de la tabla,
Cuando edita rol o deshabilita cuenta,
Entonces el frontend envía PUT o DELETE según corresponda,
Y refresca la tabla reflejando los cambios.

Story ID

User

Priority

Epic

TS12

Mobile Developer

Alta

EP09 - Aplicación Móvil

Title

Implementar autenticación y pantalla de inicio en app móvil

Description

Como desarrollador móvil (iOS/Android), necesito implementar login/registro y pantalla de inicio para que operadores y supervisores accedan y vean sus envíos asignados.

Acceptance Criteria

Escenario 01: Login con email y contraseña

Dado que el usuario tiene una cuenta activa,
Cuando ingresa credenciales y presiona Iniciar sesión,
Entonces la app consume POST /auth/login y almacena JWT de forma segura,
Y navega a la pantalla de inicio.
Escenario 02: Almacenamiento seguro de token

Dado que el usuario cierra y abre nuevamente la app,
Cuando el token aún no ha expirado,
Entonces la app restaura sesión automáticamente,
Y evita solicitar login nuevamente.
Escenario 03: Pantalla de inicio con resumen de envíos

Dado que el usuario está autenticado y tiene envíos asignados,
Cuando la app carga la pantalla de inicio,
Entonces consume GET /envios/asignados y muestra lista de envíos activos,
Y presenta ID, destino, temperatura y estado.

Story ID

User

Priority

Epic

TS13

Mobile Developer

Alta

EP09 - Aplicación Móvil

Title

Implementar recepción de notificaciones push

Description

Como desarrollador móvil, necesito implementar recepción de notificaciones push (FCM/APNs) para alertas en tiempo real por flags críticos de envíos asignados.

Acceptance Criteria

Escenario 01: Registro del dispositivo para notificaciones push

Dado que el usuario inicia sesión por primera vez,
Cuando la app solicita permisos y el usuario los acepta,
Entonces la app obtiene el token push y lo envía al backend,
Y queda asociado al usuario autenticado.
Escenario 02: Recepción de notificación por flag crítico

Dado que el usuario tiene sesión activa o app en segundo plano,
Cuando backend envía notificación por flag crítico asignado,
Entonces la app muestra notificación en el centro del dispositivo,
Y al tocarla abre detalle del envío.
Escenario 03: Contenido de la notificación

Dado que el usuario recibe una notificación push,
Cuando la visualiza,
Entonces el título indica Alerta CryoGuard - Flag Crítico,
Y el cuerpo muestra tipo de flag, valor e ID de envío.


Story ID

User

Priority

Epic

TS14

Mobile Developer

Media

EP09 - Aplicación Móvil

Title

Implementar override remoto desde app móvil

Description

Como desarrollador móvil, necesito implementar override remoto para que supervisores autorizados desbloqueen contenedor a distancia o gestionen alertas cuando lo solicite el operador.

Acceptance Criteria

Escenario 01: Solicitud de override por operador

Dado que el contenedor está bloqueado y el operador necesita abrirlo,
Cuando el operador solicita autorización desde app o por comunicación externa,
Entonces el supervisor recibe una notificación push con la solicitud,
Y puede revisar contexto del envío antes de aprobar.
Escenario 02: Aprobación de override por supervisor

Dado que el supervisor recibe la solicitud de override,
Cuando abre la app, selecciona envío y presiona Autorizar desbloqueo,
Entonces la app consume POST /envios/{id}/override-remoto y backend envía comando al IoT,
Y registra la acción en el log con ID del supervisor.
Escenario 03: Confirmación de override exitoso

Dado que el comando de override fue enviado exitosamente,
Cuando el dispositivo confirma la recepción,
Entonces la app muestra mensaje Contenedor desbloqueado temporalmente,
Y el operador puede abrir la caja.


Las User Stories son clave en metodologías ágiles porque traducen los requisitos funcionales desde la mirada del usuario. Cada historia especifica una necesidad concreta, lo que permite planificar, priorizar y construir el sistema de forma iterativa. Así se asegura que cada función aporte valor real y permanezca alineada con las expectativas del usuario final.



Story ID

User

Priority

Epic

US01

Operador de transporte

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Monitorear temperatura en tiempo real

Description

Como operador de transporte, quiero monitorear la temperatura del contenedor en tiempo real durante todo el trayecto, para asegurar que las vacunas y medicamentos se mantengan dentro del rango permitido.

Acceptance Criteria

Escenario 01: Temperatura dentro del rango permitido

Dado que el sensor de temperatura registra valores dentro del rango configurado,
Cuando el sistema procesa los datos,
Entonces el LED se mantiene en color verde
Y no se activan alertas sonoras.
Escenario 02: Temperatura fuera del rango permitido

Dado que el sensor de temperatura registra valores fuera del rango configurado,
Cuando el sistema detecta la desviación,
Entonces se activa LED rojo y buzzer
Y se genera un flag crítico en el sistema.


Story ID

User

Priority

Epic

US02

Operador de transporte

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Monitorear humedad del contenedor

Description

Como operador de transporte, quiero monitorear los niveles de humedad dentro del contenedor, para evitar que la humedad excesiva dane los productos biomedicos sensibles.

Acceptance Criteria

Escenario 01: Humedad dentro del rango permitido

Dado que el sensor de humedad registra valores dentro del rango configurado (30%-70%),
Cuando el sistema procesa los datos,
Entonces no se genera ninguna alerta relacionada con humedad,
Y el sistema mantiene el estado normal del envio.
Escenario 02: Humedad fuera del rango permitido

Dado que el sensor de humedad registra valores por debajo o por encima del rango,
Cuando el sistema detecta la desviacion,
Entonces se activa un flag preventivo,
Y se envia una notificacion a la app del supervisor.










Story ID

User

Priority

Epic

US03

Operador de transporte

Media

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Detectar vibraciones o impactos

Description

Como operador de transporte, quiero detectar vibraciones o impactos anormales durante el traslado, para identificar si los productos han sufrido golpes que puedan comprometer su integridad.

Acceptance Criteria

Escenario 01: Vibracion dentro del umbral normal

Dado que el sensor de vibracion registra valores dentro del umbral normal,
Cuando el sistema evalua los datos,
Entonces no se genera ningun flag,
Y el envio continua sin incidentes de impacto.
Escenario 02: Vibracion que supera el umbral permitido

Dado que el sensor de vibracion registra un impacto o vibracion excesiva,
Cuando el sistema detecta la anomalia,
Entonces se registra el evento con timestamp,
Y se anade al log de incidentes del envio.

Story ID

User

Priority

Epic

US04

Supervisor de logistica

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Geolocalizar contenedor en tiempo real

Description

Como supervisor de logistica, quiero conocer la ubicacion GPS del contenedor en tiempo real, para monitorear que el transporte siga la ruta planificada.

Acceptance Criteria

Escenario 01: Visualizacion de ubicacion en mapa

Dado que el contenedor tiene conexion a internet,
Cuando el supervisor accede al dashboard web,
Entonces puede ver la ubicacion exacta del contenedor en un mapa interactivo,
Y validar que este dentro de la ruta esperada.
Escenario 02: Actualizacion de ubicacion en intervalos regulares

Dado que el contenedor esta en movimiento,
Cuando el sistema recibe datos GPS,
Entonces actualiza la posicion en el mapa cada 30 segundos o segun intervalo configurado,
Y mantiene trazabilidad continua del trayecto.

Story ID

User

Priority

Epic

US05

Supervisor de logistica

Alta

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Activar enfriamiento automatico (Peltier)

Description

Como supervisor de logistica, quiero que el sistema active automaticamente el enfriamiento (Peltier) cuando la temperatura supere el rango permitido, para proteger los productos sin intervencion manual.

Acceptance Criteria

Escenario 01: Activacion automatica de enfriamiento

Dado que la temperatura supera el rango permitido en mas de 1 C,
Cuando el sistema detecta la condicion,
Entonces activa el modulo Peltier, disipador y ventilador automaticamente,
Y protege la integridad de los productos durante el traslado.
Escenario 02: Desactivacion al normalizar temperatura

Dado que el enfriamiento esta activo y la temperatura vuelve al rango permitido,
Cuando se mantiene estable dentro del rango por 2 minutos,
Entonces el sistema desactiva automaticamente el enfriamiento,
Y vuelve al modo de monitoreo normal.

Story ID

User

Priority

Epic

US06

Administrador

Media

EP01 - Monitoreo y Control del Contenedor Inteligente

Title

Bloquear apertura del contenedor (Servo)

Description

Como administrador, quiero que el sistema bloquee automaticamente la apertura del contenedor cuando se detecte una condicion critica, para evitar que se acceda a productos en estado vulnerable.

Acceptance Criteria

Escenario 01: Bloqueo automatico por condicion critica

Dado que se detecta un flag critico (temperatura extrema o apertura no autorizada),
Cuando el sistema activa la alerta,
Entonces el servo bloquea fisicamente la apertura del contenedor,
Y se restringe el acceso hasta autorizacion valida.
Escenario 02: Desbloqueo por override autorizado

Dado que el contenedor esta bloqueado por condicion critica,
Cuando un usuario autorizado ejecuta override mediante boton fisico o app,
Entonces el sistema desbloquea el contenedor y registra la accion en el log,
Y deja trazabilidad del evento de desbloqueo.

Story ID

User

Priority

Epic

US07

Operador de transporte

Alta

EP02 - Alertas y Notificaciones

Title

Recibir alerta visual mediante LED

Description

Como operador de transporte, quiero recibir alertas visuales mediante LEDs de colores, para identificar rapidamente el estado del contenedor sin necesidad de mirar una pantalla.

Acceptance Criteria

Escenario 01: LED verde para estado normal

Dado que todas las variables estan dentro del rango permitido,
Cuando el sistema evalua las condiciones,
Entonces el LED se mantiene en color verde fijo,
Y el operador identifica que no hay riesgo actual.
Escenario 02: LED rojo para condicion critica

Dado que al menos una variable critica esta fuera del rango permitido,
Cuando el sistema detecta la condicion,
Entonces el LED parpadea en color rojo,
Y se indica una accion inmediata del operador.

Story ID

User

Priority

Epic

US08

Operador de transporte

Alta

EP02 - Alertas y Notificaciones

Title

Recibir alerta sonora mediante Buzzer

Description

Como operador de transporte, quiero recibir alertas sonoras cuando se detecte una condicion critica, para actuar inmediatamente incluso si no estoy mirando el contenedor.

Acceptance Criteria

Escenario 01: Activacion de buzzer en condición crítica

Dado que se detecta un flag crítico,
Cuando el sistema activa las alertas,
Entonces el buzzer emite un sonido intermitente,
Y el operador es advertido incluso sin contacto visual.
Escenario 02: Desactivación manual del buzzer

Dado que el buzzer esta activo por una condición crítica,
Cuando el operador presiona el botón físico de silencio o confirma la alerta en app,
Entonces el buzzer se desactiva pero el LED rojo continúa parpadeando,
Y la condición permanece visible hasta normalizarse.

Story ID

User

Priority

Epic

US09

Supervisor de logistica

Alta

EP02 - Alertas y Notificaciones

Title

Detectar salida de geocerca

Description

Como supervisor de logistica, quiero recibir una alerta cuando el contenedor salga de la ruta o geocerca definida, para identificar desviaciones no planificadas.

Acceptance Criteria

Escenario 01: Alerta por salida de geocerca

Dado que se ha definido una geocerca para la ruta del envio,
Cuando el GPS del contenedor detecta que la ubicacion esta fuera de la geocerca,
Entonces el sistema genera un flag preventivo y envia una notificacion al supervisor,
Y se activa seguimiento reforzado del trayecto.
Escenario 02: Registro de desviacion en el log

Dado que se detecto una salida de geocerca,
Cuando el supervisor revisa el historial del envio,
Entonces puede ver el punto exacto de desviacion con timestamp y duracion,
Y usar la evidencia para analisis posterior.

Story ID

User

Priority

Epic

US10

Supervisor de logistica

Alta

EP02 - Alertas y Notificaciones

Title

Detectar apertura no autorizada del contenedor

Description

Como supervisor de logistica, quiero recibir una alerta cuando el contenedor se abra sin autorizacion, para identificar posibles manipulaciones indebidas de los productos.

Acceptance Criteria

Escenario 01: Alerta por apertura no autorizada

Dado que el contenedor esta cerrado y el sistema en modo de transporte,
Cuando se detecta la apertura del contenedor sin un override previo,
Entonces se activa flag critico, LED rojo, buzzer y notificacion remota,
Y se registra un evento de seguridad en el sistema.
Escenario 02: Registro de apertura autorizada

Dado que un usuario autorizado ejecuta override antes de abrir,
Cuando se detecta la apertura del contenedor,
Entonces se registra como apertura autorizada y no se activan alertas,
Y se conserva la trazabilidad de la autorizacion.

Story ID

User

Priority

Epic

US11

Supervisor de logistica

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Visualizar dashboard con mapa de envios activos

Description

Como supervisor de logistica, quiero visualizar un dashboard web con un mapa que muestre todos los envios activos, para supervisar su ubicacion y estado de manera centralizada.

Acceptance Criteria

Escenario 01: Mapa con todos los envios activos

Dado que hay multiples envios en curso,
Cuando el supervisor accede al dashboard,
Entonces ve un mapa con marcadores de cada contenedor activo y su estado (verde/normal, amarillo/preventivo, rojo/critico),
Y puede priorizar acciones sobre los envios criticos.
Escenario 02: Filtros por region y estado

Dado que el supervisor quiere enfocarse en una region especifica,
Cuando aplica filtros por region o estado,
Entonces el mapa muestra solo los envios que cumplen con los criterios seleccionados,
Y mejora la supervision operativa por segmento.

Story ID

User

Priority

Epic

US12

Supervisor de logistica

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Consultar logs historicos de envios

Description

Como supervisor de logistica, quiero consultar los logs historicos de envios anteriores, para analizar incidentes pasados y mejorar rutas futuras.

Acceptance Criteria

Escenario 01: Visualizacion de historial por envio

Dado que el supervisor selecciona un envio completado,
Cuando accede a los detalles del envio,
Entonces puede ver el log completo de temperatura, humedad, vibracion, ubicacion y eventos de apertura,
Y analizar incidentes con contexto completo.
Escenario 02: Exportacion de logs

Dado que el supervisor necesita compartir los logs con un auditor,
Cuando selecciona la opcion de exportacion,
Entonces el sistema genera un archivo CSV o PDF con todos los datos del envio,
Y facilita auditoria y cumplimiento documental.

Story ID

User

Priority

Epic

US13

Operador de transporte

Alta

EP03 - Dashboard Web y Aplicacion Movil

Title

Recibir notificaciones push en app movil

Description

Como operador de transporte, quiero recibir notificaciones push en mi aplicacion movil cuando se detecte una condicion critica, para actuar rapidamente incluso si no estoy cerca del contenedor.

Acceptance Criteria

Escenario 01: Notificacion push por flag critico

Dado que se detecta un flag critico en el contenedor,
Cuando el sistema procesa el evento,
Entonces envia una notificacion push a la app movil del operador asignado al envio,
Y permite reaccion inmediata ante el incidente.
Escenario 02: Contenido de la notificacion

Dado que el operador recibe la notificacion,
Cuando abre la app,
Entonces puede ver que variable causo el flag, el valor registrado y la accion recomendada,
Y decidir la respuesta operativa adecuada.

Story ID

User

Priority

Epic

US14

Administrador

Media

EP03 - Dashboard Web y Aplicacion Movil

Title

Gestionar usuarios y roles desde dashboard

Description

Como administrador, quiero gestionar usuarios y asignar roles desde el dashboard web, para controlar quien tiene acceso a que funcionalidades del sistema.

Acceptance Criteria

Escenario 01: Crear nuevo usuario

Dado que el administrador esta en el panel de gestion de usuarios,
Cuando ingresa nombre, correo y selecciona un rol (Operador, Supervisor, ONG),
Entonces el sistema crea el usuario y envia un correo con instrucciones de acceso,
Y deja el registro de alta en el sistema.
Escenario 02: Modificar rol de usuario existente

Dado que un usuario cambia de puesto o responsabilidades,
Cuando el administrador modifica su rol,
Entonces el sistema actualiza los permisos del usuario sin afectar sus datos historicos,
Y aplica la nueva politica de acceso de inmediato.

Story ID

User

Priority

Epic

US15

Operador de transporte

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Almacenar datos localmente durante transporte offline

Description

Como operador de transporte, quiero que el sistema almacene todos los datos de sensores localmente cuando no hay conexion a internet, para no perder informacion durante trayectos en zonas sin cobertura.

Acceptance Criteria

Escenario 01: Almacenamiento automatico en tarjeta SD

Dado que el contenedor esta en una zona sin conectividad,
Cuando los sensores capturan datos,
Entonces el sistema guarda toda la informacion en la memoria interna o tarjeta SD,
Y evita perdida de datos durante el trayecto.
Escenario 02: Capacidad de almacenamiento suficiente

Dado que el trayecto dura hasta 7 dias sin conectividad,
Cuando el sistema almacena datos continuamente,
Entonces la tarjeta SD tiene capacidad suficiente para retener todos los datos sin sobrescribir,
Y conserva la trazabilidad completa del envio.

Story ID

User

Priority

Epic

US16

Supervisor de logistica

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Sincronizar datos con la nube automaticamente

Description

Como supervisor de logistica, quiero que los datos almacenados localmente se sincronicen automaticamente con la nube cuando haya conectividad disponible, para tener trazabilidad completa y respaldo de la informacion.

Acceptance Criteria

Escenario 01: Sincronizacion automatica al detectar WiFi

Dado que el contenedor regresa a una zona con conexion a internet,
Cuando el sistema detecta una red WiFi configurada o datos moviles,
Entonces inicia automaticamente la sincronizacion de datos pendientes con la nube,
Y mantiene actualizado el historial centralizado.
Escenario 02: Resumen de sincronizacion

Dado que la sincronizacion ha finalizado,
Cuando el supervisor revisa el dashboard,
Entonces puede ver un resumen de los datos sincronizados y confirmar que no hubo perdida de informacion,
Y valida la integridad del respaldo en nube.

Story ID

User

Priority

Epic

US17

Administrador de ONG

Alta

EP04 - Almacenamiento y Sincronizacion de Datos

Title

Generar reporte de trazabilidad para donantes

Description

Como administrador de ONG, quiero generar un reporte de trazabilidad completo de un envio o proyecto, para presentar evidencia a donantes internacionales sobre el uso adecuado de los recursos.

Acceptance Criteria

Escenario 01: Generacion de reporte por envio

Dado que un envio ha sido completado y sincronizado,
Cuando el administrador selecciona el envio y la opcion "Generar reporte",
Entonces el sistema crea un documento PDF con graficos de temperatura, mapa de ruta, incidentes y estado final,
Y deja evidencia formal para auditoria de donantes.
Escenario 02: Reporte consolidado por proyecto

Dado que un proyecto incluye multiples envios,
Cuando el administrador selecciona el proyecto y un rango de fechas,
Entonces el sistema genera un reporte consolidado con resumen de todos los envios y estadisticas agregadas,
Y facilita la rendicion de cuentas del proyecto.

Story ID

User

Priority

Epic

US18

Administrador

Media

EP05 - Gestion de Usuarios y Roles

Title

Ejecutar override manual mediante boton fisico

Description

Como operador autorizado, quiero ejecutar override manual mediante botones fisicos en el contenedor, para desbloquear la caja o silenciar alertas en situaciones de emergencia cuando no tengo acceso a la app.

Acceptance Criteria

Escenario 01: Desbloqueo por override fisico

Dado que el contenedor esta bloqueado por una condicion critica,
Cuando el operador autorizado presiona el boton de override y ingresa su codigo,
Entonces el servo desbloquea el contenedor y se registra la accion con el ID del operador,
Y queda constancia para seguimiento posterior.
Escenario 02: Silenciar buzzer mediante boton fisico

Dado que el buzzer esta activo por una alerta,
Cuando el operador presiona el boton de silencio,
Entonces el buzzer se desactiva pero el LED rojo continua parpadeando,
Y la alerta visual se mantiene hasta resolver la condicion.

Story ID

User

Priority

Epic

US19

Supervisor de logistica

Media

EP05 - Gestion de Usuarios y Roles

Title

Ejecutar override remoto desde app o web

Description

Como supervisor de logistica, quiero ejecutar override remoto desde la app o web, para autorizar el desbloqueo del contenedor a distancia sin necesidad de que el operador este fisicamente presente.

Acceptance Criteria

Escenario 01: Override remoto por supervisor

Dado que el operador solicita autorizacion para abrir el contenedor en una zona remota,
Cuando el supervisor recibe la solicitud en la app y la aprueba,
Entonces el sistema envia una senal al contenedor para desbloquear el servo temporalmente,
Y habilita la apertura bajo control remoto autorizado.
Escenario 02: Registro de override remoto

Dado que se ejecuto un override remoto,
Cuando se revisa el log del envio,
Entonces se registra quien autorizo, desde que dispositivo, en que momento y la duracion del desbloqueo,
Y se conserva trazabilidad completa de la aprobacion.

Story ID

User

Priority

Epic

US20

Administrador

Alta

EP05 - Gestion de Usuarios y Roles

Title

Configurar rangos permitidos por tipo de producto

Description

Como administrador, quiero configurar los rangos permitidos de temperatura, humedad y vibracion segun el tipo de producto (vacunas, insulina, sangre), para que el sistema evalue correctamente las condiciones segun los requerimientos especificos de cada producto.

Acceptance Criteria

Escenario 01: Seleccion de producto predefinido

Dado que el administrador esta configurando un nuevo envio,
Cuando selecciona un tipo de producto de la lista predefinida (ej. Vacuna Pfizer, Insulina),
Entonces el sistema carga automaticamente los rangos permitidos recomendados por el fabricante,
Y aplica los parametros al envio configurado.
Escenario 02: Configuracion manual de rangos personalizados

Dado que el producto no esta en la lista predefinida,
Cuando el administrador ingresa valores manuales para temperatura minima, maxima y otros parametros,
Entonces el sistema guarda la configuracion y la aplica a todos los envios de ese producto,
Y conserva la plantilla para futuras operaciones.


Technical Stories

En esta sección se describen las historias técnicas que desarrollamos para implementar las funcionalidades clave. Cada historia define tareas específicas que el equipo de desarrollo debe realizar, como crear endpoints, supervisar el sistema, generar reportes, entre otros.


Story ID

User

Priority

Epic

TS01

Developer

Alta

EP06 - Backend y API

Title

API Sincronización de datos desde dispositivo IoT

Description

Como desarrollador, necesito exponer un endpoint para que el dispositivo CryoGuard sincronice los datos almacenados localmente (temperatura, humedad, vibración, GPS, eventos de apertura) con la nube cuando haya conectividad disponible, asegurando la integridad y trazabilidad de la información.

Acceptance Criteria

Escenario 01: Sincronización exitosa de datos

Dado que el dispositivo tiene datos pendientes en su almacenamiento local (tarjeta SD),
Cuando detecta conexión a internet y envía los datos al endpoint de sincronización,
Entonces el backend recibe, valida y almacena los datos en la base de datos,
Y retorna código 201 con confirmación de recepción.
Escenario 02: Datos duplicados o ya sincronizados

Dado que el dispositivo envía un lote de datos ya sincronizados previamente,
Cuando el endpoint recibe datos con IDs duplicados,
Entonces el backend responde con código 200 indicando que ya existen,
Y no duplica registros en la base de datos.
Escenario 03: Error de conexión durante sincronización

Dado que el dispositivo está enviando datos y la conexión se interrumpe,
Cuando el endpoint no recibe el lote completo,
Entonces el backend no almacena datos parciales,
Y el dispositivo reintenta el envío completo en la siguiente sincronización.

Story ID

User

Priority

Epic

TS02

Developer

Alta

EP06 - Backend y API

Title

API Gestión de flags y alertas

Description

Como desarrollador, necesito exponer endpoints para que el dispositivo IoT envíe flags generados localmente (edge computing) y para que la web/app consulten alertas activas, permitiendo la supervisión en tiempo real de condiciones críticas.

Acceptance Criteria

Escenario 01: Recepción de flag crítico desde dispositivo

Dado que el dispositivo detecta una condición fuera de rango (ej. temperatura > 8°C),
Cuando envía el flag al endpoint correspondiente,
Entonces el backend registra el flag con timestamp, tipo, valor y ubicación GPS,
Y activa el proceso de notificaciones push a usuarios suscritos al envío.
Escenario 02: Consulta de flags activos por usuario

Dado que un supervisor accede al dashboard,
Cuando consume el endpoint GET /flags?estado=activo,
Entonces el backend retorna la lista de flags activos ordenados por prioridad,
Y solo incluye envíos asociados al usuario (por región o rol).
Escenario 03: Confirmación de flag (override)

Dado que un usuario autorizado confirma un flag desde la app,
Cuando consume el endpoint PUT /flags/{id}/confirmar,
Entonces el backend actualiza el estado del flag a confirmado,
Y registra quién lo confirmó, desde qué dispositivo y en qué momento.


Story ID

User

Priority

Epic

TS03

Developer

Alta

EP06 - Backend y API

Title

API Gestión de usuarios y roles

Description

Como desarrollador, necesito exponer endpoints para gestionar usuarios (crear, modificar, eliminar) y asignar roles (Operador, Supervisor, Administrador, ONG), asegurando autenticación segura y control de acceso basado en permisos.

Acceptance Criteria

Escenario 01: Registro exitoso de usuario

Dado que el administrador o el registro público está disponible,
Cuando el usuario envía email, contraseña válida y datos básicos,
Entonces el backend crea la cuenta en la base de datos y retorna token JWT o código 201,
Y la contraseña se almacena hasheada (bcrypt/argon2).
Escenario 02: Registro con email duplicado o datos inválidos

Dado que el endpoint de registro recibe datos,
Cuando el email ya existe o la contraseña no cumple requisitos,
Entonces el backend responde con código 409 (duplicado) o 400 (validación),
Y retorna un mensaje claro con el error específico.
Escenario 03: Autenticación de usuario (login)

Dado que un usuario tiene una cuenta activa,
Cuando envía sus credenciales al endpoint /auth/login,
Entonces el backend valida credenciales y retorna JWT con expiración configurada,
Y el rol/permisos del usuario quedan codificados en el token.
Escenario 04: Asignación de rol por administrador

Dado que un administrador gestiona usuarios desde el dashboard,
Cuando asigna o modifica el rol de un usuario,
Entonces el backend actualiza permisos del usuario,
Y registra la acción en un log de auditoría.

Story ID

User

Priority

Epic

TS04

Developer

Media

EP06 - Backend y API

Title

API Generación de reportes de trazabilidad

Description

Como desarrollador, necesito exponer endpoints para generar reportes de trazabilidad (por envío, por proyecto o por rango de fechas) en formatos PDF y Excel, para que supervisores y ONGs presenten evidencia a donantes y entes reguladores.

Acceptance Criteria

Escenario 01: Generación de reporte por ID de envío

Dado que un supervisor selecciona un envío completado,
Cuando consume GET /reportes/envio/{id}?formato=pdf,
Entonces el backend genera un PDF con gráficos, mapa de ruta, incidentes y estado final,
Y retorna el archivo para descarga.
Escenario 02: Generación de reporte consolidado por proyecto

Dado que una ONG necesita reportar múltiples envíos de un proyecto,
Cuando consume POST /reportes/proyecto con rango de fechas y lista de envíos,
Entonces el backend genera un consolidado con resumen estadístico y porcentaje de éxito,
Y retorna el archivo en formato PDF o Excel.
Escenario 03: Cache de reportes para evitar regeneración

Dado que el mismo reporte se solicita múltiples veces sin cambios de datos,
Cuando el backend recibe una solicitud idéntica (mismos parámetros),
Entonces retorna el reporte en caché sin regenerar documento,
Y reduce tiempo de respuesta y carga del servidor.





Story ID

User

Priority

Epic

TS05

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar lectura de sensores (temp, humedad, vibración, GPS, apertura)

Description

Como desarrollador embedded, necesito implementar la lectura de todos los sensores del dispositivo CryoGuard, asegurando captura de datos en intervalos configurables y almacenamiento local en tarjeta SD.

Acceptance Criteria

Escenario 01: Lectura periódica de sensores

Dado que el dispositivo está encendido y en modo operación,
Cuando el temporizador alcanza el intervalo configurado (ej. cada 30 segundos),
Entonces el sistema lee todos los sensores,
Y almacena datos en tarjeta SD con timestamp y GPS actual.
Escenario 02: Almacenamiento local en tarjeta SD

Dado que el dispositivo está en zona sin conectividad,
Cuando los sensores capturan datos,
Entonces los datos se escriben en la tarjeta SD en formato CSV o JSON,
Y se mantienen hasta la siguiente sincronización exitosa.
Escenario 03: Detección de fallo de sensor

Dado que un sensor no responde o envía datos inválidos,
Cuando el sistema intenta leerlo,
Entonces registra un error interno y continúa con los sensores restantes,
Y genera un flag de mantenimiento para el supervisor.



Story ID

User

Priority

Epic

TS06

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar reglas de edge computing para generación de flags

Description

Como desarrollador embedded, necesito implementar un motor de reglas que evalúe localmente los datos de sensores y genere flags preventivos o críticos cuando las variables salgan de rango, activando actuadores automáticamente.

Acceptance Criteria

Escenario 01: Evaluación de temperatura fuera de rango

Dado que el rango permitido es 2°C a 8°C,
Cuando el sensor registra 9°C durante más de 30 segundos,
Entonces el sistema genera un flag crítico,
Y activa LED rojo, buzzer y Peltier automáticamente.
Escenario 02: Evaluación de temperatura cercana al límite

Dado que el rango permitido es 2°C a 8°C,
Cuando el sensor registra 7.5°C cercano al límite superior,
Entonces el sistema genera un flag preventivo,
Y activa LED amarillo sin buzzer.
Escenario 03: Evaluación de vibración excesiva

Dado que existe un umbral máximo de vibración configurado,
Cuando el sensor registra un valor superior al umbral,
Entonces el sistema genera un flag de vibración,
Y registra el evento sin activar enfriamiento.
Escenario 04: Evaluación de apertura no autorizada

Dado que el contenedor está cerrado y no hay override activo,
Cuando el sensor de apertura detecta que la caja se abrió,
Entonces el sistema genera un flag crítico de apertura no autorizada,
Y activa LED rojo, buzzer y bloqueo de servo.

Story ID

User

Priority

Epic

TS07

Embedded Developer

Alta

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar control de actuadores (Peltier, Servo, LED, Buzzer)

Description

Como desarrollador embedded, necesito implementar el control de actuadores del dispositivo por reglas automáticas y por comandos manuales (botones físicos o remotos).

Acceptance Criteria

Escenario 01: Activación automática de Peltier por temperatura alta

Dado que se detectó un flag crítico por temperatura alta,
Cuando el sistema evalúa la regla correspondiente,
Entonces activa módulo Peltier, disipador y ventilador,
Y mantiene enfriamiento hasta normalización sostenida por 2 minutos.
Escenario 02: Activación de servo para bloqueo de apertura

Dado que se detectó un flag crítico o apertura no autorizada,
Cuando el sistema activa el bloqueo,
Entonces el servo bloquea físicamente la apertura del contenedor,
Y solo se desbloquea con override autorizado.
Escenario 03: Control de LEDs según estado

Dado que no hay flags activos, cuando se evalúa estado normal, entonces LED verde fijo.
Dado que hay flag preventivo activo, cuando la condición es cercana al límite, entonces LED amarillo fijo.
Dado que hay flag crítico activo, cuando la condición está fuera de rango, entonces LED rojo parpadeante.
Y el patrón visual refleja de forma inmediata la severidad actual.
Escenario 04: Control de buzzer para alertas sonoras

Dado que hay un flag crítico activo,
Cuando se activa la alerta sonora,
Entonces el buzzer emite pitido intermitente y puede silenciarse por botón físico,
Pero el LED rojo continúa parpadeando hasta normalizar la condición.

Story ID

User

Priority

Epic

TS08

Embedded Developer

Media

EP07 - Dispositivo IoT y Edge Computing

Title

Implementar botones físicos para override y control manual

Description

Como desarrollador embedded, necesito implementar lectura de botones físicos (override, silencio, prueba de LEDs, reset) para permitir acciones manuales sin depender de app o web.

Acceptance Criteria

Escenario 01: Override manual mediante botón físico

Dado que el contenedor está bloqueado por un flag crítico,
Cuando el operador autorizado presiona el botón de override por 3 segundos,
Entonces el sistema desbloquea el servo por 30 segundos,
Y registra la acción en el log con timestamp e identificador.
Escenario 02: Silencio de buzzer mediante botón físico

Dado que el buzzer está activo por un flag crítico,
Cuando el operador presiona el botón de silencio,
Entonces el buzzer se desactiva inmediatamente,
Pero el LED rojo continúa parpadeando hasta normalizar la condición.
Escenario 03: Prueba de LEDs y buzzer

Dado que el operador quiere verificar actuadores,
Cuando presiona el botón de prueba,
Entonces el sistema enciende LED rojo, amarillo y verde en secuencia,
Y activa el buzzer por 1 segundo.

Story ID

User

Priority

Epic

TS09

Frontend Developer

Alta

EP08 - Dashboard Web

Title

Implementar dashboard con mapa interactivo de envíos activos

Description

Como desarrollador frontend, necesito implementar un dashboard web con mapa interactivo para visualizar contenedores activos y su estado en tiempo real.

Acceptance Criteria

Escenario 01: Carga inicial del mapa con envíos activos

Dado que un supervisor autenticado accede al dashboard,
Cuando la página se carga,
Entonces el frontend consume GET /envios/activos y renderiza marcadores activos,
Y cada marcador muestra color según estado del envío.
Escenario 02: Actualización en tiempo real de posiciones

Dado que el dashboard está abierto y hay envíos en movimiento,
Cuando el backend recibe nuevos datos de ubicación,
Entonces el frontend actualiza posiciones sin recargar la página,
Mediante WebSockets o polling cada 10 segundos.
Escenario 03: Click en marcador para ver detalles del envío

Dado que el supervisor hace click en un marcador,
Cuando se abre un panel lateral,
Entonces muestra ID, producto, temperatura actual, flags activos y último dato de humedad,
Y permite supervisión operativa contextual por envío.

Story ID

User

Priority

Epic

TS10

Frontend Developer

Alta

EP08 - Dashboard Web

Title

Implementar tabla de logs históricos con filtros y exportación

Description

Como desarrollador frontend, necesito implementar tabla paginada de logs históricos con filtros por fecha/región/producto/estado y exportación a CSV/PDF.

Acceptance Criteria

Escenario 01: Visualización de tabla de logs

Dado que un supervisor accede a la sección de historial,
Cuando la página se carga,
Entonces el frontend consume GET /logs?page=1&limit=50 y renderiza tabla de resultados,
Y muestra columnas de envío, fecha, producto, promedio, incidentes y estado final.
Escenario 02: Filtros combinados

Dado que el supervisor aplica filtros combinados,
Cuando hace click en Aplicar filtros,
Entonces el frontend consume endpoint con parámetros de filtro,
Y actualiza la tabla con resultados filtrados.
Escenario 03: Exportación a CSV/PDF

Dado que el supervisor necesita exportar los logs filtrados,
Cuando hace click en Exportar y selecciona CSV,
Entonces el frontend descarga archivo con los datos de la tabla actual,
Y respeta filtros aplicados en la exportación.

Story ID

User

Priority

Epic

TS11

Frontend Developer

Media

EP08 - Dashboard Web

Title

Implementar panel de administración de usuarios y roles

Description

Como desarrollador frontend, necesito implementar un panel para crear, modificar, eliminar usuarios y asignar roles (Operador, Supervisor, Administrador, ONG).

Acceptance Criteria

Escenario 01: Listado de usuarios con paginación

Dado que un administrador accede al panel de usuarios,
Cuando la página se carga,
Entonces el frontend consume GET /admin/usuarios y renderiza la tabla,
Y muestra nombre, email, rol, fecha y estado.
Escenario 02: Creación de nuevo usuario

Dado que el administrador hace click en Nuevo usuario,
Cuando completa formulario y confirma,
Entonces el frontend envía POST /admin/usuarios,
Y actualiza la tabla con el nuevo registro.
Escenario 03: Edición y eliminación de usuarios

Dado que el administrador selecciona un usuario de la tabla,
Cuando edita rol o deshabilita cuenta,
Entonces el frontend envía PUT o DELETE según corresponda,
Y refresca la tabla reflejando los cambios.

Story ID

User

Priority

Epic

TS12

Mobile Developer

Alta

EP09 - Aplicación Móvil

Title

Implementar autenticación y pantalla de inicio en app móvil

Description

Como desarrollador móvil (iOS/Android), necesito implementar login/registro y pantalla de inicio para que operadores y supervisores accedan y vean sus envíos asignados.

Acceptance Criteria

Escenario 01: Login con email y contraseña

Dado que el usuario tiene una cuenta activa,
Cuando ingresa credenciales y presiona Iniciar sesión,
Entonces la app consume POST /auth/login y almacena JWT de forma segura,
Y navega a la pantalla de inicio.
Escenario 02: Almacenamiento seguro de token

Dado que el usuario cierra y abre nuevamente la app,
Cuando el token aún no ha expirado,
Entonces la app restaura sesión automáticamente,
Y evita solicitar login nuevamente.
Escenario 03: Pantalla de inicio con resumen de envíos

Dado que el usuario está autenticado y tiene envíos asignados,
Cuando la app carga la pantalla de inicio,
Entonces consume GET /envios/asignados y muestra lista de envíos activos,
Y presenta ID, destino, temperatura y estado.

Story ID

User

Priority

Epic

TS13

Mobile Developer

Alta

EP09 - Aplicación Móvil

Title

Implementar recepción de notificaciones push

Description

Como desarrollador móvil, necesito implementar recepción de notificaciones push (FCM/APNs) para alertas en tiempo real por flags críticos de envíos asignados.

Acceptance Criteria

Escenario 01: Registro del dispositivo para notificaciones push

Dado que el usuario inicia sesión por primera vez,
Cuando la app solicita permisos y el usuario los acepta,
Entonces la app obtiene el token push y lo envía al backend,
Y queda asociado al usuario autenticado.
Escenario 02: Recepción de notificación por flag crítico

Dado que el usuario tiene sesión activa o app en segundo plano,
Cuando backend envía notificación por flag crítico asignado,
Entonces la app muestra notificación en el centro del dispositivo,
Y al tocarla abre detalle del envío.
Escenario 03: Contenido de la notificación

Dado que el usuario recibe una notificación push,
Cuando la visualiza,
Entonces el título indica Alerta CryoGuard - Flag Crítico,
Y el cuerpo muestra tipo de flag, valor e ID de envío.


Story ID

User

Priority

Epic

TS14

Mobile Developer

Media

EP09 - Aplicación Móvil

Title

Implementar override remoto desde app móvil

Description

Como desarrollador móvil, necesito implementar override remoto para que supervisores autorizados desbloqueen contenedor a distancia o gestionen alertas cuando lo solicite el operador.

Acceptance Criteria

Escenario 01: Solicitud de override por operador

Dado que el contenedor está bloqueado y el operador necesita abrirlo,
Cuando el operador solicita autorización desde app o por comunicación externa,
Entonces el supervisor recibe una notificación push con la solicitud,
Y puede revisar contexto del envío antes de aprobar.
Escenario 02: Aprobación de override por supervisor

Dado que el supervisor recibe la solicitud de override,
Cuando abre la app, selecciona envío y presiona Autorizar desbloqueo,
Entonces la app consume POST /envios/{id}/override-remoto y backend envía comando al IoT,
Y registra la acción en el log con ID del supervisor.
Escenario 03: Confirmación de override exitoso

Dado que el comando de override fue enviado exitosamente,
Cuando el dispositivo confirma la recepción,
Entonces la app muestra mensaje Contenedor desbloqueado temporalmente,
Y el operador puede abrir la caja.