package org.webcrawler.links;


import javaslang.control.Try;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by andrew on 2/19/17.
 */
public class LinkSupport
{
    public static void printLink(int depth, Link link)
    {
        String padding = Stream.generate(() -> " ")
              .limit(depth)
              .collect(Collectors.joining());

        System.out.println(padding + link);
        link.getChildAnchors()
            .forEach(anchor ->
                     {
                         printLink(depth + 2, anchor);
                     });
    }


    public static Try<URI> toURI(String uri)
    {
        return Try.of(() -> new URI(uri));
    }
}
