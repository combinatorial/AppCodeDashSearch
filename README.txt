A simple plugin for the IntelliJ Platform (IntelliJ IDEA, RubyMine, WebStorm, PhpStorm, PyCharm, Android Studio and AppCode) that provides keyboard shortcut access to Dash. This is necessary as the 'Services' menu is not populated in IntelliJ (apparently a Java limitation).

Download Dash.jar from here: https://github.com/gdelmas/IntelliJDashPlugin/releases/download/2.0/Dash.jar
Install Dash.jar as a plugin.

Dash is a Mac application for rapid search of developer documentation. It is free with nags to persuade you to pay and lose the nags. The free version is fully functional and super-useful. Get karma for buying and supporting the developer :) It can be downloaded here:
http://kapeli.com/dash


The default shortcut assigned in the plugin is Mac-Shift-D. It either uses the current selection for the search, or the caret position.

The plugin will use the documents file type to determine which docset keyword to use in Dash.
These associations are customizable in "~/Library/Preferences/%IDE_NAME%/options/options.xml" under the property "DASH_PLUGIN_KEYWORDS"
%IDE_NAME% might be "WebIde60" or "IdeaIC12"

Values pairs can be provided in a semi-colon delimited list. The value pair consists of FILE_TYPE=KEYWORD
File type names can be found in the IDE settings. Instead of file types file extensions can be used. The file extension has to start with a dot.

    ex: HTML=html;.xhtml=html
      |           |
      |          Uses Dash keyword "html" for files with
      |          .xhtml extension (extensions have
      |          priority over file types)
     Uses Dash keyword "html" for files of type HTML


Please feel free to request improvements, or fork-it and make them yourself!
