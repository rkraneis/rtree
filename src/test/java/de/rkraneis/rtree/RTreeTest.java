package de.rkraneis.rtree;

import static de.rkraneis.rtree.Entry.entry;
import static de.rkraneis.rtree.geometry.Geometries.circle;
import static de.rkraneis.rtree.geometry.Geometries.point;
import static de.rkraneis.rtree.geometry.Geometries.rectangle;
import static de.rkraneis.rtree.geometry.Intersects.pointIntersectsCircle;
import static de.rkraneis.rtree.geometry.Intersects.rectangleIntersectsCircle;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.rkraneis.rtree.geometry.Circle;
import de.rkraneis.rtree.geometry.Geometries;
import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.HasGeometry;
import de.rkraneis.rtree.geometry.Point;
import de.rkraneis.rtree.geometry.Rectangle;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

public class RTreeTest {

    private static final double PRECISION = 0.000001;

    @Test
    public void testInstantiation() {
        RTree<Object, Geometry> tree = RTree.create();
        assertFalse(tree.entries().findAny().isPresent());
    }

    @Test
    public void testSearchEmptyTree() {
        RTree<Object, Geometry> tree = RTree.create();
        assertFalse(tree.search(r(1)).findAny().isPresent());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchOnOneItem() {
        RTree<Object, Rectangle> tree = RTree.create();
        Entry<Object, Rectangle> entry = e(1);
        tree = tree.add(entry);
        assertEquals(Arrays.asList(entry), tree.search(r(1)).collect(toList()));
    }

    @Test
    public void testTreeWithOneItemIsNotEmpty() {
        RTree<Object, Geometry> tree = RTree.create().add(e(1));
        assertFalse(tree.isEmpty());
    }

    // @Test(expected = IOException.class)
    public void testSaveFileException() throws IOException {
        FileLock lock = null;
        RandomAccessFile file = null;
        try {
            String filename = "target/locked.png";
            File f = new File(filename);
            f.createNewFile();
            file = new RandomAccessFile(f, "rw");
            lock = file.getChannel().lock();
            RTree.create().visualize(600, 600).save(filename, "PNG");
        } finally {
            try {
                lock.release();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testVisualizerWithEmptyTree() {
        RTree<Object, Geometry> tree = RTree.create();
        tree.visualize(600, 600).save("target/tree.png", "PNG");
    }

    @Test
    public void testAddObservable() {
        Entry<Object, Rectangle> e1 = e(1);
        Entry<Object, Rectangle> e2 = e2(1);

        RTree<Object, Rectangle> tree = RTree.maxChildren(4).<Object, Rectangle> create().add(e1)
                .add(e2).delete(e1);
        RTree<Object, Rectangle> emptyTree = RTree.maxChildren(4).create();
        System.out.println(tree.entries().collect(toList()).toString());
        Stream<?> deletedtree = emptyTree.add(tree.entries());
        assertEquals(2, (int) deletedtree.count());
    }

    @Test
    public void testPerformanceAndEntriesCount() {

        long repeats = Long.parseLong(System.getProperty("r", "1"));
        long n = Long.parseLong(System.getProperty("n", "10000"));
        RTree<Object, Geometry> tree = null;
        while (--repeats >= 0) {
            long t = System.currentTimeMillis();
            tree = createRandomRTree(n);
            long diff = System.currentTimeMillis() - t;
            System.out.println("inserts/second = " + ((double) n / diff * 1000));
        }
        assertEquals(n, (int) tree.entries().count());

        long t = System.currentTimeMillis();
        Entry<Object, Geometry> entry = tree.search(rectangle(0, 0, 500, 500)).findFirst()
                .get();
        long diff = System.currentTimeMillis() - t;
        System.out.println("found " + entry);
        System.out.println("time to get nearest with " + n + " entries=" + diff);

    }

    @Test
    public void testSearchOfPoint() {
        Object value = new Object();
        RTree<Object, Geometry> tree = RTree.create().add(value, point(1, 1));
        List<Entry<Object, Geometry>> list = tree.search(point(1, 1)).collect(toList());
        assertEquals(1, list.size());
        assertEquals(value, list.get(0).value());
    }

    @Test
    public void testSearchOfPointWithinDistance() {
        Object value = new Object();
        RTree<Object, Geometry> tree = RTree.create().add(value, point(1, 1));
        List<Entry<Object, Geometry>> list = tree.search(point(1, 1), 2).collect(toList());
        assertEquals(1, list.size());
        assertEquals(value, list.get(0).value());
    }

    static List<Entry<Object, Geometry>> createRandomEntries(long n) {
        List<Entry<Object, Geometry>> list = new ArrayList<Entry<Object, Geometry>>();
        for (long i = 0; i < n; i++)
            list.add(randomEntry());
        return list;
    }

    static RTree<Object, Geometry> createRandomRTree(long n) {
        RTree<Object, Geometry> tree = RTree.maxChildren(4).create();
        for (long i = 0; i < n; i++) {
            Entry<Object, Geometry> entry = randomEntry();
            tree = tree.add(entry);
        }
        return tree;
    }

    static Entry<Object, Geometry> randomEntry() {
        return entry(new Object(), (Geometry) random());
    }

    @Test
    public void testDeleteWithGeometry() {
        RTree<Object, Rectangle> tree = RTree.maxChildren(4).create();
        Entry<Object, Rectangle> entry = e(1);
        Entry<Object, Rectangle> entry2 = e2(1);
        tree = tree.add(entry).add(entry2);

        tree = tree.delete(entry.value(), entry.geometry(), true);
        List<Entry<Object, Rectangle>> entries = tree.entries().collect(toList());
        assertTrue(entries.contains(entry2) && !entries.contains(entry));
    }

    @Test
    public void testDepthWith0() {
        RTree<Object, Geometry> tree = RTree.create();
        tree = tree.add(createRandomEntries(5));
        List<Entry<Object, Geometry>> entries = tree.entries().collect(toList());
        RTree<Object, Geometry> deletedTree = tree.delete(entries, true);
        assertTrue(deletedTree.isEmpty());
    }

    @Test
    public void testContext() {
        RTree<Object, Geometry> tree = RTree.create();
        assertNotNull(tree.context());
    }

    @Test
    public void testIterableDeletion() {
        RTree<Object, Rectangle> tree = RTree.create();
        Entry<Object, Rectangle> entry1 = e(1);
        Entry<Object, Rectangle> entry2 = e(2);
        Entry<Object, Rectangle> entry3 = e(3);
        tree = tree.add(entry1).add(entry2).add(entry3);

        List<Entry<Object, Rectangle>> list = new ArrayList<Entry<Object, Rectangle>>();
        list.add(entry1);
        list.add(entry3);
        RTree<Object, Rectangle> deletedTree = tree.delete(list);
        List<Entry<Object, Rectangle>> entries = deletedTree.entries().collect(toList());
        assertTrue(entries.contains(entry2) && !entries.contains(entry1)
                && !entries.contains(entry3));
    }

    @Test
    public void testObservableDeletion() {
        RTree<Object, Rectangle> tree = RTree.create();
        Entry<Object, Rectangle> entry1 = e(1);
        Entry<Object, Rectangle> entry2 = e(3);
        Entry<Object, Rectangle> entry3 = e(5);
        tree = tree.add(entry1).add(entry2).add(entry3);
        Stream<Entry<Object, Rectangle>> obs = tree.search(r(2), 5);
        List<RTree<Object, Rectangle>> deleted = tree.delete(obs, true).collect(toList());
        assertTrue(deleted.get(deleted.size() - 1) != null);
    }

    @Test
    public void testFullDeletion() {
        RTree<Object, Rectangle> tree = RTree.maxChildren(4).create();
        Entry<Object, Rectangle> entry = e(1);
        tree = tree.add(entry).add(entry);
        tree = tree.delete(entry, true);
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testPartialDeletion() {
        RTree<Object, Rectangle> tree = RTree.maxChildren(4).create();
        Entry<Object, Rectangle> entry = e(1);
        tree = tree.add(entry).add(entry);
        tree = tree.delete(entry, false);
        List<Entry<Object, Rectangle>> entries = tree.entries().collect(toList());
        int countEntries = (int) tree.entries().count();
        assertTrue(countEntries == 1);
        assertTrue(entries.get(0).equals(entry));
    }

    @Test
    public void testDepthWithMaxChildren3Entries1() {
        RTree<Object, Rectangle> tree = create(3, 1);
        assertEquals(1, tree.calculateDepth());
    }

    @Test
    public void testDepthWithMaxChildren3Entries2() {
        RTree<Object, Rectangle> tree = create(3, 2);
        assertEquals(1, tree.calculateDepth());
    }

    @Test
    public void testDepthWithMaxChildren3Entries3() {
        RTree<Object, Rectangle> tree = create(3, 3);
        assertEquals(1, tree.calculateDepth());
    }

    @Test
    public void testDepthWithMaxChildren3Entries4() {
        RTree<Object, Rectangle> tree = create(3, 4);
        assertEquals(2, tree.calculateDepth());
    }

    @Test
    public void testDepthWithMaxChildren3Entries8() {
        RTree<Object, Rectangle> tree = create(3, 8);
        tree.visualize(800, 800).save(new File("target/treeLittle.png"), "PNG");
        assertEquals(3, tree.calculateDepth());
    }

    @Test
    public void testDepthWithMaxChildren3Entries10() {
        RTree<Object, Rectangle> tree = create(3, 10);
        assertEquals(3, tree.calculateDepth());
    }

    @Test
    public void testSizeIsZeroIfTreeEmpty() {
        assertEquals(0, create(3, 0).size());
    }

    @Test
    public void testSizeIsOneIfTreeHasOneEntry() {
        assertEquals(1, create(3, 1).size());
    }

    @Test
    public void testSizeIsFiveIfTreeHasFiveEntries() {
        assertEquals(5, create(3, 5).size());
    }

    @Test
    public void testSizeAfterDelete() {
        Entry<Object, Rectangle> entry = e(1);
        RTree<Object, Rectangle> tree = create(3, 0).add(entry).add(entry).add(entry).delete(entry);
        assertEquals(2, tree.size());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeletionThatRemovesAllNodesChildren() {
        RTree<Object, Rectangle> tree = create(3, 8);
        tree = tree.add(e(10));
        // node children are now 1,2 and 3,4
        assertEquals(3, tree.calculateDepth());
        tree = tree.delete(e(10));
        // node children are now 1,2 and 3
        assertEquals(3, tree.calculateDepth());
        assertEquals(Sets.newHashSet(e(1), e(2), e(3), e(4), e(5), e(6), e(7), e(8)),
                Sets.newHashSet(tree.entries().collect(toList())));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteOfEntryThatDoesNotExistFromTreeOfOneEntry() {
        RTree<Object, Geometry> tree = RTree.create().add(e(1));
        tree = tree.delete(e(2));
        assertEquals(Lists.newArrayList(e(1)), tree.entries().collect(toList()));
    }

    @Test
    public void testDeleteFromEmptyTree() {
        RTree<Object, Geometry> tree = RTree.create();
        tree = tree.delete(e(2));
        assertEquals(0, (int) tree.entries().count());
    }

    @Test
    public void testBuilder1() {
        RTree<Object, Point> tree = RTree.minChildren(1).maxChildren(4)
                .selector(new SelectorMinimalAreaIncrease()).splitter(new SplitterQuadratic())
                .create();
        testBuiltTree(tree);
    }

    @Test
    public void testDeletionOfEntryThatDoesNotExistFromNonLeaf() {
        RTree<Object, Rectangle> tree = create(3, 100).delete(e(1000));
        assertEquals(100, (int) tree.entries().count());
    }

    @Test
    public void testBuilder2() {
        RTree<Object, Point> tree = RTree.selector(new SelectorMinimalAreaIncrease())
                .minChildren(1).maxChildren(4).splitter(new SplitterQuadratic()).create();
        testBuiltTree(tree);
    }

    @Test
    public void testBuilder3() {
        RTree<Object, Point> tree = RTree.maxChildren(4)
                .selector(new SelectorMinimalAreaIncrease()).minChildren(1)
                .splitter(new SplitterQuadratic()).create();
        testBuiltTree(tree);
    }

    @Test
    public void testBuilder4() {
        RTree<Object, Point> tree = RTree.splitter(new SplitterQuadratic()).maxChildren(4)
                .selector(new SelectorMinimalAreaIncrease()).minChildren(1).create();
        testBuiltTree(tree);
    }

    private void testBuiltTree(RTree<Object, Point> tree) {
        for (int i = 1; i <= 1000; i++) {
            tree = tree.add(i, Geometries.point(i, i));
        }
        assertEquals(1000, (int) tree.entries().count());
    }

    private static RTree<Object, Rectangle> create(int maxChildren, int n) {
        RTree<Object, Rectangle> tree = RTree.maxChildren(maxChildren).create();
        for (int i = 1; i <= n; i++)
            tree = tree.add(e(i));
        return tree;
    }

    @Test
    public void testNearestSameDirection() {
        RTree<Object, Rectangle> tree = RTree.maxChildren(4).<Object, Rectangle> create().add(e(1))
                .add(e(2)).add(e(3)).add(e(10)).add(e(11));
        List<Entry<Object, Rectangle>> list = tree.nearest(r(9), 10, 2).collect(toList());
        assertEquals(2, list.size());
        assertEquals(10, list.get(0).geometry().mbr().x1(), PRECISION);
        assertEquals(11, list.get(1).geometry().mbr().x1(), PRECISION);

        List<Entry<Object, Rectangle>> list2 = tree.nearest(r(10), 8, 3).collect(toList());
        assertEquals(2, list2.size());
        assertEquals(11, list2.get(0).geometry().mbr().x1(), PRECISION);
        assertEquals(10, list2.get(1).geometry().mbr().x1(), PRECISION);
    }

    @Test
    public void testNearestDifferentDirections() {
        RTree<Object, Geometry> tree = RTree.maxChildren(4).create().add(e(1)).add(e(2)).add(e(3))
                .add(e(9)).add(e(10));
        List<Entry<Object, Geometry>> list = tree.nearest(r(6), 10, 2).collect(toList());
        assertEquals(2, list.size());
        assertEquals(3, list.get(0).geometry().mbr().x1(), PRECISION);
        assertEquals(9, list.get(1).geometry().mbr().x1(), PRECISION);
    }

    @Test
    public void testNearestToAPoint() {
        Object value = new Object();
        RTree<Object, Geometry> tree = RTree.create().add(value, point(1, 1));
        List<Entry<Object, Geometry>> list = tree.nearest(point(2, 2), 3, 2).collect(toList());
        assertEquals(1, list.size());
        assertEquals(value, list.get(0).value());
    }

    @Test
    public void testVisualizer() {
        List<Entry<Object, Geometry>> entries = createRandomEntries(1000);
        int maxChildren = 8;
        RTree<Object, Geometry> tree = RTree.maxChildren(maxChildren).create().add(entries);
        tree.visualize(600, 600).save("target/tree.png");

        RTree<Object, Geometry> tree2 = RTree.star().maxChildren(maxChildren).create().add(entries);
        tree2.visualize(600, 600).save("target/tree2.png");
    }

    @Test(expected = RuntimeException.class)
    public void testSplitterRStarThrowsExceptionOnEmptyList() {
        SplitterRStar spl = new SplitterRStar();
        spl.split(Collections.<HasGeometry> emptyList(), 4);
    }

    @Test
    public void testVisualizerWithGreekData() {
        List<Entry<Object, Point>> entries = GreekEarthquakes.entriesList();
        int maxChildren = 8;
        RTree<Object, Point> tree = RTree.maxChildren(maxChildren).<Object, Point> create()
                .add(entries);
        tree.visualize(2000, 2000).save("target/greek.png");

        // do search
        System.out.println("found="
                + tree.search(Geometries.rectangle(40, 27.0, 40.5, 27.5)).count());

        RTree<Object, Point> tree2 = RTree.maxChildren(maxChildren).star().<Object, Point> create()
                .add(entries);
        tree2.visualize(2000, 2000).save("target/greek2.png");
    }

    @Test
    public void testDeleteOneFromOne() {
        Entry<Object, Rectangle> e1 = e(1);
        RTree<Object, Rectangle> tree = RTree.maxChildren(4).<Object, Rectangle> create().add(e1)
                .delete(e1);
        assertEquals(0, (int) tree.entries().count());
    }

    @Test
    public void testDeleteOneFromTreeWithDepthGreaterThanOne() {
        Entry<Object, Rectangle> e1 = e(1);
        RTree<Object, Rectangle> tree = RTree.maxChildren(4).<Object, Rectangle> create().add(e1)
                .add(e(2)).add(e(3)).add(e(4)).add(e(5)).add(e(6)).add(e(7)).add(e(8)).add(e(9))
                .add(e(10)).delete(e1);
        assertEquals(9, (int) tree.entries().count());
        assertFalse(tree.entries().anyMatch(e1::equals));
    }

    @Test
    public void testDeleteOneFromLargeTreeThenDeleteAllAndEnsureEmpty() {
        int n = 10000;
        RTree<Object, Geometry> tree = createRandomRTree(n).add(e(1)).add(e(2)).delete(e(1));
        assertEquals(n + 1, (int) tree.entries().count());
        assertFalse(tree.entries().anyMatch(e(1)::equals));
        assertTrue(tree.entries().anyMatch(e(2)::equals));
        n++;
        assertEquals(n, tree.size());

        for (Entry<Object, Geometry> entry : tree.entries().collect(toList())) { // XXX iterable from stream?
            tree = tree.delete(entry);
            n--;
            assertEquals(n, tree.size());
        }
        assertEquals(0, (int) tree.entries().count());
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testDeleteOnlyDeleteOneIfThereAreMoreThanMaxChildren() {
        Entry<Object, Rectangle> e1 = e(1);
        int count = (int) RTree.maxChildren(4).create().add(e1).add(e1).add(e1).add(e1).add(e1)
                .delete(e1).search(e1.geometry().mbr()).count();
        assertEquals(4, count);
    }

    @Test
    public void testDeleteAllIfThereAreMoreThanMaxChildren() {
        Entry<Object, Rectangle> e1 = e(1);
        int count = (int) RTree.maxChildren(4).create().add(e1).add(e1).add(e1).add(e1).add(e1)
                .delete(e1, true).search(e1.geometry().mbr()).count();
        assertEquals(0, count);
    }

    @Test
    public void testDeleteItemThatIsNotPresentDoesNothing() {
        Entry<Object, Rectangle> e1 = e(1);
        Entry<Object, Rectangle> e2 = e(2);
        RTree<Object, Rectangle> tree = RTree.<Object, Rectangle> create().add(e1);
        assertTrue(tree == tree.delete(e2));
    }

    @Test
    public void testExampleOnReadMe() {
        RTree<String, Geometry> tree = RTree.maxChildren(5).create();
        tree = tree.add(entry("DAVE", point(10, 20))).add(entry("FRED", point(12, 25)))
                .add(entry("MARY", point(97, 125)));
    }

    @Test(timeout = 2000)
    public void testUnsubscribe() {
        RTree<Object, Geometry> tree = createRandomRTree(1000);
        assertEquals(0, (int) tree.entries().limit(0).count());
    }

    @Test
    public void testSearchConditionAlwaysFalse() {
        @SuppressWarnings("unchecked")
        RTree<Object, Geometry> tree = (RTree<Object, Geometry>) (RTree<?, ?>) create(3, 3);
        assertEquals(0, (int) tree.search(alwaysFalse()).count());
    }

    private static <T> Predicate<T> alwaysFalse() {
        return t -> false;
    }
    
    @Test
    public void testAddOverload() {
        @SuppressWarnings("unchecked")
        RTree<Object, Geometry> tree = (RTree<Object, Geometry>) (RTree<?, ?>) create(3, 0);
        tree = tree.add(123, Geometries.point(1, 2));
        assertEquals(1, (int) tree.entries().count());
    }

    @Test
    public void testDeleteOverload() {
        @SuppressWarnings("unchecked")
        RTree<Object, Geometry> tree = (RTree<Object, Geometry>) (RTree<?, ?>) create(3, 0);
        tree = tree.add(123, Geometries.point(1, 2)).delete(123, Geometries.point(1, 2));
        assertEquals(0, (int) tree.entries().count());
    }

    @Test
    public void testStandardRTreeSearch() {
        Rectangle r = rectangle(13.0, 23.0, 50.0, 80.0);
        Point[] points = { point(59.0, 91.0), point(86.0, 14.0), point(36.0, 60.0),
                point(57.0, 36.0), point(14.0, 37.0) };

        RTree<Integer, Geometry> tree = RTree.create();
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            System.out.println("point(" + point.x() + "," + point.y() + "), value=" + (i + 1));
            tree = tree.add(i + 1, point);
        }
        System.out.println(tree.asString());
        System.out.println("searching " + r);
        Set<Integer> set = new HashSet<Integer>(tree.search(r).map(RTreeTest.<Integer> toValue())
                .collect(toList()));
        assertEquals(new HashSet<Integer>(asList(3, 5)), set);
    }

    @Test
    public void testStandardRTreeSearch2() {
        Rectangle r = rectangle(10.0, 10.0, 50.0, 50.0);
        Point[] points = { point(28.0, 19.0), point(29.0, 4.0), point(10.0, 63.0),
                point(34.0, 85.0), point(62.0, 45.0) };

        RTree<Integer, Geometry> tree = RTree.create();
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            System.out.println("point(" + point.x() + "," + point.y() + "), value=" + (i + 1));
            tree = tree.add(i + 1, point);
        }
        System.out.println(tree.asString());
        System.out.println("searching " + r);
        Set<Integer> set = new HashSet<Integer>(tree.search(r).map(RTreeTest.<Integer> toValue())
                .collect(toList()));
        assertEquals(new HashSet<Integer>(asList(1)), set);
    }

    @Test
    public void testStarTreeReturnsSameAsStandardRTree() {

        RTree<Integer, Geometry> tree1 = RTree.create();
        RTree<Integer, Geometry> tree2 = RTree.star().create();

        Rectangle[] testRects = { rectangle(0, 0, 0, 0), rectangle(0, 0, 100, 100),
                rectangle(0, 0, 10, 10), rectangle(0.12, 0.25, 50.356, 50.756),
                rectangle(1, 0.252, 50, 69.23), rectangle(13.12, 23.123, 50.45, 80.9),
                rectangle(10, 10, 50, 50) };

        for (int i = 1; i <= 10000; i++) {
            Point point = nextPoint();
            // System.out.println("point(" + point.x() + "," + point.y() +
            // "),");
            tree1 = tree1.add(i, point);
            tree2 = tree2.add(i, point);
        }

        for (Rectangle r : testRects) {
            Set<Integer> res1 = new HashSet<Integer>(tree1.search(r)
                    .map(RTreeTest.<Integer> toValue()).collect(toList()));
            Set<Integer> res2 = new HashSet<Integer>(tree2.search(r)
                    .map(RTreeTest.<Integer> toValue()).collect(toList()));
            // System.out.println("searchRect= rectangle(" + r.x1() + "," +
            // r.y1() + "," + r.x2() + "," + r.y2()+ ")");
            // System.out.println("res1.size=" + res1.size() + ",res2.size=" +
            // res2.size());
            // System.out.println("res1=" + res1 + ",res2=" + res2);
            assertEquals(res1.size(), res2.size());
        }
    }

    @Test
    public void testSearchWithIntersectsRectangleFunction() {
        RTree<Integer, Rectangle> tree = RTree.create();
        tree.search(circle(0, 0, 1), rectangleIntersectsCircle);
    }

    @Test
    public void testSearchWithIntersectsPointFunctionReturnsOne() {
        RTree<Integer, Point> tree = RTree.<Integer, Point> create().add(1, point(0, 0));
        Stream<Entry<Integer, Point>> entries = tree.search(circle(0, 0, 1),
                pointIntersectsCircle);
        assertEquals(1, (int) entries.count());
    }

    @Test
    public void testSearchWithIntersectsPointFunctionReturnsNone() {
        RTree<Integer, Point> tree = RTree.<Integer, Point> create().add(1, point(10, 10));
        Stream<Entry<Integer, Point>> entries = tree.search(circle(0, 0, 1),
                pointIntersectsCircle);
        assertEquals(0, (int) entries.count());
    }

    @Test
    public void testSearchWithDistanceFunctionIntersectsMbrButNotActualGeometry() {
        RTree<Integer, Point> tree = RTree.<Integer, Point> create().add(1, point(0, 0))
                .add(2, point(1, 1));

        Stream<Entry<Integer, Point>> entries = tree.search(circle(0, 0, 1), 0.1,
                distanceCircleToPoint);
        assertEquals(1, (int) entries.count());
    }

    @Test
    public void testSearchWithDistanceFunctionIntersectsMbrAndActualGeometry() {
        RTree<Integer, Point> tree = RTree.<Integer, Point> create().add(1, point(0, 0))
                .add(2, point(1, 1));

        Stream<Entry<Integer, Point>> entries = tree.search(circle(0, 0, 1), 0.5,
                distanceCircleToPoint);
        assertEquals(2, (int) entries.count());
    }

    @Test
    public void testSearchWithDistanceFunctionIntersectsNothing() {
        RTree<Integer, Point> tree = RTree.<Integer, Point> create().add(1, point(0, 0))
                .add(2, point(1, 1));

        Stream<Entry<Integer, Point>> entries = tree.search(circle(10, 10, 1), 0.5,
                distanceCircleToPoint);
        assertEquals(0, (int) entries.count());
    }

    @Test
    public void calculateDepthOfEmptyTree() {
        RTree<Object, Geometry> tree = RTree.create();
        assertEquals(0, tree.calculateDepth());
    }

    @Test
    public void calculateAsStringOfEmptyTree() {
        RTree<Object, Geometry> tree = RTree.create();
        assertEquals("", tree.asString());
    }

    private static ToDoubleBiFunction<Point, Circle> distanceCircleToPoint 
            = (Point point, Circle circle) -> circle.distance(point.mbr());

    private static <T> Function<Entry<T, ?>, T> toValue() {
        return entry -> entry.value();
    }

    private static Point nextPoint() {

        double randomX = Math.round(Math.random() * 100);

        double randomY = Math.round(Math.random() * 100);

        return Point.create(randomX, randomY);

    }

    static Entry<Object, Rectangle> e(int n) {
        return Entry.<Object, Rectangle> entry(n, r(n));
    }

    static Entry<Object, Rectangle> e2(int n) {
        return Entry.<Object, Rectangle> entry(n, r(n - 1));
    }

    private static Rectangle r(int n) {
        return rectangle(n, n, n + 1, n + 1);
    }

    private static Rectangle r(double n, double m) {
        return rectangle(n, m, n + 1, m + 1);
    }

    static Rectangle random() {
        return r(Math.random() * 1000, Math.random() * 1000);
    }
}
