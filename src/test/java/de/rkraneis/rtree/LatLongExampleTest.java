package de.rkraneis.rtree;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.github.davidmoten.grumpy.core.Position;
import de.rkraneis.rtree.geometry.Geometries;
import de.rkraneis.rtree.geometry.Point;
import de.rkraneis.rtree.geometry.Rectangle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LatLongExampleTest {

    private static final Point sydney = Geometries.point(151.2094, -33.86);
    private static final Point canberra = Geometries.point(149.1244, -35.3075);
    private static final Point brisbane = Geometries.point(153.0278, -27.4679);
    private static final Point bungendore = Geometries.point(149.4500, -35.2500);

    @Test
    public void testLatLongExample() {

        // This is to demonstrate how to use rtree to to do distance searches
        // with Lat Long points

        // Let's find all cities within 300km of Canberra

        RTree<String, Point> tree = RTree.star().create();
        tree = tree.add("Sydney", sydney);
        tree = tree.add("Brisbane", brisbane);

        // Now search for all locations within 300km of Canberra
        final double distanceKm = 300;
        List<Entry<String, Point>> list = search(tree, canberra, distanceKm)
        // get the result
                .collect(Collectors.toList());

        // should have returned Sydney only
        assertEquals(1, list.size());
        assertEquals("Sydney", list.get(0).value());
    }

    public static <T> Stream<Entry<T, Point>> search(RTree<T, Point> tree, Point lonLat,
            final double distanceKm) {
        // First we need to calculate an enclosing lat long rectangle for this
        // distance then we refine on the exact distance
        final Position from = Position.create(lonLat.y(), lonLat.x());
        Rectangle bounds = createBounds(from, distanceKm);

        return tree
        // do the first search using the bounds
                .search(bounds)
                // refine using the exact distance
                .filter((Entry<T, Point> entry) -> {
                    Point p = entry.geometry();
                    Position position = Position.create(p.y(), p.x());
                    return from.getDistanceToKm(position) < distanceKm;
        });
    }

    @Test
    public void testSearchLatLongCircles() {
        RTree<GeoCircleValue<String>, Rectangle> tree = RTree.star().create();
        // create circles around these major towns
        GeoCircleValue<String> sydneyCircle = createGeoCircleValue(sydney, 100, "Sydney");
        GeoCircleValue<String> canberraCircle = createGeoCircleValue(canberra, 50, "Canberra");
        GeoCircleValue<String> brisbaneCircle = createGeoCircleValue(brisbane, 200, "Brisbane");

        // add the circles to the RTree using the bounding box of the circle as
        // the geometry
        tree = add(tree, sydneyCircle);
        tree = add(tree, canberraCircle);
        tree = add(tree, brisbaneCircle);

        // now find the circles that contain bungendore (which is 30km from
        // Canberra)
        final Point location = bungendore;
        String result = tree.search(location)
        // filter on the exact distance from the centre of the GeoCircle
                .filter(new Predicate<Entry<GeoCircleValue<String>, Rectangle>>() {
                    Position from = Position.create(location.y(), location.x());

                    @Override
                    public boolean test(Entry<GeoCircleValue<String>, Rectangle> entry) {
                        Position centre = Position.create(entry.value().lat, entry.value().lon);
                        return from.getDistanceToKm(centre) < entry.value().radiusKm;
                    }
                })
                // do the search (only expect one value)
                .findAny().get()
                // get the name of the GoeCircleValue returned
                .value().value;
        assertEquals("Canberra", result);
    }

    private static Rectangle createBounds(final Position from, final double distanceKm) {
        // this calculates a pretty accurate bounding box. Depending on the
        // performance you require you wouldn't have to be this accurate because
        // accuracy is enforced later
        Position north = from.predict(distanceKm, 0);
        Position south = from.predict(distanceKm, 180);
        Position east = from.predict(distanceKm, 90);
        Position west = from.predict(distanceKm, 270);

        return Geometries.rectangle(west.getLon(), south.getLat(), east.getLon(), north.getLat());
    }

    private static <T> GeoCircleValue<T> createGeoCircleValue(Point point, double radiusKm, T value) {
        return new GeoCircleValue<T>(point.y(), point.x(), radiusKm, value);
    }

    private static <T> RTree<GeoCircleValue<T>, Rectangle> add(
            RTree<GeoCircleValue<T>, Rectangle> tree, GeoCircleValue<T> c) {
        return tree.add(c, createBounds(Position.create(c.lat, c.lon), c.radiusKm));
    }

    private static class GeoCircleValue<T> {

        GeoCircleValue(float lat, float lon, double radiusKm, T value) {
            this.lat = lat;
            this.lon = lon;
            this.radiusKm = radiusKm;
            this.value = value;
        }

        float lat;
        float lon;
        double radiusKm;
        T value;
    }
}
