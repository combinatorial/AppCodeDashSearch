package de.dreamlab.dash.keywords;

import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

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
    public String getName(Sdk sdk, Project project, PsiFile psiFile, VirtualFile virtualFile) {
        Language fileSqlLanguage = null;

        try {
            Class.forName("com.intellij.sql.dialects.SqlDialectMappings");
            fileSqlLanguage = com.intellij.sql.dialects.SqlDialectMappings.getMapping(project, virtualFile);
        }
        catch (ClassNotFoundException e) {
        }

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

}
