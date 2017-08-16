package crawler.links;


import java.net.URI;


/**
 * Created by andrew on 2/19/17.
 */
public class ImageFilterConfig implements FilterConfig
{
    @Override
    public String getTagFilter()
    {
        return "img[src]";
    }


    @Override
    public String getLinkAttribute()
    {
        return "src";
    }


    @Override
    public Link createLink(Domain domain, URI ref)
    {
        return new Media(domain, ref);
    }


}
