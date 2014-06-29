A simple and intelligent plugin for the IntelliJ Platform (IntelliJ IDEA, RubyMine, WebStorm, PhpStorm, PyCharm, Android Studio) that provides keyboard shortcut access for Dash.

## Installation
To install the plugin in your IntelliJ IDE go to Preferences -> Plugins -> Browse repositories and search for "Dash".

[![Flattr this](http://api.flattr.com/button/flattr-badge-large.png)](http://flattr.com/thing/2558535/gdelmasIntelliJDashPlugin-on-GitHub)

**AppCode 1.x** users have to manually install this version of the plugin: https://github.com/gdelmas/IntelliJDashPlugin/releases/tag/2.2

## Kapeli Dash
Dash is a Mac application for rapid search of developer documentation. There is a free, fully functional version with nags. It can be downloaded here:
[http://kapeli.com/dash](http://kapeli.com/dash)

## Usage
The default shortcut assigned in the plugin is **Mac-Shift-D**.
It either uses the caret position for the search, or the current selection. The plugin will identify the currently used programming language through context and request filtered search results accordingly.

## Troubleshooting
######In rare conditions the installation from the repositories does not work
It looks like there is an IntelliJ/Java bug with OS X Mavericks which prevents to install plugins from the repositories. Please install the plugin manually from [here](https://github.com/gdelmas/IntelliJDashPlugin/releases). For additional information check [issue #13](https://github.com/gdelmas/IntelliJDashPlugin/issues/13).
