# Implementation Tasks - CryoGuard Backend Refactoring

## Overview

This file contains all implementation tasks organized by bounded context.
For detailed task breakdowns, see the individual task files:
- `tasks-iam-monitoring.md` - IAM and Monitoring contexts
- `tasks-evaluation-logistics.md` - Evaluation and Logistics contexts
- `tasks-new-contexts.md` - NEW contexts (Actuators, Operations, Notifications, Audit)

---

## Priority Order for Implementation

Based on frontend dependencies:

| Priority | Bounded Context | Tasks File | Frontend Coverage |
|----------|-----------------|------------|-------------------|
| 1 | IAM | tasks-iam-monitoring.md | Login, Users page |
| 2 | Monitoring | tasks-iam-monitoring.md | Containers, Monitoring, Dashboard |
| 3 | Evaluation | tasks-evaluation-logistics.md | Alerts, Settings |
| 4 | Logistics | tasks-evaluation-logistics.md | Routes, Geofences |
| 5 | Audit | tasks-new-contexts.md | AuditLogs page |
| 6 | Actuators | tasks-new-contexts.md | No frontend UI yet |
| 7 | Operations | tasks-new-contexts.md | No frontend UI yet |
| 8 | Notifications | tasks-new-contexts.md | Settings only |

---

## Quick Reference - Task Counts

| Bounded Context | Tasks | Status |
|-----------------|-------|--------|
| IAM | ~20 | Partial (existing code) |
| Monitoring | ~25 | Partial (existing code) |
| Evaluation | ~20 | Partial (existing code) |
| Logistics | ~20 | Partial (existing code) |
| Actuators | ~27 | NEW |
| Operations | ~39 | NEW |
| Notifications | ~33 | NEW |
| Audit | ~25 | NEW |
| **Total** | **~209** | |

---

## Dependencies Between Contexts

```
AUDIT (no dependencies) ─────────┐
                                   │
OPERATIONS ───────────────────────┼──▶ ACTUATORS
                                   │
NOTIFICATIONS ─────────────────────┘
```

```
IAM ─────────▶ MONITORING ─────────▶ EVALUATION ─────────▶ NOTIFICATIONS
                  │                      │
                  │                      │
                  └──────▶ LOGISTICS ◀───┘
```

---

## Quick Start

### For existing contexts (IAM, Monitoring, Evaluation, Logistics):
1. Read the existing entity
2. Update field names/enums to match spec (lowercase)
3. Review/update controller
4. Ensure DTOs/assemblers match frontend

### For new contexts (Actuators, Operations, Notifications, Audit):
1. Create package structure
2. Create domain entities
3. Create repositories
4. Create service layer
5. Create controllers
6. Create DTOs/assemblers

---

## Verification Checklist

After implementing each context:
- [ ] All endpoints respond correctly
- [ ] Field names match frontend exactly
- [ ] Enum values are lowercase in JSON
- [ ] Pagination works
- [ ] Filtering works
- [ ] Tests pass

Run application with: `./mvnw spring-boot:run`
Check Swagger: http://localhost:8080/swagger-ui.html