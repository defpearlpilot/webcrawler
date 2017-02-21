package org.webcrawler.links;


import javaslang.control.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.webcrawler.CrawlerSupport;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;


public abstract class Link
{
    protected final Domain domain;
    protected final URI link;

    protected final Collection<Link> childAnchors;
    protected final Collection<Link> mediaLinks;
    protected final Collection<Link> scripts;


    public Link(Domain domain, URI link)
    {
        this.domain = domain;
        this.link = link;

        childAnchors = new LinkedList<>();
        mediaLinks = new LinkedList<>();
        scripts = new LinkedList<>();
    }


    public URI getLink()
    {
        return link;
    }


    public Collection<Link> getChildAnchors()
    {
        return childAnchors;
    }


    public void crawlSite()
    {
        openBuffer().andThen(this::readLinks)
                    .andThen(() ->
                             {
                                 childAnchors.parallelStream()
                                             .filter(anchor -> domain.canVisit(anchor.getLink()))
                                             .forEach(Link::crawlSite);
                             });
    }


    public void showSiteMap()
    {
        LinkSupport.printLink(0, this);
    }


    protected Domain getDomain()
    {
        return this.domain;
    }


    private Try<Document> openBuffer()
    {
        return Try.of(() -> {
            String location = this.link.toURL().toString();
            return Jsoup.connect(location).get();
        });
    }


    private Try<Boolean> readLinks(Document doc)
    {
        return Try.of(() ->
                      {
                          childAnchors.addAll(CrawlerSupport.extractAnchors(doc, getDomain()));
                          mediaLinks.addAll(CrawlerSupport.extractLinks(doc, getDomain()));
                          return true;
                      });
    }

}