package de.rkraneis.util;

import java.util.Objects;

/**
 *
 * @author René Kraneis
 */
public class Preconditions {

    public static void checkArgument(boolean assertion) {
        if (!assertion) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkNotNull(Object o) {
        Objects.requireNonNull(o);
    }

}
