package org.qrl.tcplugin;

import nz.laspruca.tcplugin.*;
import org.bukkit.configuration.file.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.qrl.tcplugin.annotations.*;
import org.reflections.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.*;

public final class TCPlugin extends JavaPlugin {
	public List<Class<?>> components = new ArrayList<>();
	public static TCPlugin plugin;
	public static Logger logger;
	public static FileConfiguration config;


	@Override
	public void onEnable() {
		plugin = TCPlugin.getPlugin(TCPlugin.class);
		logger = plugin.getLogger();
		config = plugin.getConfig();
		saveDefaultConfig();
		registerComponents();
		for (Class<?> component : components) {
			Method method = Arrays
					.stream(component.getMethods())
					.filter(meth -> meth.isAnnotationPresent(TCPluginComponentInit.class))
					.collect(Collectors.toList())
					.get(0);
			try {
				method.invoke(null);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDisable() {
		for (Class<?> component : components) {
			List<Method> methods = Arrays
					.stream(component.getMethods())
					.filter(meth -> meth.isAnnotationPresent(TCPluginComponentShutdown.class))
					.collect(Collectors.toList());
			if (methods.size() >= 1) {
				try {
					methods.get(0).invoke(null);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void registerEvent(Listener e) {
		getServer().getPluginManager().registerEvents(e, this);
	}

	public void registerComponents() {
		Reflections reflections = new Reflections("");
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(TCPluginComponent.class);

		for (Class<?> tClass : classes) {
			Method[] methods = tClass.getMethods();
				if (Arrays.stream(methods)
						.filter(method -> method.isAnnotationPresent(TCPluginComponentInit.class)).count() < 1) {
					logger.severe("Class " + tClass.getName() + " does not contain any init methods");
					break;
				} else {
					components.add(tClass);
				}
		}
	}
}