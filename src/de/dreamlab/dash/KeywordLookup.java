package de.dreamlab.dash;

import com.intellij.lang.Language;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.ui.Messages;

import java.util.*;

public class KeywordLookup {
    private static final String ANDROID_STUDIO_PRODUCT_CODE = "AI";

    private HashMap<String, List<String>> languageMap;

    public KeywordLookup()
    {
        languageMap = new HashMap<String, List<String>>();

        // IntelliJ
        addLanguage("JAVA", javaKeyword(), "javafx", "grails", "groovy", "playjava", "spring", "cvj", "processing");

        // WebStorm
        addLanguage("HTML", "html");
        addLanguage("CSS", "css");
        addLanguage("LESS", "less", "css");
        addLanguage("SASS", "sass", "compass", "bourbon", "neat", "css");
        addLanguage("SCSS", "sass", "compass", "bourbon", "neat", "css");
        addLanguage("JavaScript", "javascript", "jquery", "jqueryui", "jquerym", "backbone", "marionette", "meteor", "sproutcore", "moo", "prototype", "bootstrap", "foundation", "lodash", "underscore", "ember", "sencha", "extjs", "titanium", "knockout", "zepto", "yui", "d3", "dojo", "nodejs", "express", "grunt", "mongoose", "chai", "cordova", "phonegap");
        addLanguage("CoffeeScript", "coffee", "javascript", "jquery", "jqueryui", "jquerym", "backbone", "marionette", "meteor", "sproutcore", "moo", "prototype", "bootstrap", "foundation", "lodash", "underscore", "ember", "sencha", "extjs", "titanium", "knockout", "zepto", "yui", "d3", "dojo", "nodejs", "express", "grunt", "mongoose", "chai", "cordova", "phonegap");
        addLanguage("MySQL", "mysql");
        addLanguage("SQLite", "sqlite");

        // PhpStorm
        addLanguage("PHP", "php", "wordpress", "drupal", "zend", "laravel", "yii", "joomla", "ee", "codeigniter", "cakephp", "symfony", "typo3", "twig", "smarty");
        addLanguage("SmartyConfig", "smarty");

        /*
        Supported Languages

        IntelliJ Community Editon:
        JQL DTD SPI Properties TEXT RegExp RELAX-NG XHTML YouTrack XPath2 XPath XML Renderscript Manifest Groovy AIDL

        PhpStorm:
        CSS Asp Twig RegExp JSP PostgreSQL  Apple JS SQL92 ReST MySQL SQLite SmartyConfig HAML H2 DB2 GWT JavaScript TypeScript SASS XML JS in HTML JavaScript 1.8 Smarty PostgresPLSQL JQL LESS OracleSqlPlus yaml HSQLDB CoffeeScript ApacheConfig DTD JSON textmate JavaScript 1.5 Sybase Locale ECMA Script Level 4 ECMAScript 6 JavaScript 1.7 Gherkin Derby TEXT XHTML SCSS PHP XPath XPath2 RELAX-NG JavaScript 1.6 SQL YouTrack TSQL JQuery-CSS Ini JavaScript Oracle JSPX GenericSQL HTML


        /*
            use the following command to display all available languages in the event log. intended for development purposes.
            listRegisteredLanguages();
         */
    }


    private void listRegisteredLanguages() {
        Collection<Language> languages = Language.getRegisteredLanguages();

        ArrayList<String> languageList = new ArrayList<String>();

        for ( Language language : languages ) {
            String languageStr = language.getID();

            Language baseLanguage = language.getBaseLanguage();
            while ( baseLanguage != null ) {
                languageStr += " <- " + baseLanguage.getID();
                baseLanguage = baseLanguage.getBaseLanguage();
            }

            languageList.add(languageStr);
        }

        Collections.sort(languageList);

        String message = "";
        for ( String s : languageList ) {
            message += s + "\n";
        }

        Notifications.Bus.notify(new Notification("Dash", "Dash: Registered Languages ", message, NotificationType.INFORMATION));
    }

    private void addLanguage(String language, String... keywords)
    {
        languageMap.put(language, Arrays.asList(keywords));
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

    public List<String> findKeywords(Language language)
    {
        String languageName = findLanguageName(language);

        if ( languageName != null ) {
            return languageMap.get(languageName);
        }
        else {
            return new ArrayList<String>();
        }
    }

    private String javaKeyword()
    {
        if ( ANDROID_STUDIO_PRODUCT_CODE.equals(ApplicationInfo.getInstance().getBuild().getProductCode()) ) {
            return "android";
        }
        else {
            return "java";
        }
    }
}
