package de.dreamlab.dash.keywords;

import de.dreamlab.dash.LookupInfoDictionary;

public class Keyword implements IKeyword {
    private String keyword;

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName(final LookupInfoDictionary dict) {
        return keyword;
    }
}
