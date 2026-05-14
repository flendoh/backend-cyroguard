# CryoGuard Frontend Analysis

## Overview

The CryoGuard frontend is a React-based web application using TypeScript, React Router, and Tailwind CSS. It provides a dashboard interface for monitoring cold chain logistics containers.

## Page Structure

### 1. DashboardHome (`/dashboard`)
**Purpose**: Main overview page with KPIs, map placeholder, and container table

**Functionality**:
- KPI cards showing: Active Containers (12), Critical Alerts (2), Offline Devices (1), Average Temperature (5.4°C)
- Real-time map placeholder (OpenStreetMap integration planned)
- Live telemetry chart (temperature/humidity over time)
- Active containers table with: ID, Temperature, Humidity, Status, GPS, Last Update, Actions

**User Stories Covered**: US11 (dashboard with map), US04 (GPS location), US01/US02 (temperature/humidity monitoring)

**API Endpoints (Expected)**:
- `GET /envios/activos` - active containers with status
- `GET /telemetry/realtime` - live sensor data
- WebSocket or polling for real-time updates

**Status**: ⚠️ Mock data - UI structure complete but no real API integration

---

### 2. Monitoring (`/dashboard/monitoring`)
**Purpose**: Real-time operational monitoring of single container

**Functionality**:
- Sensor panel: Temperature (°C), Humidity (%), Vibration (g), GPS status, Door status
- Live graphs for temperature and humidity with 5-second updates
- Real-time event feed showing system logs
- Toggle between Live/Paused modes

**User Stories Covered**: US01 (temperature), US02 (humidity), US03 (vibration), US04 (GPS)

**API Endpoints (Expected)**:
- `GET /sensors/realtime` - current sensor readings
- `GET /logs?type=eventos` - real-time event feed

**Status**: ⚠️ Mock data with simulated real-time updates

---

### 3. Containers (`/dashboard/containers`)
**Purpose**: Container/device management and overview

**Functionality**:
- Search and filter containers
- Grid view of containers with: name, device ID, status, temp, humidity, battery, GPS, operator
- Add Container modal with form fields
- Actions: View Details, Edit, Delete

**User Stories Covered**: US14 (user management partial - operators listed), US20 (product type config partial)

**API Endpoints (Expected)**:
- `GET /contenedores` - list all containers
- `POST /contenedores` - create new container
- `PUT /contenedores/{id}` - update container
- `DELETE /contenedores/{id}` - delete container

**Status**: ⚠️ Mock data - CRUD UI present but not connected to API

---

### 4. Alerts (`/dashboard/alerts`)
**Purpose**: Alert and incident management

**Functionality**:
- Stats: Critical (2), Warnings (2), Open (2), Resolved (1)
- Search and filter by status
- Alert list with: ID, container, severity, type, message, timestamp, status, location
- Alert detail modal with: Acknowledge, Resolve, Escalate actions

**User Stories Covered**: US09 (geofence exit detection), US10 (unauthorized opening), TS02 (flag/alert management)

**API Endpoints (Expected)**:
- `GET /flags?estado=activo` - active flags/alerts
- `PUT /flags/{id}/confirmar` - acknowledge/resolve flag

**Status**: ⚠️ Mock data - UI complete but static

---

### 5. Routes (`/dashboard/routes`)
**Purpose**: GPS route management and geofencing

**Functionality**:
- Map placeholder showing active routes and geofences
- Tabs: Active Routes, Geofences
- Routes list with: name, container, status, start/end, distance, duration, checkpoints
- Geofences list with: name, type, status, coordinates
- Create Route / Create Geofence buttons

**User Stories Covered**: US04 (GPS tracking), US09 (geofence monitoring)

**API Endpoints (Expected)**:
- `GET /rutas` - list routes
- `POST /rutas` - create route
- `GET /geocercas` - list geofences
- `POST /geocercas` - create geofence

**Status**: ⚠️ Mock data - UI present, map placeholder only

---

### 6. Reports (`/dashboard/reports`)
**Purpose**: Report generation and export

**Functionality**:
- Generate New Report form with: Report Type (Telemetry/Alerts/Routes/Audit), Format (CSV/PDF/JSON/XLSX), Start/End Date, Container, Severity filter
- Recent reports table with: name, type, date range, format, size, download action

**User Stories Covered**: US12 (historical logs export), US17 (traceability reports for donors)

**API Endpoints (Expected)**:
- `POST /reportes/generar` - generate report
- `GET /reportes/{id}/descargar` - download report

**Status**: ⚠️ Mock data - form UI complete, export not functional

---

### 7. Users (`/dashboard/users`)
**Purpose**: User and role management

**Functionality**:
- Stats: Total Users (4), Administrators (1), Operators (3), Inactive (1)
- User search
- Users table with: avatar, name, email, role, status, last login, actions (Edit, Lock, Delete)
- Add User modal with: Full name, email, password, role (Operator/Administrator)

**User Stories Covered**: US14 (user and role management from dashboard)

**API Endpoints (Expected)**:
- `GET /admin/usuarios` - list users
- `POST /admin/usuarios` - create user
- `PUT /admin/usuarios/{id}` - update user
- `DELETE /admin/usuarios/{id}` - delete user

**Status**: ⚠️ Mock data - UI complete but static

---

### 8. AuditLogs (`/dashboard/audit-logs`)
**Purpose**: System activity audit trail

**Functionality**:
- Stats: Total Logs (8), Success (6), Failed (1), Modules (6)
- Search and filter by module
- Logs table with: timestamp, user, action, module, result, details

**User Stories Covered**: US12 (incident analysis), TS03 (audit log for user management)

**API Endpoints (Expected)**:
- `GET /logs?page=1&limit=50` - paginated audit logs
- `GET /logs?modulo=X` - filter by module

**Status**: ⚠️ Mock data - UI complete but static

---

### 9. Settings (`/dashboard/settings`)
**Purpose**: Global system configuration

**Functionality**:
- Monitoring Rules: Temperature range (min/max), Humidity range (min/max), Vibration threshold
- Notifications: Push notifications toggle, Email alerts toggle, FCM Server Key
- Synchronization: Retry attempts, Batch size
- Info box about offline operation (72 hours)

**User Stories Covered**: US20 (configure allowed ranges by product type)

**API Endpoints (Expected)**:
- `GET /configuracion` - get settings
- `PUT /configuracion` - update settings

**Status**: ⚠️ Mock data - form UI functional but no persistence

---

### 10. Login (`/login`)
**Purpose**: User authentication

**Functionality**:
- Email and password fields
- Remember me checkbox
- Forgot password link
- Demo credentials note
- Navigation back to landing page

**User Stories Covered**: TS12 (authentication), TS03 (user login)

**API Endpoints (Expected)**:
- `POST /auth/login` - authenticate user, return JWT

**Status**: ⚠️ Mock navigation - no actual API call

---

## Components

### UI Components (`/components/ui/`)
Shadcn/ui style components available:
- `button`, `input`, `select`, `checkbox`, `switch`
- `table`, `dialog`, `sheet`, `drawer`
- `tabs`, `accordion`, `collapsible`
- `chart`, `calendar`, `carousel`
- `badge`, `avatar`, `alert`, `alert-dialog`
- `tooltip`, `popover`, `menubar`, `navigation-menu`
- `form`, `label`, `textarea`
- And many more standard UI primitives

### Figma Components (`/components/figma/`)
- `ImageWithFallback` - image with fallback handling

---

## Layout Structure

### DashboardLayout
- Sidebar navigation with icons and labels
- Main content area
- Responsive design (mobile-friendly)

**Navigation Items**:
1. Dashboard (Home icon)
2. Containers (Package icon)
3. Monitoring (Activity icon)
4. Alerts (AlertTriangle icon)
5. Routes (Navigation icon)
6. Reports (FileText icon)
7. Users (Users icon)
8. Audit Logs (ScrollText icon)
9. Settings (Settings icon)

---

## Technical Stack

- **Framework**: React 18+ with TypeScript
- **Routing**: React Router v6
- **Styling**: Tailwind CSS
- **Icons**: Lucide React
- **Charts**: Recharts
- **State**: React hooks (useState, useEffect)
- **HTTP**: No visible API service layer (mocked data)

---

## API Service Layer

**Missing**: No dedicated API service files found (`/imports/` or `/services/`). All pages use hardcoded mock data.

**Expected API Services**:
- AuthService (login, logout, token management)
- ContainerService (CRUD operations)
- SensorService (real-time data)
- AlertService (flags management)
- ReportService (generation/export)
- UserService (management)

---

## Gaps Identified

1. **No API integration** - All pages use mock data
2. **No real-time updates** - WebSocket integration not implemented
3. **No authentication state management** - Login doesn't persist
4. **No mobile app** - Only web dashboard exists (EP09 mobile not implemented)
5. **No push notification integration** - Settings have FCM key field but no service
6. **Map is placeholder only** - OpenStreetMap not actually integrated
7. **No offline mode handling** - UI doesn't account for connectivity loss
8. **No override functionality UI** - US18/US19 not reflected in frontend