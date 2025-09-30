-- Normalize ordering columns from 1-based to 0-based indexing so Hibernate @OrderColumn
-- does not materialize an initial null element in lists. This keeps existing relative
-- ordering while aligning with Hibernate's zero-based expectations.

UPDATE resume.skill_group SET position = position - 1 WHERE position > 0;
UPDATE resume.education SET position = position - 1 WHERE position > 0;
UPDATE resume.experience SET position = position - 1 WHERE position > 0;
UPDATE resume.project SET position = position - 1 WHERE position > 0;
UPDATE resume.experience_description SET position = position - 1 WHERE position > 0;
UPDATE resume.experience_bullet SET position = position - 1 WHERE position > 0;
UPDATE resume.skill SET position = position - 1 WHERE position > 0;

-- (Optional) Future hardening (left commented until constraints explicitly desired):
-- ALTER TABLE resume.skill_group ADD CONSTRAINT skill_group_position_non_negative CHECK (position >= 0);
-- Repeat analogous constraints for other tables if needed.
