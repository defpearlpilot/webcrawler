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
        return "href";
    }


    @Override
    public Link createLink(Domain domain, URI ref)
    {
        return new SourceLink(domain, ref);
    }


}
