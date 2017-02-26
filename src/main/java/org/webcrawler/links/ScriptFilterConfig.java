package org.webcrawler.links;


import java.net.URI;


/**
 * Created by andrew on 2/19/17.
 */
public class ScriptFilterConfig implements FilterConfig
{
    @Override
    public String getTagFilter()
    {
        return "script[src]";
    }


    @Override
    public String getLinkAttribute()
    {
        return "src";
    }


    @Override
    public Link createLink(Domain domain, URI ref)
    {
        return new Script(domain, ref);
    }
}
