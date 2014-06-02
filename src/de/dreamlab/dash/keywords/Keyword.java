package de.dreamlab.dash.keywords;

import com.intellij.openapi.projectRoots.Sdk;

public class Keyword implements IKeyword {
    private String keyword;

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName(Sdk sdk) {
        return keyword;
    }
}
