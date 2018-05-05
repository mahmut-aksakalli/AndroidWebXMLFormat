package hr.ferit.mahmutaksakalli.androidwebxmlformat.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class NewsInfo {

    @Element(name = "title") private String title;
    @Element(name = "category") private String category;
    @Element(name = "description") private String description;
    @Element(name = "pubDate") private String pubDate;
    @Element(name = "enclosure") private Enclosure enclosure;
    @Element(name = "link") private String link;


    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public String getLink() {
        return link;
    }
}

