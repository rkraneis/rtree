package de.rkraneis.rtree;

import java.util.Collections;

import org.junit.Test;
import org.mockito.Mockito;

import rx.Subscriber;
import rx.functions.Func1;

import de.rkraneis.rtree.OnSubscribeSearch.SearchProducer;
import de.rkraneis.rtree.geometry.Geometries;
import de.rkraneis.rtree.geometry.Geometry;
import de.rkraneis.rtree.geometry.Point;
import de.rkraneis.rtree.geometry.Rectangle;

public class OnSubscribeSearchTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchProducerThrowsExceptionFromRequestAll() {
        Node<Integer, Geometry> node = Mockito.mock(Node.class);
        Func1<Geometry, Boolean> condition = Mockito.mock(Func1.class);
        Subscriber<Entry<Integer, Geometry>> subscriber = Mockito.mock(Subscriber.class);
        RuntimeException error = new RuntimeException();
        Mockito.doThrow(error).when(node).search(condition, subscriber);
        SearchProducer<Integer, Geometry> p = new OnSubscribeSearch.SearchProducer<Integer, Geometry>(
                node, condition, subscriber);
        p.request(Long.MAX_VALUE);
        Mockito.verify(subscriber).onError(error);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchProducerThrowsExceptionFromRequestSome() {
        Geometry g = new Geometry() {

            @Override
            public double distance(Rectangle r) {
                throw new RuntimeException("boo");
            }

            @Override
            public Rectangle mbr() {
                throw new RuntimeException("boo");
            }

            @Override
            public boolean intersects(Rectangle r) {
                throw new RuntimeException("boo");
            }};
        Node<Integer, Point> node = new Leaf<Integer, Point>(Collections.singletonList(Entry
                .entry(1, Geometries.point(1, 1))), null);
        
        Func1<Geometry, Boolean> condition = Mockito.mock(Func1.class);
        Subscriber<Entry<Integer, Point>> subscriber = new Subscriber<Entry<Integer, Point>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Entry<Integer, Point> t) {

            }
        };
        RuntimeException error = new RuntimeException();
        SearchProducer<Integer, Point> p = new OnSubscribeSearch.SearchProducer<Integer, Point>(
                node, condition, subscriber);
        p.request(1);
    }

}
