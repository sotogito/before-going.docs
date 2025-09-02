```
@Modifying(clearAutomatically = true, flushAutomatically = true)
@Query(value = """
    UPDATE mission p
    LEFT JOIN mission c
      ON c.parent_mission_id = p.id
     AND c.use_date = :targetDate
    SET p.is_checked = COALESCE(c.is_checked, 0)
    WHERE p.use_date IS NULL
      AND p.mission_type = 'BASIC'
    """, nativeQuery = true)
int syncBasicIsCheckedFromChild(@Param("targetDate") LocalDate targetDate);

```