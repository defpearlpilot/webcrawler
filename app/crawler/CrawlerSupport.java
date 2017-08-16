package crawler;


import crawler.links.*;
import javaslang.control.Try;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CrawlerSupport
{
    private static final FilterConfig ANCHOR_CONFIG = new AnchorFilterConfig();
    private static final FilterConfig LINK_CONFIG = new LinkFilterConfig();
    private static final FilterConfig IMAGE_CONFIG = new ImageFilterConfig();
    private static final FilterConfig SCRIPT_CONFIG = new ScriptFilterConfig();



    public static Collection<Link> extractAnchors( Document doc, Domain domain)
    {
        return extractFromDocument(ANCHOR_CONFIG, doc, domain).collect(Collectors.toList());
    }


    public static Collection<Link> extractLinks(Document doc, Domain domain)
    {
        return extractFromDocument(LINK_CONFIG, doc, domain).collect(Collectors.toList());
    }


    public static Collection<Link> extractImages(Document doc, Domain domain)
    {
        return extractFromDocument(IMAGE_CONFIG, doc, domain).collect(Collectors.toList());
    }


    public static Collection<Link> extractScripts(Document doc, Domain domain)
    {
        return extractFromDocument(SCRIPT_CONFIG, doc, domain).collect(Collectors.toList());
    }


    private static Stream<Link> extractFromDocument(FilterConfig config, Document doc, Domain domain)
    {
        Elements references = doc.select(config.getTagFilter());
        return processElement(config, domain, references);
    }


    private static Stream<Link> processElement( FilterConfig config, Domain domain, Elements links)
    {
        return StreamUtils.toParallelStream(links.iterator())
                          .map(elt -> elt.attr(config.getLinkAttribute()))
                          .map(LinkSupport::toURI)
                          .filter(Try::isSuccess)
                          .map(Try::get)
                          .filter(domain::visitURI)
                          .map(ref -> config.createLink(domain, ref));
    }

}
