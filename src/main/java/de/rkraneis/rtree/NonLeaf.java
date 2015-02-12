package de.rkraneis.rtree;


import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.ListPair;
import de.rkraneis.rtree.geometry.Rectangle;
import de.rkraneis.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.of;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class NonLeaf<T, S extends Geometry> implements Node<T, S> {

    private final List<? extends Node<T, S>> children;
    private final Rectangle mbr;
    private final Context context;

    NonLeaf(List<? extends Node<T, S>> children, Context context) {
        Preconditions.checkArgument(!children.isEmpty());
        this.context = context;
        this.children = children;
        this.mbr = Util.mbr(children);
    }

    @Override
    public Geometry geometry() {
        return mbr;
    }

    @Override
    public Stream<Entry<T, S>> search(Predicate<? super Geometry> criterion) {

        if (!criterion.test(this.geometry().mbr()))
            return Stream.empty();

        return children.stream().flatMap(c -> c.search(criterion));
    }

    @Override
    public int count() {
        return children.size();
    }

    List<? extends Node<T, S>> children() {
        return children;
    }

    @Override
    public List<Node<T, S>> add(Entry<? extends T, ? extends S> entry) {
        final Node<T, S> child = context.selector().select(entry.geometry().mbr(), children);
        List<Node<T, S>> list = child.add(entry);
        List<? extends Node<T, S>> children2 = Util.replace(children, child, list);
        if (children2.size() <= context.maxChildren())
            return Collections.singletonList((Node<T, S>) new NonLeaf<T, S>(children2, context));
        else {
            ListPair<? extends Node<T, S>> pair = context.splitter().split(children2,
                    context.minChildren());
            return makeNonLeaves(pair);
        }
    }

    private List<Node<T, S>> makeNonLeaves(ListPair<? extends Node<T, S>> pair) {
        List<Node<T, S>> list = new ArrayList<Node<T, S>>();
        list.add(new NonLeaf<T, S>(pair.group1().list(), context));
        list.add(new NonLeaf<T, S>(pair.group2().list(), context));
        return list;
    }

    @Override
    public NodeAndEntries<T, S> delete(Entry<? extends T, ? extends S> entry, boolean all) {
        // the result of performing a delete of the given entry from this node
        // will be that zero or more entries will be needed to be added back to
        // the root of the tree (because num entries of their node fell below
        // minChildren),
        // zero or more children will need to be removed from this node,
        // zero or more nodes to be added as children to this node(because
        // entries have been deleted from them and they still have enough
        // members to be active)
        List<Entry<T, S>> addTheseEntries = new ArrayList<Entry<T, S>>();
        List<Node<T, S>> removeTheseNodes = new ArrayList<Node<T, S>>();
        List<Node<T, S>> addTheseNodes = new ArrayList<Node<T, S>>();
        int countDeleted = 0;

        for (final Node<T, S> child : children) {
            if (entry.geometry().intersects(child.geometry().mbr())) {
                final NodeAndEntries<T, S> result = child.delete(entry, all);
                if (result.node().isPresent()) {
                    if (result.node().get() != child) {
                        // deletion occurred and child is above minChildren so
                        // we update it
                        addTheseNodes.add(result.node().get());
                        removeTheseNodes.add(child);
                        addTheseEntries.addAll(result.entriesToAdd());
                        countDeleted += result.countDeleted();
                        if (!all)
                            break;
                    }
                    // else nothing was deleted from that child
                } else {
                    // deletion occurred and brought child below minChildren
                    // so we redistribute its entries
                    removeTheseNodes.add(child);
                    addTheseEntries.addAll(result.entriesToAdd());
                    countDeleted += result.countDeleted();
                    if (!all)
                        break;
                }
            }
        }
        if (removeTheseNodes.isEmpty())
            return new NodeAndEntries<T, S>(of(this), Collections.<Entry<T, S>> emptyList(), 0);
        else {
            List<Node<T, S>> nodes = Util.remove(children, removeTheseNodes);
            nodes.addAll(addTheseNodes);
            if (nodes.size() == 0)
                return new NodeAndEntries<T, S>(Optional.<Node<T, S>> empty(), addTheseEntries,
                        countDeleted);
            else {
                NonLeaf<T, S> node = new NonLeaf<T, S>(nodes, context);
                return new NodeAndEntries<T, S>(of(node), addTheseEntries, countDeleted);
            }
        }
    }
}