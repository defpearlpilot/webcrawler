package org.webcrawler;


import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class StreamUtils
{
    public static <T> Stream<T> toParallelStream(Iterator<T> iter)
    {
        Spliterator<T> spliter = Spliterators.spliteratorUnknownSize(iter, Spliterator.NONNULL);

        return StreamSupport.stream(spliter, true);
    }
}
