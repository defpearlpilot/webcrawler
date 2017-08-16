package crawler.links;


import javaslang.control.Try;
import org.jsoup.nodes.Document;
import crawler.CrawlerSupport;
import crawler.JsoupSupport;

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
    protected final Collection<Link> links;


    public Link(Domain domain, URI link)
    {
        this.domain = domain;
        this.link = link;

        childAnchors = new LinkedList<>();
        mediaLinks = new LinkedList<>();
        scripts = new LinkedList<>();
        links = new LinkedList<>();
    }


    public abstract boolean isTraversable();


    public URI getLink()
    {
        return link;
    }


    public Collection<Link> getChildAnchors()
    {
        return childAnchors;
    }


    public Collection<Link> getMediaLinks()
    {
        return mediaLinks;
    }


    public Collection<Link> getScripts()
    {
        return scripts;
    }


    public Collection<Link> getChildLinks()
    {
        return links;
    }


    public void crawlSite()
    {
        if (!this.isTraversable())
        {
            return;
        }

        openBuffer().andThen(this::readLinks)
                    .andThen(() ->
                             {
                                 childAnchors.parallelStream()
                                             .filter(anchor -> domain.canTraverse(anchor.getLink()))
                                             .filter(anchor -> domain.canVisit(anchor.getLink()))
                                             .forEach(Link::crawlSite);

                                 links.parallelStream()
                                      .filter(anchor -> domain.canTraverse(anchor.getLink()))
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
        return JsoupSupport.connectBuffer(this.link);
    }


    private Try<Boolean> readLinks(Document doc)
    {
        return Try.of(() ->
                      {
                          childAnchors.addAll(CrawlerSupport.extractAnchors(doc, getDomain()));
                          scripts.addAll(CrawlerSupport.extractScripts(doc, getDomain()));
                          links.addAll(CrawlerSupport.extractLinks(doc, getDomain()));
                          mediaLinks.addAll(CrawlerSupport.extractImages(doc, getDomain()));
                          return true;
                      });
    }

}