package de.rkraneis.rtree;

import org.junit.Test;

import de.rkraneis.util.TestingUtil;

public class UtilTest {

    @Test
    public void coverPrivateConstructor() {
        TestingUtil.callConstructorAndCheckIsPrivate(Util.class);
    }

}
