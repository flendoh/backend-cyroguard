# CryoGuard User Story & Technical Story Coverage

## User Stories (US01-US20)

---

## US01: Monitorear temperatura en tiempo real

**Description**: Como operador de transporte, quiero monitorear la temperatura del contenedor en tiempo real durante todo el trayecto, para asegurar que las vacunas y medicamentos se mantengan dentro del rango permitido.

**Frontend**:
- `DashboardHome.tsx` - KPI card shows Average Temperature, container table shows per-container temp
- `Monitoring.tsx` - Live temperature chart with real-time updates (simulated), sensor panel with current temp reading

**Backend**: Expected endpoint `GET /telemetry/realtime` or `GET /sensores/temperatura`

**Status**: ⚠️ **Partial** - UI exists with mock data, no real API integration

---

## US02: Monitorear humedad del contenedor

**Description**: Como operador de transporte, quiero monitorear los niveles de humedad dentro del contenedor, para evitar que la humedad excesiva dañe los productos biomédicos sensibles.

**Frontend**:
- `DashboardHome.tsx` - Humidity shown in container table
- `Monitoring.tsx` - Live humidity chart, sensor panel with humidity reading

**Backend**: Expected endpoint `GET /sensores/humedad`

**Status**: ⚠️ **Partial** - UI exists with mock data, no real API integration

---

## US03: Detectar vibraciones o impactos

**Description**: Como operador de transporte, quiero detectar vibraciones o impactos anormales durante el traslado, para identificar si los productos han sufrido golpes que puedan comprometer su integridad.

**Frontend**:
- `Monitoring.tsx` - Vibration panel showing current vibration (g) and threshold (1.0g), event feed shows vibration alerts

**Backend**: Expected endpoint `GET /sensores/vibracion`

**Status**: ⚠️ **Partial** - UI exists with mock data

---

## US04: Geolocalizar contenedor en tiempo real

**Description**: Como supervisor de logística, quiero conocer la ubicación GPS del contenedor en tiempo real, para monitorear que el transporte siga la ruta planificada.

**Frontend**:
- `DashboardHome.tsx` - Map placeholder showing container positions, GPS coordinates in container table
- `Routes.tsx` - Route management with GPS tracking, geofence display

**Backend**: Expected endpoints `GET /envios/activos` (includes GPS), `GET /rutas`

**Status**: ⚠️ **Partial** - Map is placeholder only (OpenStreetMap integration planned but not implemented)

---

## US05: Activar enfriamiento automático (Peltier)

**Description**: Como supervisor de logística, quiero que el sistema active automáticamente el enfriamiento (Peltier) cuando la temperatura supere el rango permitido, para proteger los productos sin intervención manual.

**Frontend**: ❌ **NOT IMPLEMENTED** - No UI for device control

**Backend**: Expected endpoint `POST /dispositivo/peltier/activar` (device command API)

**Status**: ❌ **Missing in Frontend** - This is a device-side story; web dashboard cannot control hardware directly. Would need a control panel UI that sends commands to device.

---

## US06: Bloquear apertura del contenedor (Servo)

**Description**: Como administrador, quiero que el sistema bloquee automáticamente la apertura del contenedor cuando se detecte una condición crítica, para evitar que se acceda a productos en estado vulnerable.

**Frontend**: ❌ **NOT IMPLEMENTED** - No UI for servo lock control

**Backend**: Expected endpoint `POST /dispositivo/servo/bloquear`

**Status**: ❌ **Missing in Frontend** - Device control story. No UI to manually unlock or view lock status.

---

## US07: Recibir alerta visual mediante LED

**Description**: Como operador de transporte, quiero recibir alertas visuales mediante LEDs de colores, para identificar rápidamente el estado del contenedor sin necesidad de mirar una pantalla.

**Frontend**: ❌ **NOT IMPLEMENTED** - This is device-side (LEDs on hardware)

**Backend**: N/A - Device firmware controls LEDs

**Status**: ❌ **Not Applicable** - LED alerts are physical/device-side, not web dashboard

---

## US08: Recibir alerta sonora mediante Buzzer

**Description**: Como operador de transporte, quiero recibir alertas sonoras cuando se detecte una condición crítica, para actuar inmediatamente incluso si no estoy mirando el contenedor.

**Frontend**: ❌ **NOT IMPLEMENTED** - This is device-side (buzzer on hardware)

**Backend**: N/A - Device firmware controls buzzer

**Status**: ❌ **Not Applicable** - Buzzer alerts are physical/device-side, not web dashboard

---

## US09: Detectar salida de geocerca

**Description**: Como supervisor de logística, quiero recibir una alerta cuando el contenedor salga de la ruta o geocerca definida, para identificar desviaciones no planificadas.

**Frontend**:
- `Alerts.tsx` - Shows alerts for geofence violations (mock data)
- `Routes.tsx` - Geofence configuration and display

**Backend**: Expected endpoints `GET /geocercas`, `POST /alertas/geocerca`

**Status**: ⚠️ **Partial** - Alerts UI shows geofence alerts but no actual detection logic

---

## US10: Detectar apertura no autorizada del contenedor

**Description**: Como supervisor de logística, quiero recibir una alerta cuando el contenedor se abra sin autorización, para identificar posibles manipulaciones indebidas de los productos.

**Frontend**:
- `Alerts.tsx` - "Door Opened" alert type shown in mock data
- `Monitoring.tsx` - Door status panel shows "Closed" / "Open"

**Backend**: Expected endpoints `GET /flags` (TS02), door sensor event capture

**Status**: ⚠️ **Partial** - Door status UI exists but no actual integration

---

## US11: Visualizar dashboard con mapa de envíos activos

**Description**: Como supervisor de logística, quiero visualizar un dashboard web con un mapa que muestre todos los envíos activos, para supervisar su ubicación y estado de manera centralizada.

**Frontend**:
- `DashboardHome.tsx` - Map placeholder, active containers table with status badges (Normal/Critical/Preventivo/Offline), KPI cards

**Backend**: Expected endpoints `GET /envios/activos` with status, `GET /contenedores`

**Status**: ⚠️ **Partial** - Map is placeholder (OpenStreetMap integration planned), table shows mock data

---

## US12: Consultar logs históricos de envíos

**Description**: Como supervisor de logística, quiero consultar los logs históricos de envíos anteriores, para analizar incidentes pasados y mejorar rutas futuras.

**Frontend**:
- `AuditLogs.tsx` - Full audit log table with search/filter, timestamp, user, action, module, result
- `Reports.tsx` - Export functionality with format selection (CSV/PDF/JSON/XLSX)

**Backend**: Expected endpoints `GET /logs?page=1&limit=50` (TS10), `POST /reportes/generar`

**Status**: ⚠️ **Partial** - UI exists with mock data, export not functional

---

## US13: Recibir notificaciones push en app móvil

**Description**: Como operador de transporte, quiero recibir notificaciones push en mi aplicación móvil cuando se detecte una condición crítica, para actuar rápidamente incluso si no estoy cerca del contenedor.

**Frontend**: ❌ **NOT IMPLEMENTED** - Mobile app does not exist (EP09 not built)

**Backend**: Expected endpoint for FCM/APNs token registration, push notification dispatch (TS13)

**Status**: ❌ **Missing** - Mobile app (EP09 - TS12, TS13, TS14) not developed

---

## US14: Gestionar usuarios y roles desde dashboard

**Description**: Como administrador, quiero gestionar usuarios y asignar roles desde el dashboard web, para controlar quien tiene acceso a que funcionalidades del sistema.

**Frontend**:
- `Users.tsx` - Full CRUD UI with: user list table, add user modal, edit/delete actions, role assignment (Administrator/Operator)

**Backend**: Expected endpoints `GET /admin/usuarios`, `POST /admin/usuarios`, `PUT /admin/usuarios/{id}`, `DELETE /admin/usuarios/{id}` (TS03)

**Status**: ⚠️ **Partial** - UI complete with mock data, no actual API integration

---

## US15: Almacenar datos localmente durante transporte offline

**Description**: Como operador de transporte, quiero que el sistema almacene todos los datos de sensores localmente cuando no haya conexión a internet, para no perder información durante trayectos en zonas sin cobertura.

**Frontend**: ❌ **NOT IMPLEMENTED** - Device-side story (SD card storage on ESP32)

**Backend**: N/A - Device firmware handles local storage

**Status**: ❌ **Not Applicable** - Local storage is device-side (TS05)

---

## US16: Sincronizar datos con la nube automáticamente

**Description**: Como supervisor de logística, quiero que los datos almacenados localmente se sincronicen automáticamente con la nube cuando haya conectividad disponible, para tener trazabilidad completa y respaldo de la información.

**Frontend**:
- `Settings.tsx` - Synchronization config (retry attempts, batch size), offline operation info (72 hours)
- `Containers.tsx` - Shows connectivity status per container

**Backend**: Expected endpoints `POST /sync` (TS01), `GET /sync/status`

**Status**: ⚠️ **Partial** - Settings UI exists but sync not implemented

---

## US17: Generar reporte de trazabilidad para donors

**Description**: Como administrador de ONG, quiero generar un reporte de trazabilidad completo de un envío o proyecto, para presentar evidencia a donors internacionales sobre el uso adecuado de los recursos.

**Frontend**:
- `Reports.tsx` - Report generation form (type, format, date range, container), recent reports table with download

**Backend**: Expected endpoints `GET /reportes/envio/{id}?formato=pdf` (TS04), `POST /reportes/proyecto`

**Status**: ⚠️ **Partial** - UI exists with mock data, report generation not functional

---

## US18: Ejecutar override manual mediante botón físico

**Description**: Como operador autorizado, quiero ejecutar override manual mediante botones físicos en el contenedor, para desbloquear la caja o silenciar alertas en situaciones de emergencia cuando no tengo acceso a la app.

**Frontend**: ❌ **NOT IMPLEMENTED** - Device-side story (physical buttons on hardware)

**Backend**: Expected device-level override tracking via `POST /dispositivo/override` (logs via TS02)

**Status**: ❌ **Not Applicable** - Physical button override is device-side (TS08)

---

## US19: Ejecutar override remoto desde app o web

**Description**: Como supervisor de logística, quiero ejecutar override remoto desde la app o web, para autorizar el desbloqueo del contenedor a distancia sin necesidad de que el operador esté físicamente presente.

**Frontend**: ❌ **NOT IMPLEMENTED** - No UI for remote override

**Backend**: Expected endpoint `POST /envios/{id}/override-remoto` (TS14)

**Status**: ❌ **Missing in Frontend** - No remote unlock UI in dashboard

---

## US20: Configurar rangos permitidos por tipo de producto

**Description**: Como administrador, quiero configurar los rangos permitidos de temperatura, humedad y vibración según el tipo de producto (vacunas, insulina, sangre), para que el sistema evalúe correctamente las condiciones según los requerimientos específicos de cada producto.

**Frontend**:
- `Settings.tsx` - Monitoring rules: temperature range (min/max), humidity range (min/max), vibration threshold
- `Containers.tsx` - Add container form has "Temp Range" and "Humidity Range" fields

**Backend**: Expected endpoint `GET/PUT /configuracion/rangos` or product type configuration

**Status**: ⚠️ **Partial** - Global ranges configurable but no product type templates (Vaccine Pfizer, Insulin, Blood, etc.)

---

## Technical Stories (TS01-TS14)

---

## TS01: API Sincronización de datos desde dispositivo IoT

**Description**: Endpoint para que el dispositivo CryoGuard sincronice datos almacenados localmente con la nube.

**Frontend**: N/A - Device-to-backend direct communication

**Backend Required**:
- `POST /sync` - Receive sensor data batch from device
- `GET /sync/status` - Check sync status
- Handle duplicate detection, partial data rejection

**Status**: ❌ **Backend Not Implemented** - No backend API found for this

---

## TS02: API Gestión de flags y alertas

**Description**: Endpoints para que el dispositivo IoT envíe flags y la web/app consulte alertas activas.

**Frontend**:
- `Alerts.tsx` - Display alerts with severity, status, actions (Acknowledge, Resolve, Escalate)
- `Settings.tsx` - Notification toggles

**Backend Required**:
- `POST /flags` - Receive flag from device
- `GET /flags?estado=activo` - Query active flags
- `PUT /flags/{id}/confirmar` - Confirm/resolve flag

**Status**: ⚠️ **Partial** - UI has mock data, no actual API

---

## TS03: API Gestión de usuarios y roles

**Description**: Endpoints para gestionar usuarios y asignar roles con autenticación JWT.

**Frontend**:
- `Login.tsx` - Login form
- `Users.tsx` - User management CRUD

**Backend Required**:
- `POST /auth/register` - User registration
- `POST /auth/login` - Authentication, return JWT
- `GET /admin/usuarios` - List users
- `POST /admin/usuarios` - Create user
- `PUT /admin/usuarios/{id}` - Update user/role
- `DELETE /admin/usuarios/{id}` - Delete user

**Status**: ⚠️ **Partial** - UI exists, no actual backend API

---

## TS04: API Generación de reportes de trazabilidad

**Description**: Endpoints para generar reportes de trazabilidad en PDF y Excel.

**Frontend**:
- `Reports.tsx` - Report generation form, download table

**Backend Required**:
- `GET /reportes/envio/{id}?formato=pdf` - Generate single shipment report
- `POST /reportes/proyecto` - Generate consolidated project report
- Report caching

**Status**: ⚠️ **Partial** - UI with mock data, no actual generation

---

## TS05: Implementar lectura de sensores (device-side)

**Description**: Implementar lectura de sensores en el dispositivo (temp, humedad, vibración, GPS, apertura).

**Frontend**: N/A

**Backend**: N/A

**Status**: ❌ **Device-side Only** - Not applicable to web frontend

---

## TS06: Implementar reglas de edge computing para generación de flags

**Description**: Motor de reglas que evalúa datos localmente y genera flags preventivos/críticos.

**Frontend**: N/A

**Backend**: N/A

**Status**: ❌ **Device-side Only** - Not applicable to web frontend

---

## TS07: Implementar control de actuadores (Peltier, Servo, LED, Buzzer)

**Description**: Control de actuadores por reglas automáticas y comandos manuales.

**Frontend**: N/A (would need device control panel)

**Backend**: Expected device command API

**Status**: ❌ **Device-side Only** - No frontend for actuator control

---

## TS08: Implementar botones físicos para override y control manual

**Description**: Lectura de botones físicos (override, silencio, prueba de LEDs, reset).

**Frontend**: N/A

**Backend**: N/A

**Status**: ❌ **Device-side Only** - Not applicable to web frontend

---

## TS09: Implementar dashboard con mapa interactivo de envíos activos

**Description**: Dashboard web con mapa interactivo para visualizar contenedores activos.

**Frontend**:
- `DashboardHome.tsx` - Map placeholder, container table
- `Containers.tsx` - Container management

**Backend Required**: `GET /envios/activos` with GPS coordinates and status

**Status**: ⚠️ **Partial** - UI has placeholder map, no real-time updates

---

## TS10: Implementar tabla de logs históricos con filtros y exportación

**Description**: Tabla paginada de logs históricos con filtros y exportación CSV/PDF.

**Frontend**:
- `AuditLogs.tsx` - Search, filter by module, paginated table

**Backend Required**: `GET /logs?page=1&limit=50&modulo=X`

**Status**: ⚠️ **Partial** - UI with mock data, no actual pagination/filters

---

## TS11: Implementar panel de administración de usuarios y roles

**Description**: Panel para crear, modificar, eliminar usuarios y asignar roles.

**Frontend**:
- `Users.tsx` - Full CRUD with role assignment

**Backend Required**: Full user management API (see TS03)

**Status**: ⚠️ **Partial** - UI complete with mock data

---

## TS12: Implementar autenticación y pantalla de inicio en app móvil

**Description**: Login/registro y pantalla de inicio en app móvil para operadores y supervisores.

**Frontend**: 
- `Login.tsx` - Web login exists
- Mobile app: ❌ **NOT BUILT**

**Backend Required**: `POST /auth/login` (shared with web)

**Status**: ⚠️ **Web Login Exists** - Mobile app (EP09) not developed

---

## TS13: Implementar recepción de notificaciones push

**Description**: Recepción de notificaciones push (FCM/APNs) para alertas en tiempo real.

**Frontend**: ❌ **NOT IMPLEMENTED**

**Backend Required**: FCM/APNs token registration, push dispatch

**Status**: ❌ **Missing** - No push notification service

---

## TS14: Implementar override remoto desde app móvil

**Description**: Override remoto para que supervisores desbloqueen contenedor a distancia.

**Frontend**: ❌ **NOT IMPLEMENTED**

**Backend Required**: `POST /envios/{id}/override-remoto`

**Status**: ❌ **Missing** - No override UI in web or mobile

---

## Summary Table

| Story | Frontend | Backend | Status |
|-------|----------|---------|--------|
| US01-US04, US09-US12, US14, US16-US17, US20 | ⚠️ Mock UI | ❌ Not built | Partial |
| US05-US06, US18 | ❌ None | N/A | Device-side only |
| US07-US08, US15 | N/A | N/A | Device-side only |
| US13 | ❌ None | ❌ Not built | Mobile app missing |
| US19 | ❌ None | ❌ Not built | No override UI |
| TS01-TS02 | ⚠️ Mock UI | ❌ Not built | Partial |
| TS03-TS04, TS09-TS11 | ⚠️ Mock UI | ❌ Not built | Partial |
| TS05-TS08 | N/A | N/A | Device-side only |
| TS12 | ⚠️ Web login only | ⚠️ Partial | Mobile missing |
| TS13-TS14 | ❌ None | ❌ Not built | Missing |

---

## Critical Gaps

1. **No Backend API** - All API endpoints referenced are expected but not implemented
2. **No API Service Layer** - Frontend has no `src/app/imports/` or `src/app/services/` with actual API calls
3. **No Real-Time** - WebSocket integration for live updates not implemented
4. **No Authentication State** - Login doesn't persist, no JWT handling
5. **No Mobile App** - EP09 (TS12, TS13, TS14) completely missing
6. **No Push Notifications** - FCM setup present in Settings but no service
7. **No Map Integration** - OpenStreetMap placeholder only
8. **No Device Control UI** - Cannot trigger override or view device lock status
9. **No Offline Handling** - UI doesn't account for connectivity loss
10. **No Geofence Logic** - Routes page shows geofences but no detection of exit