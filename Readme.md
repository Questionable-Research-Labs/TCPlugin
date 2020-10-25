# Technocraft Plugin
This is the plugin used on the technocraft server for general util, and some
fun. Yes there is an entire discord client bootstrapped in here, come at me! 😠

## Project Structure
If you are wanting to make you own additions to the plugin, please place 
your work in its own package so that we don't have to dance around each other's 
code. To add you plugin, create some sort of main class for you plugin with
onEnable() and onDisable(), or something similar and add it to 
org.qrl.tcplugin.TCPlugin.