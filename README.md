A simple plugin for the IntelliJ Platform (IntelliJ IDEA, RubyMine, WebStorm, PhpStorm, PyCharm, Android Studio and AppCode) that provides keyboard shortcut access to Dash. This is necessary as the 'Services' menu is not populated in IntelliJ (apparently a Java limitation).

## Installation
To install the plugin in your IntelliJ IDE go to Preferences -> Plugins -> Browse repositories and search for "Dash".

## 3.0 Beta
There is a beta for version 3.0 with content aware search. You can install it manually from [here](https://github.com/gdelmas/IntelliJDashPlugin/releases/tag/3.0beta1). You can also help [create and update the search filter dictionaries](https://github.com/gdelmas/IntelliJDashPlugin/issues/15).

## Kapeli Dash
Dash is a Mac application for rapid search of developer documentation. It is free with nags to persuade you to pay and lose the nags. The free version is fully functional and super-useful. Get karma for buying and supporting the developer :) It can be downloaded here:
[http://kapeli.com/dash](http://kapeli.com/dash)

## Usage
The default shortcut assigned in the plugin is **Mac-Shift-D**. It either 
uses the current selection for the search, or the caret position.

## Configuration
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

## Troubleshooting
######The installation from the repositories does not work
It looks like there is an IntelliJ/Java bug with OS X Mavericks which prevents to install plugins from the repositories. Please install the plugin manually from [here](https://github.com/gdelmas/IntelliJDashPlugin/releases). For additional information check [issue #13](https://github.com/gdelmas/IntelliJDashPlugin/issues/13).

