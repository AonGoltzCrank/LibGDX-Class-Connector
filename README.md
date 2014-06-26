# LibGDX Class Connector and How to use:

LibGDX Class Connector can be used to access methods in the main file of extention projects in LibGDX, for example if you are making a Desktop game, you will have the project "YourGame-desktop', which you wouldn't be able to access. LibGDX Class Connector works around this to give you access to all the methods in your main project files, so that if you have a method in your DesktopLauncher file you need, you can register the DesktopLauncher with the Connector class and by that use methods inside it.
## Example of use:
let's say we have the packages: com.MyName.MyPakcage.desktop  and com.MyName.MyPackage
Inside the DesktopLauncher class:
```java
public static void main(String[] args){
   Connector conn = Connector.getInstance();
   conn.registerClass(new DesktopLauncher().getClass());
   // The rest of the code here.
}

public void doSomething(){
   //Do something.
}
```
Inside a different file that needs access the a method inside DesktopLauncher:
```java
public void activateDoSomething(){
..................... //The method
   Connector conn = Connector.getInstance();
   conn.invokeMethod("com.MyName.MyProject.desktop.DesktopLauncher", "doSomething", null);
}
```
### If you have some parameteres in the method, for example:
```java
public void doSomething(Object param){
   //Do something.
}
```
You'll have to do this:
```java
public void activateDoSomething(){
..................... //The method
   Connector conn = Connector.getInstance();
   conn.invokeMethod("com.MyName.MyProject.desktop.DesktopLauncher", "doSomething", new Object[]{obj}); //Or as many items as you need.
}
```
### If you have a return method:
```java
public int doSomething(Object param){
   //Do something.
   return 1;
}
```
To get the returned object you'll do this:
```java
public void activateDoSomething(){
..................... //The method
   Connector conn = Connector.getInstance();
   conn.invokeMethod("com.MyName.MyProject.desktop.DesktopLauncher", "doSomething", new Object[]{obj}); //Or as many items as you need.
   Object returned = conn.getResultOfInvoke();
}
```

That's it, have fun!
