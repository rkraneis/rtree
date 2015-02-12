package de.rkraneis.rtree.geometry;

import de.rkraneis.util.ObjectsHelper;
import java.util.Objects;
import java.util.Optional;

public final class Circle implements Geometry {

    private final float x, y, radius;
    private final Rectangle mbr;

    protected Circle(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mbr = Rectangle.create(x - radius, y - radius, x + radius, y + radius);
    }

    public static Circle create(double x, double y, double radius) {
        return new Circle((float) x, (float) y, (float) radius);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    @Override
    public Rectangle mbr() {
        return mbr;
    }

    @Override
    public double distance(Rectangle r) {
        return Math.max(0, new Point(x, y).distance(r) - radius);
    }

    @Override
    public boolean intersects(Rectangle r) {
        return distance(r) == 0;
    }

    public boolean intersects(Circle c) {
        double total = radius + c.radius;
        return new Point(x, y).distanceSquared(new Point(c.x, c.y)) <= total * total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, radius);
    }

    @Override
    public boolean equals(Object obj) {
        Optional<Circle> other = ObjectsHelper.asClass(obj, Circle.class);
        if (other.isPresent()) {
            return Objects.equals(x, other.get().x) && Objects.equals(y, other.get().y)
                    && Objects.equals(radius, other.get().radius);
        } else
            return false;
    }

    public boolean intersects(Point point) {
        return Math.sqrt(sqr(x - point.x()) + sqr(y - point.y())) <= radius;
    }

    private float sqr(float x) {
        return x * x;
    }
}