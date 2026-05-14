# Proposal: Refactorización Backend Completa CryoGuard

## 1. Problem Statement

El backend actual de CryoGuard no refleja la arquitectura completa definida en las user stories. Faltan bounded contexts críticos y los existentes no tienen la estructura de datos alineada con el frontend mock.

**Estado actual:**
- IAM: Parcial (solo Users, falta roles completos y audit trail)
- Monitoring: Parcial (Container existe, falta telemetry)
- Evaluation: Parcial (Alert y MonitoringRule existen, falta flags)
- Logistics: Parcial (Route y Geofence existen, falta Fleet)
- **ACTUATORS: NO EXISTE** → LED, Buzzer, Peltier, Servo
- **OPERATIONS: NO EXISTE** → Flags, override, usar/transferir/descartar
- **NOTIFICATIONS: NO EXISTE** → Push, email, pantalla
- **AUDIT: NO EXISTE** → Log de quién accionó qué

## 2. Scope

### 2.1 Bounded Contexts a Refactorizar/Crear

| Bounded Context | Estado | Target |
|-----------------|--------|--------|
| **IAM** (Identity & Access Management) | ⚠️ Parcial | Completo con audit trail y override manual |
| **Monitoring** (Monitoreo IoT) | ⚠️ Parcial | Completo con lectura de sensores |
| **Evaluation** (Core Flags & Rules) | ⚠️ Parcial | Completo con evaluación de flags |
| **Logistics** (GPS, Geocercas) | ⚠️ Parcial | Completo con rutas y tracking |
| **Actuators** (Control y Actuación) | ❌ No existe | LED, Buzzer, Peltier, Servo |
| **Operations** (Flags & Acciones) | ❌ No existe | Override, usar, transferir, descartar |
| **Notifications** (Alertas) | ❌ No existe | Push, email, pantalla |
| **Audit** (Logs de Seguridad) | ❌ No existe | Trazabilidad completa |

### 2.2 Datos del Frontend a Considerar

El frontend mock (`C:\Users\meteg\Downloads\project`) define la estructura de datos esperada:

**Container:**
```typescript
interface Container {
  id: string;
  name: string;
  status: 'active' | 'inactive' | 'maintenance';
  currentLocation: { lat: number; lng: number } | null;
  temperature: number | null;
  humidity: number | null;
  batteryLevel: number;
  lastUpdate: string;
  productType: string;
}
```

**Alert:**
```typescript
interface Alert {
  id: string;
  containerId: string;
  type: 'temperature' | 'humidity' | 'vibration' | 'door' | 'geofence';
  severity: 'critical' | 'warning' | 'info';
  message: string;
  timestamp: string;
  acknowledged: boolean;
}
```

**Route:**
```typescript
interface Route {
  id: string;
  containerId: string;
  origin: string;
  destination: string;
  status: 'active' | 'completed' | 'cancelled';
  startTime: string;
  estimatedArrival: string;
  currentLocation: { lat: number; lng: number } | null;
}
```

**AuditLog:**
```typescript
interface AuditLog {
  id: string;
  userId: string;
  userName: string;
  action: string;
  timestamp: string;
  details: string;
}
```

## 3. Proposed Architecture

### 3.1 Bounded Context Dependencies

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ Monitoring  │────▶│ Evaluation  │────▶│ Notifications│
│ (sensors)   │     │ (flags)     │     │ (alerts)    │
└─────────────┘     └─────────────┘     └─────────────┘
                           │
                           ▼
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ Operations  │◀───│ Actuators   │◀───│   IAM       │
│ (override)  │     │ (control)  │     │ (auth)      │
└─────────────┘     └─────────────┘     └─────────────┘
                           │
                           ▼
                   ┌─────────────┐
                   │   Audit    │
                   │ (logs)     │
                   └─────────────┘
```

### 3.2 Data Models per Bounded Context

**IAM:**
- User (id, username, email, password, role, status)
- Role (ADMIN, OPERATOR, SUPERVISOR, NGO)
- Permission (READ, WRITE, OVERRIDE, ADMIN)
- AuditEntry (id, userId, action, timestamp, details)

**Monitoring:**
- Container (id, name, status, location, productType)
- Telemetry (id, containerId, temperature, humidity, vibration, gps, timestamp)
- SensorReading (id, containerId, type, value, unit, timestamp)

**Evaluation:**
- MonitoringRule (id, name, type, minValue, maxValue, severity)
- Alert (id, containerId, type, severity, message, timestamp, acknowledged)
- Flag (id, containerId, type, value, threshold, severity, active)

**Logistics:**
- Route (id, containerId, origin, destination, status, startTime, estimatedArrival)
- Geofence (id, name, coordinates[], radius, active)
- LocationRecord (id, routeId, lat, lng, timestamp)

**Actuators:**
- ActuatorState (id, containerId, ledColor, buzzerActive, peltierActive, servoLocked)
- ActuatorCommand (id, containerId, type, payload, issuedBy, timestamp)

**Operations:**
- Operation (id, containerId, type: USE|TRANSFER|DISCARD, status, requestedBy, timestamp)
- OverrideRequest (id, containerId, requestedBy, approvedBy, status, reason)

**Notifications:**
- Notification (id, userId, containerId, type, title, body, read, timestamp)
- NotificationPreference (id, userId, type: PUSH|EMAIL|SMS, enabled)

**Audit:**
- AuditLog (id, userId, userName, action, entityType, entityId, details, timestamp)

## 4. Rollback Plan

1. Mantener backup del código actual antes de refactorizar
2. Cada bounded context se refactoriza en rama separada
3. Tests de integración antes de merge a main
4. Desplegar primero a ambiente de staging

## 5. Risks

| Risk | Mitigation |
|------|------------|
| Pérdida de datos por cambios en modelos | Validar migraciones con tests |
| Conflictos con código existente | Code review obligatorio |
| Frontend no alineado con backend | Sincronizar estructura de datos del frontend-analysis.md |

## 6. Success Criteria

- [ ] Todos los bounded contexts implementados
- [ ] Estructura de datos alineada con frontend mock
- [ ] Endpoints RESTful para cada contexto
- [ ] Tests unitarios con cobertura >70%
- [ ] Aplicación arranca sin errores con profile dev