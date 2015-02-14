package de.dreamlab.dash.keywords;

import de.dreamlab.dash.LookupInfoDictionary;

import java.util.ArrayList;

public class FileTypeSpecificKeyword implements IKeyword {
    private String keyword;
    private String fileType;


    public FileTypeSpecificKeyword(String keyword, String fileType) {
        this.keyword = keyword;
        this.fileType = fileType;
    }

    @Override
    public String getName(LookupInfoDictionary dict) {
        try {
            if (fileType.equals(dict.getPsiFile().getFileType().getName())) {
                return keyword;
            }
        } catch (Throwable e) {
        }

        return null;
    }

    public static IKeyword[] createList(String[] defaultKeywords, String fileType, String[] fileTypeSpecificKeywords)
    {
        ArrayList<IKeyword> result = new ArrayList<IKeyword>();

        for ( String defaultKeyword : defaultKeywords ) {
            result.add(new ExcludeFileTypeKeyword(defaultKeyword, fileType));
        }

        for ( String fileTypeSpecificKeyword : fileTypeSpecificKeywords ) {
            result.add(new FileTypeSpecificKeyword(fileTypeSpecificKeyword, fileType));
        }

        return result.toArray(new IKeyword[result.size()]);
    }
}
