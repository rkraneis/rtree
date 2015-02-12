package de.rkraneis.rtree;

import org.junit.Test;

import de.rkraneis.util.TestingUtil;

public class FunctionsTest {

    @Test
    public void testConstructorIsPrivate() {
        TestingUtil.callConstructorAndCheckIsPrivate(Functions.class);
    }
}
