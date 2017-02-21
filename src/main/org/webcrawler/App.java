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
//        Domain google = new Domain("http://www.google.com");
//        google.crawlSite();


        LinkSupport.toURI("http://wiprodigital.com")
                   .map(Domain::new)
                   .andThen(Domain::crawlSite)
                   .andThen(Domain::showSiteMap);

    }
}
