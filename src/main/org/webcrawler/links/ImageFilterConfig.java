package org.webcrawler.links;


import java.net.URI;


/**
 * Created by andrew on 2/19/17.
 */
public class ImageFilterConfig implements FilterConfig
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
        System.out.println("Creating anchor " + ref);
        return new Anchor(domain, ref);
    }


}
