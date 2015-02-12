package de.rkraneis.rtree;

import org.junit.Test;

import de.rkraneis.util.TestingUtil;

public class ComparatorsTest {

    @Test
    public void testConstructorIsPrivate() {
        TestingUtil.callConstructorAndCheckIsPrivate(Comparators.class);
    }

}
