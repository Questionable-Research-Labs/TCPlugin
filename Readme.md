# Technocraft Plugin
This is the plugin used on the technocraft server for general util, and some fun. Yes there is an entire discord client bootstrapped in here, come at me! angry

## Project Structure
If you are wanting to make you own additions to the plugin, please place your work in its own package so that we don't 
have to dance around each other's code. To add your own component, create some sort of main class for you component and
annotate it with @TCPluginComponent and one required method for initialization, annotated with @TCPluginComponentInit and
one optional method for shutdown, annotated with @TCPluginComponentShutdown.