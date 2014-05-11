A simple plugin for the IntelliJ Platform (IntelliJ IDEA, RubyMine, WebStorm, PhpStorm, PyCharm, Android Studio) that provides keyboard shortcut access to Dash.

## Installation
To install the plugin in your IntelliJ IDE go to Preferences -> Plugins -> Browse repositories and search for "Dash".

**AppCode 1.x** users have to manually install this version of the plugin: https://github.com/gdelmas/IntelliJDashPlugin/releases/tag/2.2

## Kapeli Dash
Dash is a Mac application for rapid search of developer documentation. It is free with nags to persuade you to pay and lose the nags. The free version is fully functional and super-useful. Get karma for buying and supporting the developer :) It can be downloaded here:
[http://kapeli.com/dash](http://kapeli.com/dash)

## Usage
The default shortcut assigned in the plugin is **Mac-Shift-D**.
It either uses the current selection for the search, or the caret position. The plugin will identify the currently used programming language through context and request filtered search results accordingly.

## Troubleshooting
######The installation from the repositories does not work
It looks like there is an IntelliJ/Java bug with OS X Mavericks which prevents to install plugins from the repositories. Please install the plugin manually from [here](https://github.com/gdelmas/IntelliJDashPlugin/releases). For additional information check [issue #13](https://github.com/gdelmas/IntelliJDashPlugin/issues/13).
