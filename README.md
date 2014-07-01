A smart and simple plugin that provides keyboard shortcut access for Dash in IntelliJ IDEA, RubyMine, WebStorm, PhpStorm, PyCharm and Android Studio.

## Installation
To install the plugin in your IDE go to Preferences -> Plugins -> Browse repositories and search for "Dash".

[![Flattr this](http://api.flattr.com/button/flattr-badge-large.png)](http://flattr.com/thing/2558535/gdelmasIntelliJDashPlugin-on-GitHub)

## What's new
In the past months I have been adding and testing new features for the plugin. I am now happy to release a lot of these additions that will make your search results just better.

From now on code in strings will be recognized. This way it is no longer necessary to select a specific word, the plugin will automatically know what you want to look up by the caret position. A lot of strings contain SQL code and for these your results will only be from a SQL docset. In Project Settings -> SQL Dialects you can set which SQL docset you want to search. There are docsets available for MySQL, PostgreSQL and SQLite.

If you have the "Regular Expressions" cheatsheet installed in Dash you can lookup groups, quantifiers and special characters really quickly. Just try and check what /\v/ stands for.

Android projects get results from the Android docs, not Java. Java results depend on the projects SDK setting, searching either Java 6, 7 or 8. The same goes for Python 2 or 3. Apart from that I added support for Bash, Go, Haskell, Lua, Markdown, Scala and TypoScript. Language support for JavaScript and others has been enhanced.

Simplicity is really important to me, that's why I always try to keep the plugin smart, simple and settings free. I hope this helps you spend less time searching the reference. Make the best of it.

## Usage
The default shortcut assigned in the plugin is **Cmd-Shift-D**.
It either uses the caret position for the search, or the current selection. The plugin will identify the currently used programming language and request filtered search results accordingly.

## Kapeli Dash App
Dash is a Mac application for rapid search of developer documentation. There is a free, fully functional version with nags. It can be downloaded here:
[http://kapeli.com/dash](http://kapeli.com/dash)

## Troubleshooting
######The plugin does not work on old IDEs
Older IDE versions like **AppCode 1.x** are not supported anymore. Please manually install version 2.2 of the plugin: https://github.com/gdelmas/IntelliJDashPlugin/releases/tag/2.2

######In rare conditions the installation from the repositories does not work
It looks like there is an IntelliJ/Java bug with OS X Mavericks which prevents to install plugins from the repositories. Please install the plugin manually from [here](https://github.com/gdelmas/IntelliJDashPlugin/releases). For additional information check [issue #13](https://github.com/gdelmas/IntelliJDashPlugin/issues/13).
