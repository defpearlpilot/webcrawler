package org.webcrawler.links;


import java.net.URI;


/**
 * Created by andrew on 2/19/17.
 */
public interface FilterConfig
{
    String getTagFilter();


    String getLinkAttribute();


    Link createLink(Domain domain, URI ref);
}
