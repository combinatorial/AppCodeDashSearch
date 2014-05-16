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

        // IntelliJ Community Edition 13.1, WebStorm 8.0, PhpStorm 7.1, RubyMine 6.3, PyCharm 3.1
        addLanguage("HTML", "html");
        addLanguage("XHTML", "html");
        addLanguage("XML", "xml");
        addLanguage("XPath", "xml"); // not RubyMine, not PyCharm
        addLanguage("RegExp", "regex");

        // WebStorm 8.0, PhpStorm 7.1, RubyMine 6.3, PyCharm 3.1
        addLanguage("CSS", "css");
        addLanguage("JQuery-CSS", "css", "jquery", "jqueryui", "jquerym");
        addLanguage("LESS", "less", "css");
        addLanguage("SASS", "sass", "compass", "bourbon", "neat", "css");
        addLanguage("SCSS", "sass", "compass", "bourbon", "neat", "css");
        addLanguage("Stylus", "stylus", "css"); // not PhpStorm
        addLanguage("HAML", "haml");
        addLanguage("CoffeeScript", "coffee", "javascript", "jquery", "jqueryui", "jquerym", "backbone", "marionette", "meteor", "sproutcore", "moo", "prototype", "bootstrap", "foundation", "lodash", "underscore", "ember", "sencha", "extjs", "titanium", "knockout", "zepto", "yui", "d3", "dojo", "nodejs", "express", "grunt", "mongoose", "moment", "require", "awsjs", "jasmine", "sinon", "chai", "cordova", "phonegap");
        addLanguage("JavaScript", "javascript", "jquery", "jqueryui", "jquerym", "backbone", "marionette", "meteor", "sproutcore", "moo", "prototype", "bootstrap", "foundation", "lodash", "underscore", "ember", "sencha", "extjs", "titanium", "knockout", "zepto", "yui", "d3", "dojo", "nodejs", "express", "grunt", "mongoose", "moment", "require", "awsjs", "jasmine", "sinon", "chai", "cordova", "phonegap");
        addLanguage("MySQL", "mysql"); // not WebStorm
        addLanguage("SQLite", "sqlite"); // not WebStorm

        // IntelliJ Community Edition 13.1
        addLanguage("JAVA", javaKeyword(), "javafx", "grails", "groovy", "playjava", "spring", "cvj", "processing", "javadoc");
        addLanguage("JSP", javaKeyword(), "javafx", "grails", "groovy", "playjava", "spring", "cvj", "processing", "javadoc"); // uncertain
        addLanguage("JSPX", javaKeyword(), "javafx", "grails", "groovy", "playjava", "spring", "cvj", "processing", "javadoc"); // uncertain

        // Products listed for each entry
        addLanguage("Dart", "dartlang", "polymerdart", "angulardart"); // WebStorm (not yet supported by Dash)
        addLanguage("DjangoTemplate", "django"); // PyCharm
        addLanguage("Groovy", "groovy"); // IntelliJ
        addLanguage("Puppet", "puppet"); // RubyMine, PyCharm
        addLanguage("Jade", "jade"); // WebStorm
        addLanguage("JsInJade", "javascript", "jade"); // WebStorm
        addLanguage("Markdown", "markdown"); // PhpStorm
        addLanguage("PHP", "php", "wordpress", "drupal", "zend", "laravel", "yii", "joomla", "ee", "codeigniter", "cakephp", "phpunit", "symfony", "typo3", "twig", "smarty", "phpp"); // PhpStorm
        addLanguage("Python", "python", "django", "twisted", "sphinx", "flask", "tornado", "sqlalchemy", "numpy", "scipy", "salt", "cvp"); // PyCharm
        addLanguage("ruby", "ruby", "rubygems", "rails"); // RubyMine
        addLanguage("Smarty", "smarty"); // PhpStorm
        addLanguage("SmartyConfig", "smarty"); // PhpStorm
        addLanguage("Twig", "twig"); // PhpStorm


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
