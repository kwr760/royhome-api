package net.royhome.tracker.repository

import java.util.UUID
import net.royhome.tracker.model.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : JpaRepository<Activity, UUID>
