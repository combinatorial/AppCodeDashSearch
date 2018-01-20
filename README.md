A smart and simple plugin that provides keyboard shortcut access for Dash, Velocity or Zeal in IntelliJ IDEA, RubyMine, WebStorm, PhpStorm, PyCharm, DataGrip, CLion, GoLand and Android Studio.

## Installation
To install the plugin in your IDE go to **Preferences -> Plugins -> Browse repositories** and **search for "Dash"**.

## Usage
The default **shortcut** assigned to smart-search is **Cmd-Shift-D** (Mac OS X) or **Ctrl-Shift-D** (Windows, Linux).


A **menubar command** named "**Smart-Search Documentation**" can be found in the "Tools" menu.


The plugin either searches for the statement at caret position or the current selection. It will identify the programming language in use and request filtered results accordingly. A non filtered search over all documentation entries can be invoked by adding the **Alt** modifier key to the shortcut. "**Search all Documentation**" is also available in the tools menu. 

## Configuration
### Shortcut
You can change the shortcut at Preferences -> Keymap -> Plug-ins -> Dash.

### Toolbar Icon
You can add a button to the toolbar. Right-click the menubar -> Customize […]. You will find the button under "Plug-ins -> Dash".

## Supported API Documentation Browsers
### Kapeli Dash (Mac OS X)
Dash is an API Documentation Browser and Code Snippet Manager. Dash stores snippets of code and instantly searches offline documentation sets for 150+ APIs (for a full list, see below). You can even generate your own docsets or request docsets to be included.
[http://kapeli.com/dash](http://kapeli.com/dash)

### Velocity (Windows)
Velocity gives your Windows desktop offline access to over 150 API documentation sets (provided by Dash for OS X).
[https://velocity.silverlakesoftware.com](https://velocity.silverlakesoftware.com)

### Zeal (Linux & Windows)
Zeal is a simple offline API documentation browser inspired by Dash (OS X app).
[http://zealdocs.org](http://zealdocs.org)


## Troubleshooting
###### On Windows, when using Zeal the IDE shows `Failed to open dash-plugin://...` in the Event Log
It looks like the URL handler has not been registered for Zeal. Please run `zeal.exe --register`.

###### The plugin does not work on old IDEs
Older IDE versions like **AppCode 1.x** are not supported anymore. Please manually install version 2.2 of the plugin: https://github.com/gdelmas/IntelliJDashPlugin/releases/tag/2.2

###### In rare conditions the installation from the repositories does not work
It looks like there is an IntelliJ/Java bug with OS X Mavericks which prevents to install plugins from the repositories. Please install the plugin manually from [here](https://github.com/gdelmas/IntelliJDashPlugin/releases). For additional information check [issue #13](https://github.com/gdelmas/IntelliJDashPlugin/issues/13).
