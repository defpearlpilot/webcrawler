package org.webcrawler.links;


import java.net.URI;



public class AnchorFilterConfig implements FilterConfig
{
    @Override
    public String getTagFilter()
    {
        return "a[href]";
    }


    @Override
    public String getLinkAttribute()
    {
        return "href";
    }


    @Override
    public Link createLink(Domain domain, URI ref)
    {
        return new Anchor(domain, ref);
    }


}
