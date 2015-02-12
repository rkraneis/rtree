package de.rkraneis.rtree.geometry;

import java.util.function.BiPredicate;

public class Intersects {

    private Intersects() {
        // prevent instantiation
    }

    public static final BiPredicate<Rectangle, Circle> rectangleIntersectsCircle
            = (Rectangle rectangle, Circle circle) -> circle.intersects(rectangle);

    public static final BiPredicate<Point, Circle> pointIntersectsCircle
            = (Point point, Circle circle) -> circle.intersects(point);

}
