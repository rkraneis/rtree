package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.HasGeometry;
import de.rkraneis.rtree.geometry.ListPair;
import de.rkraneis.util.Preconditions;
import static java.lang.Float.compare;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SplitterRStar implements Splitter {

    private final Comparator<ListPair<?>> comparator;

    @SuppressWarnings("unchecked")
    public SplitterRStar() {
        this.comparator = Comparators.compose(Comparators.overlapListPairComparator,
                Comparators.areaPairComparator);
    }

    @Override
    public <T extends HasGeometry> ListPair<T> split(List<T> items, int minSize) {
        Preconditions.checkArgument(!items.isEmpty());
        // sort nodes into increasing x, calculate min overlap where both groups
        // have more than minChildren

        Map<SortType, List<ListPair<T>>> map = new HashMap<>(5, 1.0f);
        map.put(SortType.X_LOWER, getPairs(minSize, sort(items, INCREASING_X_LOWER)));
        map.put(SortType.X_UPPER, getPairs(minSize, sort(items, INCREASING_X_UPPER)));
        map.put(SortType.Y_LOWER, getPairs(minSize, sort(items, INCREASING_Y_LOWER)));
        map.put(SortType.Y_UPPER, getPairs(minSize, sort(items, INCREASING_Y_UPPER)));

        // compute S the sum of all margin-values of the lists above
        // the list with the least S is then used to find minimum overlap

        SortType leastMarginSumSortType = Collections.min(sortTypes, marginSumComparator(map));
        List<ListPair<T>> pairs = map.get(leastMarginSumSortType);

        return Collections.min(pairs, comparator);
    }

    private static enum SortType {
        X_LOWER, X_UPPER, Y_LOWER, Y_UPPER;
    }

    private static final List<SortType> sortTypes = Collections.unmodifiableList(
            asList(SortType.values()));

    private static <T extends HasGeometry> Comparator<SortType> marginSumComparator(
            final Map<SortType, List<ListPair<T>>> map) {
        return Comparators.toComparator(sortType -> marginValueSum(map.get(sortType)));
    }

    private static <T extends HasGeometry> float marginValueSum(List<ListPair<T>> list) {
        return (float) list.stream().mapToDouble(ListPair::marginSum).sum();
    }

    private static <T extends HasGeometry> List<ListPair<T>> getPairs(int minSize, List<T> list) {
        List<ListPair<T>> pairs = new ArrayList<>(list.size() - 2 * minSize + 1);
        for (int i = minSize; i < list.size() - minSize; i++) {
            List<T> list1 = list.subList(0, i);
            List<T> list2 = list.subList(i, list.size());
            ListPair<T> pair = new ListPair<>(list1, list2);
            pairs.add(pair);
        }
        return pairs;
    }

    private static <T extends HasGeometry> List<T> sort(List<T> items,
            Comparator<HasGeometry> comparator) {
        ArrayList<T> list = new ArrayList<>(items);
        Collections.sort(list, comparator);
        return list;
    }

    private static Comparator<HasGeometry> INCREASING_X_LOWER = (n1, n2) -> 
            compare(n1.geometry().mbr().x1(), n2.geometry().mbr().x1());

    private static Comparator<HasGeometry> INCREASING_X_UPPER = (n1, n2) -> 
            compare(n1.geometry().mbr().x2(), n2.geometry().mbr().x2());

    private static Comparator<HasGeometry> INCREASING_Y_LOWER = (n1, n2) -> 
            compare(n1.geometry().mbr().y1(), n2.geometry().mbr().y1());

    private static Comparator<HasGeometry> INCREASING_Y_UPPER = (n1, n2) -> 
            compare(n1.geometry().mbr().y2(), n2.geometry().mbr().y2());

}