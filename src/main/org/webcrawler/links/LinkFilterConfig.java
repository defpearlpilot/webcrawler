package org.webcrawler.links;


import java.net.URI;


/**
 * Created by andrew on 2/19/17.
 */
public class LinkFilterConfig implements FilterConfig
{
    @Override
    public String getTagFilter()
    {
        return "link[href]";
    }


    @Override
    public String getLinkAttribute()
    {
        return "abs:href";
    }


    @Override
    public Link createLink(Domain domain, URI ref)
    {
        System.out.println("Creating link " + ref);
        return new Anchor(domain, ref);
    }


}
