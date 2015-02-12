package de.rkraneis.util;

import java.util.Optional;
import static java.util.Optional.empty;

public final class ObjectsHelper {

    private ObjectsHelper() {
        // prevent instantiation
    }

    static void instantiateForTestCoveragePurposesOnly() {
        new ObjectsHelper();
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> asClass(Object object, Class<T> cls) {
        if (object == null)
            return empty();
        else if (object.getClass() != cls)
            return empty();
        else
            return Optional.of((T) object);
    }

}