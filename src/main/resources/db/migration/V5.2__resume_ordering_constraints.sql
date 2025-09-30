-- Enforce ordering integrity: unique position within each parent scope and non-negative values.
-- This migration adds CHECK and UNIQUE constraints across ordered resume tables.

-- Pre-normalization step: ensure skill positions are gapless and zero-based per group to avoid
-- duplicate (group_id, position) pairs (e.g., legacy seed data with two entries at same position).
WITH resequenced AS (
    SELECT skill_id,
           ROW_NUMBER() OVER (PARTITION BY group_id ORDER BY position, skill_id) - 1 AS new_pos
    FROM resume.skill
)
UPDATE resume.skill s
SET position = r.new_pos
FROM resequenced r
WHERE s.skill_id = r.skill_id
  AND s.position IS DISTINCT FROM r.new_pos;

-- Education: positions per resume
ALTER TABLE resume.education
  ADD CONSTRAINT education_position_non_negative CHECK (position >= 0),
  ADD CONSTRAINT education_resume_position_unique UNIQUE (resume_id, position);

-- Experience: positions per resume
ALTER TABLE resume.experience
  ADD CONSTRAINT experience_position_non_negative CHECK (position >= 0),
  ADD CONSTRAINT experience_resume_position_unique UNIQUE (resume_id, position);

-- Experience description: positions per experience
ALTER TABLE resume.experience_description
  ADD CONSTRAINT experience_description_position_non_negative CHECK (position >= 0),
  ADD CONSTRAINT experience_description_experience_position_unique UNIQUE (experience_id, position);

-- Experience bullet: positions per experience
ALTER TABLE resume.experience_bullet
  ADD CONSTRAINT experience_bullet_position_non_negative CHECK (position >= 0),
  ADD CONSTRAINT experience_bullet_experience_position_unique UNIQUE (experience_id, position);

-- Skill group: positions per resume (top-level) and per experience (nested groups)
-- Use partial index approach would be cleaner, but for simplicity rely on two unique constraints with NULL allowance.
ALTER TABLE resume.skill_group
  ADD CONSTRAINT skill_group_position_non_negative CHECK (position >= 0);

-- Enforce uniqueness for top-level groups attached to resume
CREATE UNIQUE INDEX skill_group_resume_position_unique
  ON resume.skill_group(resume_id, position) WHERE resume_id IS NOT NULL;

-- Enforce uniqueness for experience-attached groups
CREATE UNIQUE INDEX skill_group_experience_position_unique
  ON resume.skill_group(experience_id, position) WHERE experience_id IS NOT NULL;

-- Skill: positions per skill_group
ALTER TABLE resume.skill
  ADD CONSTRAINT skill_position_non_negative CHECK (position >= 0),
  ADD CONSTRAINT skill_group_skill_position_unique UNIQUE (group_id, position);

-- Project: positions per resume
ALTER TABLE resume.project
  ADD CONSTRAINT project_position_non_negative CHECK (position >= 0),
  ADD CONSTRAINT project_resume_position_unique UNIQUE (resume_id, position);

-- Validation query (no-op in Flyway, documentation only): ensure no gaps or duplicates logically.
-- Future enhancement: add trigger to re-sequence on deletions if needed.