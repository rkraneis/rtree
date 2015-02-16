package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.HasGeometry;
import de.rkraneis.rtree.geometry.ListPair;
import de.rkraneis.rtree.geometry.Rectangle;
import static java.lang.Float.compare;
import static java.lang.Double.compare;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

/**
 * Utility functions asociated with {@link Comparator}s, especially for use with
 * {@link Selector}s and {@link Splitter}s.
 * 
 */
public final class Comparators {

    private Comparators() {
        // prevent instantiation
    }

    public static final Comparator<ListPair<?>> overlapListPairComparator = 
            toComparator(Functions.overlapListPair);

    /**
     * Compares the sum of the areas of two ListPairs.
     */
    public static final Comparator<ListPair<?>> areaPairComparator = 
            (p1, p2) -> compare(p1.areaSum(), p2.areaSum());

    /**
     * Returns a {@link Comparator} that is a normal Double comparator for the
     * total of the areas of overlap of the members of the list with the
     * rectangle r.
     * 
     * @param <T>
     *            type of geometry being compared
     * @param r
     *            rectangle
     * @param list
     *            geometries to compare with the rectangle
     * @return the total of the areas of overlap of the geometries in the list
     *         with the rectangle r
     */
    public static <T extends HasGeometry> Comparator<HasGeometry> overlapAreaComparator(
            final Rectangle r, final List<T> list) {
        return toComparator(Functions.overlapArea(r, list));
    }

    public static <T extends HasGeometry> Comparator<HasGeometry> areaIncreaseComparator(
            final Rectangle r) {
        return toComparator(Functions.areaIncrease(r));
    }

    public static Comparator<HasGeometry> areaComparator(final Rectangle r) {
        return (g1, g2) -> compare(
                g1.geometry().mbr().add(r).area(),
                g2.geometry().mbr().add(r).area());
    }
    
    public static <R, T extends Comparable<Double>> Comparator<R> toComparator(final ToDoubleFunction<R> f) {
        return (r1, r2) -> compare(f.applyAsDouble(r1), f.applyAsDouble(r2));
    }

    public static <T> Comparator<T> compose(final Comparator<T>... comparators) {
        return (t1, t2) -> Stream.of(comparators)
                .mapToInt(c -> c.compare(t1, t2))
                .filter(v -> v != 0)
                .findFirst().orElse(0);
    }

    /**
     * <p>
     * Returns a comparator that can be used to sort entries returned by search
     * methods. For example:
     * </p>
     * <p>
     * <code>search(100).sorted(ascendingDistance(r))</code>
     * </p>
     * 
     * @param <T>
     *            the value type
     * @param <S>
     *            the entry type
     * @param r
     *            rectangle to measure distance to
     * @return a comparator to sort by ascending distance from the rectangle
     */
    public static <T, S extends Geometry> Comparator<Entry<T, S>> ascendingDistance(
            final Rectangle r) {
        return (e1, e2) -> compare(
                e1.geometry().distance(r),
                e2.geometry().distance(r));
    }

}