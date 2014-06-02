package de.dreamlab.dash;

import com.intellij.lang.Language;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.projectRoots.Sdk;
import de.dreamlab.dash.keywords.ExcludeSdkTypeKeyword;
import de.dreamlab.dash.keywords.IKeyword;
import de.dreamlab.dash.keywords.Keyword;
import de.dreamlab.dash.keywords.SdkTypeSpecificKeyword;

import java.util.*;

public class KeywordLookup {
    private HashMap<String, List<IKeyword>> languageMap;

    public KeywordLookup()
    {
        languageMap = new HashMap<String, List<IKeyword>>();

        // IntelliJ Ultimate Edition 13.1, WebStorm 8.0, PhpStorm 7.1, RubyMine 6.3, PyCharm 3.1
        setLanguage("HTML", "html", "angularjs");
        setLanguage("XHTML", "html");
        setLanguage("XML", "xml");
        setLanguage("XPath", "xml"); // not RubyMine, not PyCharm
        setLanguage("RegExp", "regex");

        // IntelliJ Ultimate Edition 13.1, WebStorm 8.0, PhpStorm 7.1, RubyMine 6.3, PyCharm 3.1
        setLanguage("CSS", "css");
        setLanguage("JQuery-CSS", "css", "jquery", "jqueryui", "jquerym");
        setLanguage("LESS", "less", "css");
        setLanguage("SASS", "sass", "compass", "bourbon", "neat", "css");
        setLanguage("SCSS", "sass", "compass", "bourbon", "neat", "css");
        setLanguage("Stylus", "stylus", "css"); // not PhpStorm
        setLanguage("HAML", "haml");
        setLanguage("CoffeeScript", "coffee", "javascript", "jquery", "jqueryui", "jquerym", "backbone", "marionette", "meteor", "sproutcore", "moo", "prototype", "bootstrap", "foundation", "lodash", "underscore", "ember", "sencha", "extjs", "titanium", "knockout", "zepto", "yui", "d3", "dojo", "nodejs", "express", "grunt", "mongoose", "moment", "require", "awsjs", "jasmine", "sinon", "chai", "cordova", "phonegap"); // not IntelliJ
        setLanguage("JavaScript", "javascript", "jquery", "jqueryui", "jquerym", "backbone", "marionette", "meteor", "sproutcore", "moo", "prototype", "bootstrap", "foundation", "lodash", "underscore", "ember", "sencha", "extjs", "titanium", "knockout", "zepto", "yui", "d3", "dojo", "nodejs", "express", "grunt", "mongoose", "moment", "require", "awsjs", "jasmine", "sinon", "chai", "cordova", "phonegap", "angularjs");
        setLanguage("MySQL", "mysql"); // not WebStorm
        setLanguage("SQLite", "sqlite"); // not WebStorm

        // Products listed for each entry
        final IKeyword javaKeyword = new SdkTypeSpecificKeyword("java", "Android SDK", "android");
        final IKeyword javaFxKeyword = new ExcludeSdkTypeKeyword("javafx", "Android SDK");
        final IKeyword grailsKeyword = new ExcludeSdkTypeKeyword("grails", "Android SDK");
        final IKeyword groovyKeyword = new ExcludeSdkTypeKeyword("groovy", "Android SDK");
        final IKeyword playjavaKeyword = new ExcludeSdkTypeKeyword("playjava", "Android SDK");
        final IKeyword springKeyword = new ExcludeSdkTypeKeyword("spring", "Android SDK");

        setLanguage("Dart", "dartlang", "polymerdart", "angulardart"); // WebStorm
        setLanguage("DjangoTemplate", "django"); // PyCharm
        setLanguage("Groovy", "groovy"); // IntelliJ
        setLanguage("Jade", "jade"); // WebStorm
        setLanguage("JAVA", javaKeyword, javaFxKeyword, grailsKeyword, groovyKeyword, playjavaKeyword, springKeyword, "cvj", "processing", "javadoc"); // IntelliJ
        setLanguage("JsInJade", "javascript", "jade"); // WebStorm
        setLanguage("JSP", javaKeyword,  javaFxKeyword, grailsKeyword, groovyKeyword, playjavaKeyword, springKeyword, "cvj", "javadoc"); // IntelliJ, WebStorm, PhpStorm
        setLanguage("JSPX", javaKeyword, javaFxKeyword, grailsKeyword, groovyKeyword, playjavaKeyword, springKeyword, "cvj", "javadoc"); // IntelliJ, WebStorm, PhpStorm
        setLanguage("Mxml", "actionscript"); // IntelliJ
        setLanguage("PHP", "php", "wordpress", "drupal", "zend", "laravel", "yii", "joomla", "ee", "codeigniter", "cakephp", "phpunit", "symfony", "typo3", "twig", "smarty", "phpp"); // PhpStorm
        setLanguage("Play", "playjava"); // IntelliJ; uncertain
        setLanguage("Puppet", "puppet"); // RubyMine, PyCharm
        setLanguage("Python", "python", "django", "twisted", "sphinx", "flask", "tornado", "sqlalchemy", "numpy", "scipy", "salt", "cvp"); // PyCharm
        setLanguage("ruby", "ruby", "rubygems", "rails"); // RubyMine
        setLanguage("Smarty", "smarty"); // PhpStorm
        setLanguage("SmartyConfig", "smarty"); // PhpStorm
        setLanguage("Twig", "twig"); // PhpStorm

        // Jetbrains Plugins
        setLanguage("Haskell", "haskell");
        setLanguage("Scala", "scala", "akka", "playscala");
        setLanguage("SSP", "scala", "akka", "playscala");
        setLanguage("TypoScript", "typo3");

        // Third-party Plugins
        setLanguage("Bash", "bash", "manpages");
        setLanguage("Google Go", "go", "godoc");
        setLanguage("Lua", "lua", "corona");
        setLanguage("Markdown", "markdown");


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

    private void setLanguage(String language, Object... keywords)
    {
        ArrayList<IKeyword> keywordList = new ArrayList<IKeyword>();

        for (Object keyword : keywords) {
            if ( keyword instanceof String ) {
                keywordList.add(new Keyword((String) keyword));
            }
            else if ( keyword instanceof IKeyword ) {
                keywordList.add((IKeyword) keyword);
            }
            else {
                throw new Error("Invalid keyword");
            }

        }

        languageMap.put(language, keywordList);
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

    public List<String> findKeywords(Language language, Sdk sdk)
    {
        ArrayList<String> result = new ArrayList<String>();

        String languageName = findLanguageName(language);
        if ( languageName != null ) {
            for ( IKeyword keyword : languageMap.get(languageName) ) {
                String name = keyword.getName(sdk);

                if ( name != null ) {
                    result.add(name);
                }
            }
        }

        return result;
    }
}
