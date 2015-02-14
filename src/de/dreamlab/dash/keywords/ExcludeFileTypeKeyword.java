package de.dreamlab.dash.keywords;

import de.dreamlab.dash.LookupInfoDictionary;

public class ExcludeFileTypeKeyword implements IKeyword {
    private String keyword;
    private String fileType;


    public ExcludeFileTypeKeyword(String keyword, String fileType)
    {
        this.keyword = keyword;
        this.fileType = fileType;
    }

    @Override
    public String getName(LookupInfoDictionary dict) {
        try {
            if (fileType.equals(dict.getPsiFile().getFileType().getName())) {
                return null;
            }
        }
        catch ( Throwable e ) {
        }

        return keyword;
    }
}
