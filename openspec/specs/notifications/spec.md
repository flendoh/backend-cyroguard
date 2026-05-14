# Notifications Specification

## Purpose

Handles alert notifications (Push, Email, SMS) for CryoGuard. Provides user preference management and internal APIs for other bounded contexts to trigger notifications.

## Data Model

### Notification
| Field | Type | Description |
|-------|------|-------------|
| id | string | Unique identifier (UUID) |
| userId | string | Recipient user ID |
| containerId | string (optional) | Related container |
| alertId | string (optional) | Related alert/flag |
| type | enum | PUSH, EMAIL, SMS |
| title | string | Notification title |
| body | string | Notification body |
| read | boolean | Read status |
| createdAt | string | ISO 8601 timestamp |

### NotificationPreference
| Field | Type | Description |
|-------|------|-------------|
| userId | string | User ID (unique) |
| pushEnabled | boolean | Push notifications enabled |
| emailEnabled | boolean | Email alerts enabled |
| smsEnabled | boolean | SMS alerts enabled |
| fcmToken | string (optional) | FCM device token |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | /notifications | List user's notifications (paginated) |
| PUT | /notifications/{id}/read | Mark notification as read |
| GET | /notifications/preferences | Get user notification preferences |
| PUT | /notifications/preferences | Update user notification preferences |
| POST | /notifications/send | Internal: send notification (called by other contexts) |

## Requirements

### Requirement: List Notifications

The system MUST return paginated notifications for the authenticated user, ordered by `createdAt` descending.

#### Scenario: User retrieves notification list

- GIVEN the user is authenticated with userId "user-123"
- WHEN the user requests GET /notifications?page=1&limit=20
- THEN the system SHALL return notifications where userId equals "user-123"
- AND the results SHALL be paginated with page and limit parameters
- AND each notification SHALL include id, type, title, body, read, createdAt

#### Scenario: Empty notification list

- GIVEN the user has no notifications
- WHEN the user requests GET /notifications
- THEN the system SHALL return an empty list with pagination metadata

---

### Requirement: Mark Notification as Read

The system MUST allow users to mark their own notifications as read.

#### Scenario: Mark single notification read

- GIVEN the user owns notification "notif-456"
- WHEN the user requests PUT /notifications/notif-456/read
- THEN the system SHALL set read to true for notification "notif-456"
- AND the system SHALL return HTTP 200 with updated notification

#### Scenario: Notification not found

- GIVEN the user requests PUT /notifications/nonexistent/read
- THEN the system SHALL return HTTP 404

#### Scenario: Unauthorized access

- GIVEN notification "notif-789" belongs to user "user-other"
- WHEN user "user-123" requests PUT /notifications/notif-789/read
- THEN the system SHALL return HTTP 403

---

### Requirement: Manage Notification Preferences

The system MUST allow users to view and update their notification preferences.

#### Scenario: Get preferences

- GIVEN the user is authenticated
- WHEN the user requests GET /notifications/preferences
- THEN the system SHALL return NotificationPreference for that user
- AND if no preference exists, the system SHALL return defaults (all channels disabled)

#### Scenario: Update preferences

- GIVEN the user provides { pushEnabled: true, emailEnabled: true, smsEnabled: false, fcmToken: "token123" }
- WHEN the user requests PUT /notifications/preferences
- THEN the system SHALL persist the preferences
- AND the system SHALL return HTTP 200 with updated preferences

---

### Requirement: Send Notification (Internal)

The system MUST provide an internal endpoint for other bounded contexts to send notifications.

#### Scenario: Evaluation context triggers alert notification

- GIVEN an alert was created for container "cont-001" with severity "CRITICAL"
- WHEN the evaluation context calls POST /notifications/send with { userId, containerId, alertId, type: "PUSH", title, body }
- THEN the system SHALL create a Notification record
- AND the system SHALL dispatch via the specified channel (PUSH/EMAIL/SMS)
- AND the system SHALL return HTTP 201 with the created notification

#### Scenario: FCM push delivery

- GIVEN pushEnabled is true and fcmToken is configured
- WHEN a PUSH notification is triggered
- THEN the system SHALL send to FCM using the configured server key
- AND the system SHALL NOT block if FCM is unavailable (async)

---

## Channel Behavior

| Channel | Requirement |
|---------|-------------|
| PUSH | Requires fcmToken. Sends via FCM when available. |
| EMAIL | Requires emailEnabled. Sends via configured SMTP. |
| SMS | Requires smsEnabled. Sends via configured SMS gateway. |

## Configuration

The system SHALL use application configuration for:
- FCM server key (for push)
- SMTP settings (for email)
- SMS gateway credentials