package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.HasGeometry;
import de.rkraneis.util.ObjectsHelper;
import de.rkraneis.util.Preconditions;
import java.util.Objects;
import java.util.Optional;

/**
 * An entry in the R-tree which has a spatial representation.
 * 
 * @param <T>
 *            the type of Entry
 */
public final class Entry<T, S extends Geometry> implements HasGeometry {
    private final T value;
    private final S geometry;

    /**
     * Constructor.
     * 
     * @param value
     *            the value of the entry
     * @param geometry
     *            the geometry of the value
     */
    public Entry(T value, S geometry) {
        Preconditions.checkNotNull(geometry);
        this.value = value;
        this.geometry = geometry;
    }

    /**
     * Factory method.
     * 
     * @param <T>
     *            type of value
     * @param <S>
     *            type of geometry
     * @param value
     *            object being given a spatial context
     * @param geometry
     *            geometry associated with the value
     * @return entry wrapping value and associated geometry
     */
    public static <T, S extends Geometry> Entry<T, S> entry(T value, S geometry) {
        return new Entry<>(value, geometry);
    }

    /**
     * Returns the value wrapped by this {@link Entry}.
     * 
     * @return the entry value
     */
    public T value() {
        return value;
    }

    @Override
    public S geometry() {
        return geometry;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Entry [value=");
        builder.append(value);
        builder.append(", geometry=");
        builder.append(geometry);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, geometry);
    }

    @Override
    public boolean equals(Object obj) {
        @SuppressWarnings("rawtypes")
        Optional<Entry> other = ObjectsHelper.asClass(obj, Entry.class);
        return other.map(entry -> Objects.equals(value, entry.value)
                && Objects.equals(geometry, entry.geometry))
                .orElse(false);
    }

}