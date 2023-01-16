package fr.ludovicans.lanslib.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Call it when someone try to use an uninstalled dependency.
 */
@SuppressWarnings("unused")
public final class DependencyError extends Exception {

    /**
     * @param message to specify wich dependency is missing to use the function/method.
     */
    public DependencyError(final @NotNull String message) {
        super(message);
    }
}
