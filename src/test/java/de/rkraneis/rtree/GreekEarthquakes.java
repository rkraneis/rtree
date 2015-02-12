package de.rkraneis.rtree;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.GZIPInputStream;

import de.rkraneis.rtree.geometry.Geometries;
import de.rkraneis.rtree.geometry.Point;
import java.io.BufferedReader;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GreekEarthquakes {

    static Stream<Entry<Object, Point>> entries() {

        Stream<String> stream;

        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(
                    new GZIPInputStream(GreekEarthquakes.class
                            .getResourceAsStream("/greek-earthquakes-1964-2000.txt.gz"))));
            stream = in.lines();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stream.flatMap(line -> {
            if (line.trim().length() > 0) {
                String[] items = line.split(" ");
                double lat = Double.parseDouble(items[0]);
                double lon = Double.parseDouble(items[1]);
                return Stream.of(Entry.entry(new Object(),
                        Geometries.point(lat, lon)));
            } else {
                return Stream.empty();
            }
        });
    }

    static List<Entry<Object, Point>> entriesList() {
        return entries().collect(toList());
    }
}
