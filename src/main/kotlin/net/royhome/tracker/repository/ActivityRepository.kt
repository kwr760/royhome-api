package net.royhome.tracker.repository

import net.royhome.tracker.model.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActivityRepository : JpaRepository<Activity, UUID>
