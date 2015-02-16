package de.rkraneis.rtree;

import static de.rkraneis.rtree.Utilities.entries1000;

import java.util.List;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import de.rkraneis.rtree.geometry.Geometries;
import de.rkraneis.rtree.geometry.Point;
import de.rkraneis.rtree.geometry.Rectangle;

@State(Scope.Benchmark)
public class BenchmarksRTree {

    private final List<Entry<Object, Point>> entries = GreekEarthquakes.entriesList();

    private final List<Entry<Object, Rectangle>> some = entries1000();

    private final RTree<Object, Point> defaultTreeM4 = RTree.maxChildren(4)
            .<Object, Point> create().add(entries);

    private final RTree<Object, Point> defaultTreeM10 = RTree.maxChildren(10)
            .<Object, Point> create().add(entries);

    private final RTree<Object, Point> starTreeM4 = RTree.maxChildren(4).star()
            .<Object, Point> create().add(entries);

    private final RTree<Object, Point> starTreeM10 = RTree.maxChildren(10).star()
            .<Object, Point> create().add(entries);

    private final RTree<Object, Point> defaultTreeM32 = RTree.maxChildren(32)
            .<Object, Point> create().add(entries);

    private final RTree<Object, Point> starTreeM32 = RTree.maxChildren(32).star()
            .<Object, Point> create().add(entries);

    private final RTree<Object, Point> defaultTreeM128 = RTree.maxChildren(128)
            .<Object, Point> create().add(entries);

    private final RTree<Object, Point> starTreeM128 = RTree.maxChildren(128).star()
            .<Object, Point> create().add(entries);

    private final RTree<Object, Rectangle> smallDefaultTreeM4 = RTree.maxChildren(4)
            .<Object, Rectangle> create().add(some);

    private final RTree<Object, Rectangle> smallDefaultTreeM10 = RTree.maxChildren(10)
            .<Object, Rectangle> create().add(some);

    private final RTree<Object, Rectangle> smallStarTreeM4 = RTree.maxChildren(4).star()
            .<Object, Rectangle> create().add(some);

    private final RTree<Object, Rectangle> smallStarTreeM10 = RTree.maxChildren(10).star()
            .<Object, Rectangle> create().add(some);

    private final RTree<Object, Rectangle> smallDefaultTreeM32 = RTree.maxChildren(32)
            .<Object, Rectangle> create().add(some);

    private final RTree<Object, Rectangle> smallStarTreeM32 = RTree.maxChildren(32).star()
            .<Object, Rectangle> create().add(some);

    private final RTree<Object, Rectangle> smallDefaultTreeM128 = RTree.maxChildren(128)
            .<Object, Rectangle> create().add(some);

    private final RTree<Object, Rectangle> smallStarTreeM128 = RTree.maxChildren(128).star()
            .<Object, Rectangle> create().add(some);

    @Benchmark
    public RTree<Object, Point> defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004() {
        return insertPoint(defaultTreeM4);
    }

    @Benchmark
    public long defaultRTreeSearchOfGreekDataPointsMaxChildren004() {
        return searchGreek(defaultTreeM4);
    }

    @Benchmark
    public RTree<Object, Point> defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010() {
        return insertPoint(defaultTreeM10);
    }

    @Benchmark
    public long defaultRTreeSearchOfGreekDataPointsMaxChildren010() {
        return searchGreek(defaultTreeM10);
    }

    @Benchmark
    public RTree<Object, Point> rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004() {
        return insertPoint(starTreeM4);
    }

    @Benchmark
    public RTree<Object, Point> rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010() {
        return insertPoint(starTreeM10);
    }

    @Benchmark
    public long rStarTreeSearchOfGreekDataPointsMaxChildren004() {
        return searchGreek(starTreeM4);
    }

    @Benchmark
    public long rStarTreeSearchOfGreekDataPointsMaxChildren010() {
        return searchGreek(starTreeM10);
    }

    @Benchmark
    public RTree<Object, Point> defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032() {
        return insertPoint(defaultTreeM32);
    }

    @Benchmark
    public long defaultRTreeSearchOfGreekDataPointsMaxChildren032() {
        return searchGreek(defaultTreeM32);
    }

    @Benchmark
    public RTree<Object, Point> rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032() {
        return insertPoint(starTreeM32);
    }

    @Benchmark
    public long rStarTreeSearchOfGreekDataPointsMaxChildren032() {
        return searchGreek(starTreeM32);
    }

    @Benchmark
    public RTree<Object, Point> defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128() {
        return insertPoint(defaultTreeM128);
    }

    @Benchmark
    public long defaultRTreeSearchOfGreekDataPointsMaxChildren128() {
        return searchGreek(defaultTreeM128);
    }

    @Benchmark
    public RTree<Object, Point> rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128() {
        return insertPoint(starTreeM128);
    }

    @Benchmark
    public long rStarTreeSearchOfGreekDataPointsMaxChildren128() {
        return searchGreek(starTreeM128);
    }

    @Benchmark
    public RTree<Object, Rectangle> defaultRTreeInsertOneEntryInto1000EntriesMaxChildren004() {
        return insertRectangle(smallDefaultTreeM4);
    }

    @Benchmark
    public long defaultRTreeSearchOf1000PointsMaxChildren004() {
        return search(smallDefaultTreeM4);
    }

    @Benchmark
    public RTree<Object, Rectangle> defaultRTreeInsertOneEntryInto1000EntriesMaxChildren010() {
        return insertRectangle(smallDefaultTreeM10);
    }

    @Benchmark
    public long defaultRTreeSearchOf1000PointsMaxChildren010() {
        return search(smallDefaultTreeM10);
    }

    @Benchmark
    public RTree<Object, Rectangle> rStarTreeInsertOneEntryInto1000EntriesMaxChildren004() {
        return insertRectangle(smallStarTreeM4);
    }

    @Benchmark
    public RTree<Object, Rectangle> rStarTreeInsertOneEntryInto1000EntriesMaxChildren010() {
        return insertRectangle(smallStarTreeM10);
    }

    @Benchmark
    public long rStarTreeSearchOf1000PointsMaxChildren004() {
        return search(smallStarTreeM4);
    }

    @Benchmark
    public long rStarTreeSearchOf1000PointsMaxChildren010() {
        return search(smallStarTreeM10);
    }

    @Benchmark
    public RTree<Object, Rectangle> defaultRTreeInsertOneEntryInto1000EntriesMaxChildren032() {
        return insertRectangle(smallDefaultTreeM32);
    }

    @Benchmark
    public long defaultRTreeSearchOf1000PointsMaxChildren032() {
        return search(smallDefaultTreeM32);
    }

    @Benchmark
    public RTree<Object, Rectangle> rStarTreeInsertOneEntryInto1000EntriesMaxChildren032() {
        return insertRectangle(smallStarTreeM32);
    }

    @Benchmark
    public long rStarTreeSearchOf1000PointsMaxChildren032() {
        return search(smallStarTreeM32);
    }

    @Benchmark
    public RTree<Object, Rectangle> defaultRTreeInsertOneEntryInto1000EntriesMaxChildren128() {
        return insertRectangle(smallDefaultTreeM128);
    }

    @Benchmark
    public long defaultRTreeSearchOf1000PointsMaxChildren128() {
        return search(smallDefaultTreeM128);
    }

    @Benchmark
    public RTree<Object, Rectangle> rStarTreeInsertOneEntryInto1000EntriesMaxChildren128() {
         return insertRectangle(smallStarTreeM128);
    }

    @Benchmark
    public long rStarTreeSearchOf1000PointsMaxChildren128() {
        return search(smallStarTreeM128);
    }

    @Benchmark
    public RTree<Object, Point> rStarTreeDeleteOneEveryOccurrenceFromGreekDataChildren010() {
         return deleteAll(starTreeM10);
    }

    private RTree<Object, Point> deleteAll(RTree<Object, Point> tree) {
        return tree.delete(entries.get(1000), true);
    }

    private long search(RTree<Object, Rectangle> tree) {
        // returns 10 results
        return tree.search(Geometries.rectangle(500, 500, 630, 630)).count();
    }

    private long searchGreek(RTree<Object, Point> tree) {
        // should return 22 results
        return tree.search(Geometries.rectangle(40, 27.0, 40.5, 27.5)).count();
    }

    private RTree<Object, Rectangle> insertRectangle(RTree<Object, Rectangle> tree) {
        return tree.add(new Object(), RTreeTest.random());
    }

    private RTree<Object, Point> insertPoint(RTree<Object, Point> tree) {
        return tree.add(new Object(), Geometries.point(Math.random() * 1000, Math.random() * 1000));
    }

    public static void main(String[] args) {
        BenchmarksRTree b = new BenchmarksRTree();
        while (true)
            b.searchGreek(b.starTreeM10);
    }
}
