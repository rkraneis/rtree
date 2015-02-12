package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.HasGeometry;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

interface Node<T, S extends Geometry> extends HasGeometry {

    List<Node<T, S>> add(Entry<? extends T, ? extends S> entry);

    NodeAndEntries<T, S> delete(Entry<? extends T, ? extends S> entry, boolean all);

    Stream<Entry<T, S>> search(Predicate<? super Geometry> condition);

    int count();

}