package de.dreamlab.dash.keywords;

import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import de.dreamlab.dash.LookupInfoDictionary;

import java.lang.reflect.Method;
import java.util.HashMap;

public class SqlDialectDependentKeyword implements IKeyword {
    private HashMap<String, String> languageMap;

    public SqlDialectDependentKeyword(String defaultKeyword, String mySqlKeyword, String sqliteKeyword, String postgreSqlKeyword) {
        languageMap = new HashMap<String, String>();

        languageMap.put("SQL", defaultKeyword);
        languageMap.put("MySQL", mySqlKeyword);
        languageMap.put("SQLite", sqliteKeyword);
        languageMap.put("PostgreSQL", postgreSqlKeyword);
    }

    @Override
    public String getName(final LookupInfoDictionary dict) {
        if ( !dict.isFileSqlLanguage() ) {
            setDictFileSqlLanguage(dict);
        }

        Language fileSqlLanguage = dict.getFileSqlLanguage();

        if ( fileSqlLanguage != null ) {
            return languageMap.get(findLanguageName(fileSqlLanguage));
        }
        else {
            return null;
        }
    }

    public String findLanguageName(Language language)
    {
        while ( language != null ) {
            if ( languageMap.containsKey(language.getID()) ) {
                return language.getID();
            }

            language = language.getBaseLanguage();
        }

        return null;
    }

    private void setDictFileSqlLanguage(LookupInfoDictionary dict)
    {
        Language language = null;

        try {
//          using reflection for the following command, because of optional dependencies
//          language = com.intellij.sql.dialects.SqlDialectMappings.getMapping(dict.getProject(), dict.getVirtualFile());
            Class sqlClass = Class.forName("com.intellij.sql.dialects.SqlDialectMappings");
            Method getMappingMethod = sqlClass.getMethod("getMapping", Project.class, VirtualFile.class);
            language = (Language)getMappingMethod.invoke(null, dict.getProject(), dict.getVirtualFile());
        }
        catch (Throwable e) {
        }

        dict.setFileSqlLanguage(language);
    }

}
