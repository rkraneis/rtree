package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.HasGeometry;
import de.rkraneis.rtree.geometry.ListPair;
import de.rkraneis.rtree.geometry.Rectangle;
import java.util.List;
import java.util.function.ToDoubleFunction;

/**
 * Utility functions for making {@link Selector}s and {@link Splitter}s.
 *
 */
public final class Functions {

    private Functions() {
        // prevent instantiation
    }

    public static final ToDoubleFunction<ListPair<? extends HasGeometry>> overlapListPair =

        pair -> pair.group1().geometry().mbr()
                    .intersectionArea(pair.group2().geometry().mbr());

    public static ToDoubleFunction<HasGeometry> overlapArea(final Rectangle r,
            final List<? extends HasGeometry> list) {
        return g -> {
            Rectangle gPlusR = g.geometry().mbr().add(r);
            return list.stream()
                    .filter(other -> other != g)
                    .map(HasGeometry::geometry).map(Geometry::mbr)
                    .mapToDouble(gPlusR::intersectionArea)
                    .sum();
        };
    }

    public static ToDoubleFunction<HasGeometry> areaIncrease(final Rectangle r) {
        return g -> {
            Rectangle gPlusR = g.geometry().mbr().add(r);
            return (double) (gPlusR.area() - g.geometry().mbr().area());
        };
    }

}