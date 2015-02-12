package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.Geometry;

final class NodePosition<T, S extends Geometry> {

    private final Node<T, S> node;
    private final int position;

    NodePosition(Node<T, S> node, int position) {
        this.node = node;
        this.position = position;
    }

    Node<T, S> node() {
        return node;
    }

    int position() {
        return position;
    }

    NodePosition<T, S> nextPosition() {
        return new NodePosition<T, S>(node, position + 1);
    }

}
