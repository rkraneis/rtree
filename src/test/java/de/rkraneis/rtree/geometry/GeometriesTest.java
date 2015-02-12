package de.rkraneis.rtree.geometry;

import org.junit.Test;

import de.rkraneis.util.TestingUtil;

public class GeometriesTest {

    @Test
    public void testPrivateConstructorForCoverageOnly() {
        TestingUtil.callConstructorAndCheckIsPrivate(Geometries.class);
    }

}
