package de.rkraneis.rtree;

import static de.rkraneis.rtree.Comparators.areaComparator;
import static de.rkraneis.rtree.Comparators.areaIncreaseComparator;
import static de.rkraneis.rtree.Comparators.compose;
import static de.rkraneis.rtree.Comparators.overlapAreaComparator;
import static java.util.Collections.min;

import java.util.List;

import de.rkraneis.rtree.geometry.Geometry;

public final class SelectorMinimalOverlapArea implements Selector {

    @SuppressWarnings("unchecked")
    @Override
    public <T, S extends Geometry> Node<T, S> select(Geometry g, List<? extends Node<T, S>> nodes) {
        return min(
                nodes,
                compose(overlapAreaComparator(g.mbr(), nodes), areaIncreaseComparator(g.mbr()),
                        areaComparator(g.mbr())));
    }

}
