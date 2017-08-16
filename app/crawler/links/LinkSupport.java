package crawler.links;


import javaslang.control.Try;

import java.net.URI;
import java.net.URL;
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
        link.getMediaLinks()
            .forEach(media ->
                     {
                         printLink(depth + 2, media);
                     });

        link.getScripts()
            .forEach(media ->
                     {
                         printLink(depth + 2, media);
                     });

        link.getChildLinks()
            .forEach(childLink ->
                     {
                         printLink(depth + 2, childLink);
                     });

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


    public static Try<URL> toURL(String uri)
    {
        return Try.of(() -> new URL(uri));
    }
}
