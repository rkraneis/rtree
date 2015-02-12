package de.rkraneis.rtree;

import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.HasGeometry;
import de.rkraneis.rtree.geometry.Rectangle;

public class Mbr implements HasGeometry {

    private final Rectangle r;

    public Mbr(Rectangle r) {
        this.r = r;
    }

    @Override
    public Geometry geometry() {
        return r;
    }

}
