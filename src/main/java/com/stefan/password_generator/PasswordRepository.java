package com.stefan.password_generator;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for password records.
 * JpaRepository provides common persistence operations for PasswordEntity rows,
 * whose primary keys are Long values.
 */
public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {
}
