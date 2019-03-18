package org.kohsuke.github;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kohsuke Kawaguchi
 * @see GHIssue#getLabels()
 * @see GHRepository#listLabels()
 */
public class GHLabel {
    private String url, name, color;
    private transient GHRepository repo;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    /**
     * Color code without leading '#', such as 'f29513'
     */
    public String getColor() {
        return color;
    }

    /*package*/ GHLabel wrapUp(GHRepository repo) {
        this.repo = repo;
        return this;
    }

    public void delete() throws IOException {
        repo.root.retrieve().method("DELETE").to(url);
    }

    /**
     * @param newColor
     *      6-letter hex color code, like "f29513"
     */
    public void setColor(String newColor) throws IOException {
        repo.root.retrieve().method("PATCH").with("name", name).with("color", newColor).to(url);
    }

    /*package*/ static Collection<String> toNames(Collection<GHLabel> labels) {
        List<String> r = new ArrayList<String>();
        for (GHLabel l : labels) {
            r.add(l.getName());
        }
        return r;
    }

    @Override
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
        value="EQ_GETCLASS_AND_CLASS_CONSTANT",
        justification="I know what I'm doing")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if  (   (getClass() == GHIssue.Label.class || getClass() == GHLabel.class ) &&
                (o.getClass() == GHIssue.Label.class || o.getClass() == GHLabel.class )
            ) {
            GHLabel ghLabel = (GHLabel) o;
            return url.equals(ghLabel.url);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "GHLabel{" +
            "name='" + name + '\'' +
            '}';
    }
}
