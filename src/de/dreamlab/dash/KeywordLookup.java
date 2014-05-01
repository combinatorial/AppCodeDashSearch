package de.dreamlab.dash;

import com.intellij.lang.Language;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.util.*;

public class KeywordLookup {
    protected final Map<String, List<String>> languageMap = new HashMap<String, List<String>>();
    protected final DashLauncher launcher;

    public KeywordLookup(final DashLauncher dashLauncher)
    {
        launcher = dashLauncher;
        setUpLanguages();
    }

    protected void setUpLanguages() {
        // IntelliJ Ultimate Edition 13.1, WebStorm 8.0, PhpStorm 7.1, RubyMine 6.3, PyCharm 3.1
        addLanguage("HTML", "html", "angularjs");
        addLanguage("XHTML", "html");
        addLanguage("XML", "xml");
        addLanguage("XPath", "xml"); // not RubyMine, not PyCharm
        addLanguage("RegExp", "regex");

        // IntelliJ Ultimate Edition 13.1, WebStorm 8.0, PhpStorm 7.1, RubyMine 6.3, PyCharm 3.1
        addLanguage("CSS", "css");
        addLanguage("JQuery-CSS", "css", "jquery", "jqueryui", "jquerym");
        addLanguage("LESS", "less", "css");
        addLanguage("SASS", "sass", "compass", "bourbon", "neat", "css");
        addLanguage("SCSS", "sass", "compass", "bourbon", "neat", "css");
        addLanguage("Stylus", "stylus", "css"); // not PhpStorm
        addLanguage("HAML", "haml");
        addLanguage("CoffeeScript", "coffee", "javascript", "jquery", "jqueryui", "jquerym", "backbone", "marionette", "meteor", "sproutcore", "moo", "prototype", "bootstrap", "foundation", "lodash", "underscore", "ember", "sencha", "extjs", "titanium", "knockout", "zepto", "yui", "d3", "dojo", "nodejs", "express", "grunt", "mongoose", "moment", "require", "awsjs", "jasmine", "sinon", "chai", "cordova", "phonegap"); // not IntelliJ
        addLanguage("JavaScript", "javascript", "jquery", "jqueryui", "jquerym", "backbone", "marionette", "meteor", "sproutcore", "moo", "prototype", "bootstrap", "foundation", "lodash", "underscore", "ember", "sencha", "extjs", "titanium", "knockout", "zepto", "yui", "d3", "dojo", "nodejs", "express", "grunt", "mongoose", "moment", "require", "awsjs", "jasmine", "sinon", "chai", "cordova", "phonegap", "angularjs");
        addLanguage("MySQL", "mysql"); // not WebStorm
        addLanguage("SQLite", "sqlite"); // not WebStorm

        // IntelliJ Community Edition 13.1
        addLanguage("JAVA", "java6", "java7", "java8", "jee6", "jee7", "javadoc", "javafx", "grails", "groovy", "playjava", "spring", "cvj", "processing");
        addLanguage("JSP", "java6", "java7", "java8", "jee6", "jee7", "javadoc", "grails", "groovy", "playjava", "spring", "html", "xml", "css");
        addLanguage("JSPX", "java6", "java7", "java8", "jee6", "jee7", "javadoc", "grails", "groovy", "playjava", "spring", "html", "xml", "css");

        // Products listed for each entry
        addLanguage("Dart", "dartlang", "polymerdart", "angulardart"); // WebStorm
        addLanguage("DjangoTemplate", "django"); // PyCharm
        addLanguage("Groovy", "groovy"); // IntelliJ
        addLanguage("Jade", "jade"); // WebStorm
        addLanguage("JAVA", javaKeyword(), "javafx", "grails", "groovy", "playjava", "spring", "cvj", "processing", "javadoc"); // IntelliJ
        addLanguage("JsInJade", "javascript", "jade"); // WebStorm
        addLanguage("JSP", javaKeyword(), "javafx", "grails", "groovy", "playjava", "spring", "cvj", "javadoc"); // IntelliJ, WebStorm, PhpStorm
        addLanguage("JSPX", javaKeyword(), "javafx", "grails", "groovy", "playjava", "spring", "cvj", "javadoc"); // IntelliJ, WebStorm, PhpStorm
        addLanguage("Mxml", "actionscript"); // IntelliJ
        addLanguage("PHP", "php", "wordpress", "drupal", "zend", "laravel", "yii", "joomla", "ee", "codeigniter", "cakephp", "phpunit", "symfony", "typo3", "twig", "smarty", "phpp"); // PhpStorm
        addLanguage("Play", "playjava"); // IntelliJ; uncertain
        addLanguage("Puppet", "puppet"); // RubyMine, PyCharm
        addLanguage("Python", "python", "django", "twisted", "sphinx", "flask", "tornado", "sqlalchemy", "numpy", "scipy", "salt", "cvp"); // PyCharm
        addLanguage("ruby", "ruby", "rubygems", "rails"); // RubyMine
        addLanguage("Smarty", "smarty"); // PhpStorm
        addLanguage("SmartyConfig", "smarty"); // PhpStorm
        addLanguage("Twig", "twig"); // PhpStorm

        // Jetbrains Plugins
        addLanguage("Haskell", "haskell");
        addLanguage("Scala", "scala", "akka", "playscala");
        addLanguage("SSP", "scala", "akka", "playscala");
        addLanguage("TypoScript", "typo3");

        // Third-party Plugins
        addLanguage("Bash", "bash", "manpages");
        addLanguage("Google Go", "go" ,"godoc");
        addLanguage("Lua", "lua", "corona");
        addLanguage("Markdown", "markdown");

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

    protected void addLanguage(String language, String... keywords)
    {
        languageMap.put(language, Arrays.asList(keywords));
    }

    protected String findLanguageName(Language language)
    {
        while ( language != null ) {
            if ( languageMap.containsKey(language.getID()) ) {
                return language.getID();
            }

            language = language.getBaseLanguage();
        }

        return null;
    }

    protected List<String> findKeywords(Language language)
    {
        String languageName = findLanguageName(language);

        if ( languageName != null ) {
            return languageMap.get(languageName);
        }
        else {
            return new ArrayList<String>();
        }
    }

    public void searchOnDash(final Language language, final String query) {
        launcher.search(findKeywords(language), query);
    }
}
