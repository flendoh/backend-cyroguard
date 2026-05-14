# Skill Registry

**Delegator use only.** Any agent that launches sub-agents reads this registry to resolve compact rules, then injects them directly into sub-agent prompts. Sub-agents do NOT read this registry or individual SKILL.md files.

See `_shared/skill-resolver.md` for the full resolution protocol.

## User Skills

| Trigger | Skill | Path |
|---------|-------|------|
| DDD context, bounded context, nuevo contexto | ddd-context-scaffold | C:\Users\meteg\.config\opencode\skills\ddd-context-scaffold\SKILL.md |
| create a new skill, add agent instructions | skill-creator | C:\Users\meteg\.config\opencode\skills\skill-creator\SKILL.md |
| judgment day, review adversarial, dual review | judgment-day | C:\Users\meteg\.config\opencode\skills\judgment-day\SKILL.md |
| go testing, bubbles tea, teatest | go-testing | C:\Users\meteg\.config\opencode\skills\go-testing\SKILL.md |
| find a skill, skill for X, how do I do X | find-skills | C:\Users\meteg\.agents\skills\find-skills\SKILL.md |
| update skills, skill registry | skill-registry | C:\Users\meteg\.config\opencode\skills\skill-registry\SKILL.md |

## Compact Rules

### ddd-context-scaffold
- Generate new bounded contexts with full CQRS structure
- Use directory: `src/main/java/com/example/cryoguard/{context}/`
- Include: domain/aggregates, domain/valueobjects, domain/commands, domain/queries, application/services, infrastructure/persistence, presentation/controllers
- Follow DDD naming: aggregates end with Aggregate root, value objects are immutable, commands are past tense, queries are present/future tense
- Generate application layer services that orchestrate between aggregate roots and repositories

### judgment-day
- Launch two independent sub-agents to review same target blindly
- Synthesize findings, apply fixes, re-judge until both pass or escalate after 2 iterations
- Use for critical changes or high-risk refactoring

### go-testing
- Use teatest for Bubbletea TUI testing patterns
- Follow RED-GREEN-REFACTOR cycle
- Mock external dependencies with interfaces

### skill-registry
- Scan user skills: glob `*/SKILL.md` across global and project skill directories
- Skip `sdd-*`, `_shared`, `skill-registry`
- Generate 5-15 line compact rules per skill
- Always write `.atl/skill-registry.md` + save to engram if available

## Project Conventions

| File | Path | Notes |
|------|------|-------|
| AGENTS.md | C:\Users\meteg\.config\opencode\AGENTS.md | Orchestrator instructions, SDD workflow |
| opencode.json | C:\Users\meteg\IdeaProjects\CryoGuard\opencode.json | Project config |

## SDD Workflow Phases

| Phase | Default Model | Reads | Writes |
|-------|---------------|-------|--------|
| sdd-init | sonnet | - | config.yaml, specs/, changes/ |
| sdd-explore | sonnet | - | exploration.md |
| sdd-propose | opus | exploration (optional) | proposal.md |
| sdd-spec | sonnet | proposal | specs/{domain}/ |
| sdd-design | opus | proposal | design.md |
| sdd-tasks | sonnet | spec + design | tasks.md |
| sdd-apply | sonnet | tasks + spec + design | apply-progress |
| sdd-verify | sonnet | spec + tasks | verify-report.md |
| sdd-archive | haiku | all artifacts | archive-report |

## Bounded Contexts to Implement

| Context | Purpose |
|---------|---------|
| iam | Users, roles, permissions, authentication |
| monitoring | Sensors, telemetry, container state |
| evaluation | Flags, rules, alerts |
| logistics | Routes, geofences, fleet management |
| actuators | LED, buzzer, Peltier, servo control |
| operations | Override requests, use/transfer/discard |
| notifications | Push, email, SMS |
| audit | Security logs, traceability |