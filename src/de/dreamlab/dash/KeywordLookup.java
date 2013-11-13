package de.dreamlab.dash;

import com.intellij.ide.util.PropertiesComponent;

import java.util.HashMap;


public class KeywordLookup {
    private static String CONFIG_KEYWORDS = "DASH_INTEGRATION_KEYWORDS";
    private static String DEFAULT_KEYWORDS = "ActionScript=actionscript;C++=cpp;CoffeeScriptcoffee;Perl=perl;CSS=css;Erlang=erlang;Haskell=haskell;HTML=html;JAVA=java;CLASS=java;JavaScript=javascript;LESS=less;PHP=php;SASS=sass";

    private HashMap<String, String> typeMap;
    private HashMap<String, String> extensionMap;

    public KeywordLookup()
    {
        initDefaults();

        extensionMap = new HashMap<String, String>();
        typeMap = new HashMap<String, String>();

        String[] associations = PropertiesComponent.getInstance().getValue(CONFIG_KEYWORDS).split(";");
        for ( String association : associations ) {
            String[] values = association.split("=");

            if ( values.length == 2 ) {
                if ( values[0].substring(0, 1).equals(".") ) {
                    extensionMap.put(values[0].substring(1), values[1]);
                }
                else {
                    typeMap.put(values[0], values[1]);
                }
            }
        }
    }

    private void initDefaults()
    {
        /*
            Associations are customizable in "~/Library/Preferences/%IDE_NAME%/options/options.xml" under the property "DASH_INTEGRATION_KEYWORDS"
            %IDE_NAME% might be "WebIde60" or "IdeaIC12"

            Values pairs can be provided in a semi-colon delimited list. The value pair consists of FILE_TYPE=KEYWORD
            File type names can be found in the IDE settings. Instead of file types also file extensions can be used. The file extension has to start with a dot.

             ex: HTML=html;.xhtml=html
                  |           |
                  |          Uses Dash keyword "html" for files with .xhtml extension (extensions have priority over file types)
                 Uses Dash keyword "html" for files of type HTML
         */

        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

        if ( !propertiesComponent.isValueSet(CONFIG_KEYWORDS) ) {
            propertiesComponent.setValue(CONFIG_KEYWORDS, DEFAULT_KEYWORDS);
        }
    }

    public String findKeyword(String type, String extension)
    {
        if ( extensionMap.containsKey(extension) ) {
            return extensionMap.get(extension);
        }
        else {
            return typeMap.get(cleanType(type));
        }
    }

    private String cleanType(String type)
    {
        type = type.replaceFirst("\\(.*\\)", "");
        type = type.replace("files", "");
        type = type.trim();

        return type;
    }
}
