package de.rkraneis.rtree;

import static de.rkraneis.rtree.Comparators.areaComparator;
import static de.rkraneis.rtree.Comparators.areaIncreaseComparator;
import static de.rkraneis.rtree.Comparators.compose;
import de.rkraneis.rtree.geometry.Geometry;
import static java.util.Collections.min;

import java.util.List;

/**
 * Uses minimal area increase to select a node from a list.
 *
 */
public final class SelectorMinimalAreaIncrease implements Selector {

    @SuppressWarnings("unchecked")
    @Override
    public <T, S extends Geometry> Node<T, S> select(Geometry g, List<? extends Node<T, S>> nodes) {
        return min(nodes, compose(areaIncreaseComparator(g.mbr()), areaComparator(g.mbr())));
    }
}