package de.rkraneis.rtree.geometry;

import static de.rkraneis.rtree.geometry.Geometries.circle;
import static de.rkraneis.rtree.geometry.Geometries.rectangle;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.rkraneis.util.TestingUtil;

public class IntersectsTest {

    @Test
    public void testConstructorIsPrivate() {
        TestingUtil.callConstructorAndCheckIsPrivate(Intersects.class);
    }
    
    @Test
    public void testRectangleIntersectsCircle() {
        assertTrue(Intersects.rectangleIntersectsCircle.call(rectangle(0, 0, 0, 0), circle(0, 0, 1)));
    }
    
    @Test
    public void testRectangleDoesNotIntersectCircle() {
        assertFalse(Intersects.rectangleIntersectsCircle.call(rectangle(0, 0, 0, 0), circle(100, 100, 1)));
    }
    
}
