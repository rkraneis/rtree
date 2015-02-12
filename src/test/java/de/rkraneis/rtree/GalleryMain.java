package de.rkraneis.rtree;

import java.util.Arrays;
import java.util.List;

import de.rkraneis.rtree.geometry.Point;
import static java.util.stream.Collectors.toList;

public class GalleryMain {

    public static void main(String[] args) {
        List<Entry<Object, Point>> entries = GreekEarthquakes.entries().collect(toList());

        List<Integer> sizes = Arrays.asList(100, 1000, 10000, 1000000);
        List<Integer> maxChildrenValues = Arrays.asList(4, 8, 16, 32, 64, 128);
        for (int size : sizes)
            for (int maxChildren : maxChildrenValues) {
                if (size > maxChildren) {
                    System.out.println("saving " + size + " m=" + maxChildren);
                    RTree<Object, Point> tree = RTree.maxChildren(maxChildren)
                            .<Object, Point> create().add(entries.get(size-1));
                    tree.visualize(600, 600).save(
                            "target/greek-" + size + "-" + maxChildren + "-quad.png");
                    RTree<Object, Point> tree2 = RTree.star().maxChildren(maxChildren)
                            .<Object, Point> create().add(entries.get(size-1));
                    tree2.visualize(600, 600).save(
                            "target/greek-" + size + "-" + maxChildren + "-star.png");
                }
            }
    }
}
