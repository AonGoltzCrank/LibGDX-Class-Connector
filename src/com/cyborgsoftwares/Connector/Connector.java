package com.cyborgsoftwares.Connector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to register the desktop launcher and the core project to enable
 * restarts. How to use: To use this file you first need to call the
 * constructor.<br>
 * After you call the constructor, from the point on to retrieve the instance,
 * you have to use {@link #getInstance()}, and not the constructor again, so
 * that the data can be transfered from across the classes.<br>
 * After calling the constructor, you need to call {@link #regsiterClass(Class)}
 * , with the parameter of the class you want to register, for example:
 * <code><br>
 * 		Connector conn = new Connecter();<br>
 * 		conn.registerClass(new DesktopLauncher().getClass());<br>
 * </code> After that, the Connector will automatically register the class and
 * methods for later use. To invoke a method you have to pass in the following
 * data into the method {@link #invokeMethod(String, String, Object[])}:<br>
 * A String of the name of the class, for example:
 * <code> com.MyName.MyPackage.Awesomeness </code><br>
 * A String of the name of the method you want, for example:
 * <code> scaleImage </code><br>
 * An Object[] of the parameters you want to pass into the method, so for
 * example, if your method needs an int to run, you'll put in an object[] with
 * an integer in it. On the other hand if your method doesn't need any
 * parameters to run you can pass in null and it will run it with no parameters.<br>
 * The method will return a boolean value, true means there is a returned
 * object, which you can grab by using the {@link #getResultOfInvoke()}, false
 * means that there is no returned data and that the method was executed
 * correctly.<br>
 * That's it. If you use this please give credit.
 * 
 * @author Alon "Aon" Goltzman @ Cyborg Softwares.
 *
 */
public class Connector {

	private List<Class<?>> registeredClasses = new ArrayList<Class<?>>();
	private Cluster<String, Method> registeredMethods = new Cluster<String, Method>();
	private Object returnedResult;
	private static Connector instance = null;

	private Connector() {
	}

	/**
	 * A method to get the instance of the class to be used.
	 * 
	 * @return the current instance of the class.
	 */
	public static Connector getInstance() {
		if (instance == null) {
			return instance = new Connector();
		} else
			return instance;
	}

	/**
	 * Register a class to the current set of classes.
	 * 
	 * @param classToRegister
	 *            - the class to be registered.
	 * @return a boolean value, if true then everything went well, if false then
	 *         something went wrong.
	 */
	public boolean regsiterClass(Class<?> classToRegister) {
		registeredClasses.add(classToRegister);
		return registereMethods(classToRegister);
	}

	/**
	 * A private method to register the method of a given class.
	 * 
	 * @param classToRegister
	 *            - the class from which to get all the registered items.
	 * @return a boolean value, if true everything is okay, if false something
	 *         went wrong and an exception has been thrown.
	 */
	private boolean registereMethods(Class<?> classToRegister) {
		try {
			for (Method method : classToRegister.getMethods())
				registeredMethods.add(
						classToRegister.toString().replace("class", "").trim(),
						method);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Invoke a certain method in the class you <b>!!ALREADY REGISTERED!!<b>
	 * 
	 * @param className
	 * @param methodName
	 * @param args
	 * @return
	 */
	public boolean invokeMethod(String className, String methodName,
			Object[] args) {
		System.out.println("Connector: Trying to invoke method: " + methodName
				+ " in class " + className);
		Method[] methods = new Method[1];
		Method selectedMethod = null;
		int size = registeredMethods.getValuesForKey(className, methods);
		for (int i = 0; i < size; i++) {
			Method method = registeredMethods.get(i);
			if (method.getName().toLowerCase().equals(methodName)) {
				selectedMethod = method;
				method = null;
				break;
			}
		}
		Class<?> classObj = null;
		for (Class<?> current : registeredClasses) {
			if (current.getName().toLowerCase().equals(className)) {
				classObj = current;
				break;
			}
		}
		try {
			if (selectedMethod.getReturnType().equals(null)) {
				selectedMethod.invoke(classObj, args);
				return false;
			} else if (!selectedMethod.getReturnType().equals(null)) {
				returnedResult = selectedMethod.invoke(classObj, args);
				return true;
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Get an object if an object was received after using the
	 * {@link #invokeMethod(String, String, Object[])} method.
	 * 
	 * @return the object that was received.
	 */
	public Object getResultOfInvoke() {
		if (returnedResult == null) {
			System.out
					.println("Connector: You are trying to retrieve an object but no object was recieved back, so no object can be given.\nConnector: Check if you used the invokeMethod method.");
			return null;
		} else
			return returnedResult;
	}
}
