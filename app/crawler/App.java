package crawler;


import crawler.links.Domain;
import crawler.links.LinkSupport;

import java.net.URI;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length == 0)
        {
            System.out.println("Expected an argument with a URL");
            return;
        }


        String url = args[0];

        LinkSupport.toURI(url)
                   .andThenTry(URI::toURL)
                   .map(Domain::new)
                   .andThen(Domain::crawlSite)
                   .andThen(Domain::showSiteMap)
                   .onFailure(error -> {
                       System.out.println("Failed to produce output: " + error.getMessage());
                   });
    }
}
