package com.example.cryoguard.iam.domain.model.commands;

/**
 * Disable user command
 * <p>
 *     This class represents the command to disable a user account.
 * </p>
 * @param userId the id of the user to disable
 */
public record DisableUserCommand(Long userId) {
}