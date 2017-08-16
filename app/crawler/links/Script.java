package crawler.links;


import java.net.URI;


public class Script extends Link
{
    public Script(Domain domain, URI link)
    {
        super(domain, link);
    }


    @Override
    public boolean isTraversable()
    {
        return false;
    }


    @Override
    public String toString()
    {
        return "Script(" + link + ")";
    }
}
