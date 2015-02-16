package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.Rectangle;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import static java.lang.Integer.compare;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Visualizer {

    private final RTree<?, Geometry> tree;
    private final int width;
    private final int height;
    private final Rectangle view;
    private final int maxDepth;

    Visualizer(RTree<?, Geometry> tree, int width, int height, Rectangle view) {
        this.tree = tree;
        this.width = width;
        this.height = height;
        this.view = view;
        this.maxDepth = calculateMaxDepth(tree.root());
    }

    private static <R, S extends Geometry> int calculateMaxDepth(Optional<? extends Node<R, S>> root) {
        if (!root.isPresent())
            return 0;
        else
            return calculateDepth(root.get(), 0);
    }

    private static <R, S extends Geometry> int calculateDepth(Node<R, S> node, int depth) {
        if (node instanceof Leaf)
            return depth + 1;
        else
            return calculateDepth(((NonLeaf<R, S>) node).children().get(0), depth + 1);
    }

    public BufferedImage createImage() {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(Color.white);
        g.clearRect(0, 0, width, height);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

        if (tree.root().isPresent()) {
            final List<RectangleDepth> nodeDepths = getNodeDepthsSortedByDepth(tree.root().get());
            drawNode(g, nodeDepths);
        }
        return image;
    }

    private <T, S extends Geometry> List<RectangleDepth> getNodeDepthsSortedByDepth(Node<T, S> root) {
        final List<RectangleDepth> list = getRectangleDepths(root, 0);
        Collections.sort(list, (n1, n2) -> compare(n1.getDepth(), n2.getDepth()));
        return list;
    }

    private <T, S extends Geometry> List<RectangleDepth> getRectangleDepths(Node<T, S> node,
            int depth) {
        final List<RectangleDepth> list = new ArrayList<>();
        list.add(new RectangleDepth(node.geometry().mbr(), depth));
        if (node instanceof Leaf) {
            final Leaf<T, S> leaf = (Leaf<T, S>) node;
            leaf.entries().stream()
                    .map(entry -> new RectangleDepth(entry.geometry().mbr(), depth + 2))
                    .forEach(list::add);
        } else {
            final NonLeaf<T, S> n = (NonLeaf<T, S>) node;
            n.children().stream()
                    .map(child -> getRectangleDepths(child, depth + 1))
                    .forEach(list::addAll);
        }
        return list;
    }

    private void drawNode(Graphics2D g, List<RectangleDepth> nodes) {
        for (final RectangleDepth node : nodes) {
            final Color color = Color.getHSBColor(node.getDepth() / (maxDepth + 1f), 1f, 1f);
            g.setStroke(new BasicStroke(Math.max(0.5f, maxDepth - node.getDepth() + 1 - 1)));
            g.setColor(color);
            final Rectangle r = node.getRectangle();
            drawRectangle(g, r);
        }
    }

    private void drawRectangle(Graphics2D g, Rectangle r) {
        final double x1 = (r.x1() - view.x1()) / (view.x2() - view.x1()) * width;
        final double y1 = (r.y1() - view.y1()) / (view.y2() - view.y1()) * height;
        final double x2 = (r.x2() - view.x1()) / (view.x2() - view.x1()) * width;
        final double y2 = (r.y2() - view.y1()) / (view.y2() - view.y1()) * height;
        g.drawRect(rnd(x1), rnd(y1), rnd(x2 - x1), rnd(y2 - y1));
    }

    private static int rnd(double d) {
        return (int) Math.round(d);
    }

    public void save(File file, String imageFormat) {
        ImageSaver.save(createImage(), file, imageFormat);
    }

    public void save(String filename, String imageFormat) {
        save(new File(filename), imageFormat);
    }

    public void save(String filename) {
        save(new File(filename), "PNG");
    }
}