package org.webcrawler;


import org.webcrawler.links.Domain;
import org.webcrawler.links.LinkSupport;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length == 1)
        {

        }

//        Domain google = new Domain("http://www.google.com");
//        google.crawlSite();


        LinkSupport.toURI("http://wiprodigital.com")
                   .map(Domain::new)
                   .andThen(Domain::crawlSite)
                   .andThen(Domain::showSiteMap);

        LinkSupport.toURI("http://reddit.com")
                   .map(Domain::new)
                   .andThen(Domain::crawlSite)
                   .andThen(Domain::showSiteMap);

    }
}
