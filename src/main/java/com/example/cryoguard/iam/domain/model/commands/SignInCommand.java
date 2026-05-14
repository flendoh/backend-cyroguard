package com.example.cryoguard.iam.domain.model.commands;

/**
 * Sign in command
 * <p>
 *     This class represents the command to sign in a user.
 * </p>
 * @param email the email of the user
 * @param password the password of the user
 */
public record SignInCommand(String email, String password) {
}