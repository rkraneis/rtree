package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.HasGeometry;
import de.rkraneis.rtree.geometry.ListPair;
import de.rkraneis.rtree.geometry.Rectangle;
import de.rkraneis.util.Pair;
import de.rkraneis.util.Preconditions;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class SplitterQuadratic implements Splitter {

    @SuppressWarnings("unchecked")
    @Override
    public <T extends HasGeometry> ListPair<T> split(List<T> items, int minSize) {
        Preconditions.checkArgument(items.size() >= 2);

        // according to
        // http://en.wikipedia.org/wiki/R-tree#Splitting_an_overflowing_node

        // find the worst combination pairwise in the list and use them to start
        // the two groups
        final Pair<T> worstCombination = worstCombination(items);

        // worst combination to have in the same node is now e1,e2.

        // establish a group around e1 and another group around e2
        final List<T> group1 = new ArrayList(asList(worstCombination.value1()));
        final List<T> group2 = new ArrayList(asList(worstCombination.value2()));

        final List<T> remaining = new ArrayList<>(items);
        remaining.remove(worstCombination.value1());
        remaining.remove(worstCombination.value2());

        final int minGroupSize = items.size() / 2;

        // now add the remainder to the groups using least mbr area increase
        // except in the case where minimumSize would be contradicted
        while (remaining.size() > 0) {
            assignRemaining(group1, group2, remaining, minGroupSize);
        }
        return new ListPair<>(group1, group2);
    }

    private <T extends HasGeometry> void assignRemaining(final List<T> group1,
            final List<T> group2, final List<T> remaining, final int minGroupSize) {
        final Rectangle mbr1 = Util.mbr(group1);
        final Rectangle mbr2 = Util.mbr(group2);
        final T item1 = getBestCandidateForGroup(remaining, group1, mbr1);
        final T item2 = getBestCandidateForGroup(remaining, group2, mbr2);
        final boolean area1LessThanArea2 = item1.geometry().mbr().add(mbr1).area() <= item2
                .geometry().mbr().add(mbr2).area();

        if (area1LessThanArea2 && (group2.size() + remaining.size() - 1 >= minGroupSize)
                || !area1LessThanArea2 && (group1.size() + remaining.size() == minGroupSize)) {
            group1.add(item1);
            remaining.remove(item1);
        } else {
            group2.add(item2);
            remaining.remove(item2);
        }
    }

    //@VisibleForTesting
    static <T extends HasGeometry> T getBestCandidateForGroup(List<T> list, List<T> group,
            Rectangle groupMbr) {
        Optional<T> minEntry = empty();
        Optional<Double> minArea = empty();
        for (final T entry : list) {
            final double area = groupMbr.add(entry.geometry().mbr()).area();
            if (!minArea.isPresent() || area < minArea.get()) {
                minArea = of(area);
                minEntry = of(entry);
            }
        }
        return minEntry.get();
    }

    //@VisibleForTesting
    static <T extends HasGeometry> Pair<T> worstCombination(List<T> items) {
        Optional<T> e1 = empty();
        Optional<T> e2 = empty();
        {
            Optional<Double> maxArea = empty();
            for (final T entry1 : items) {
                for (final T entry2 : items) {
                    if (entry1 != entry2) {
                        final double area = entry1.geometry().mbr().add(entry2.geometry().mbr())
                                .area();
                        if (!maxArea.isPresent() || area > maxArea.get()) {
                            e1 = of(entry1);
                            e2 = of(entry2);
                            maxArea = of(area);
                        }
                    }
                }
            }
        }
        if (e1.isPresent())
            return new Pair<>(e1.get(), e2.get());
        else
            // all items are the same item
            return new Pair<>(items.get(0), items.get(1));
    }
}