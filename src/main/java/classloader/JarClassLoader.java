package classloader;


import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class JarClassLoader {
    private int timeToLive;
    private final Map cacheMap;

    public Class get(String updateClassName) {
        synchronized (cacheMap) {
            CacheObject c = (CacheObject) cacheMap.get(updateClassName);
            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                try {
                    return Class.forName(updateClassName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    protected class CacheObject {
        private long lastAccessed = System.currentTimeMillis();

        protected CacheObject(Class value) {
        }
    }

    public JarClassLoader() {

        timeToLive = 600 * 1000;
        cacheMap = new HashMap();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(timeToLive);
            } catch (InterruptedException ex) {
                System.err.println(ex);
                Thread.currentThread().interrupt();
            }
            cleanup();
        });
        t.setDaemon(true);
        t.start();
    }

    public void put(String key, Class value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }

    public List<Class> addClasses(String pathToJar) {
        if (!contains(pathToJar)) {
            List<Class> classes = loadClassesFromJar(pathToJar);
            for (Class cl : classes) {
                put(cl.getName(), cl);
            }
            return classes;
        } else {
            System.out.println("You've already added this jar.");
        }
        return null;
    }


    public Class updateClass(String name) throws ClassNotFoundException, NoSuchClassException {
        synchronized (cacheMap) {
            if (contains(name)) {
                Class clazz = Class.forName(name);
                final URL[] urls = new URL[1];
                urls[0] = clazz.getProtectionDomain().getCodeSource().getLocation();
                final ClassLoader delegateParent = clazz.getClassLoader().getParent();
                final URLClassLoader cl = new URLClassLoader(urls, delegateParent);
                Class clas = cl.loadClass(clazz.getName());
                cacheMap.put(name, new CacheObject(clas));
                return clas;
            } else {
                throw new NoSuchClassException("There is no such class in memory. Please add this class");
            }

        }
    }


    public boolean contains(String key) {
        synchronized (cacheMap) {
            CacheObject c = (CacheObject) cacheMap.get(key);
            if (c == null)
                return false;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return true;
            }
        }
    }


    private List<Class> loadClassesFromJar(String pathToJar) {
        List classes = new ArrayList();
        try {
            JarFile jarFile = new JarFile(pathToJar);
            Enumeration<JarEntry> e = jarFile.entries();

            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                System.out.println("Found class:  "
                        + je.getName().replaceAll("/", "\\."));
                try {
                    Class c = cl.loadClass(className);
                    classes.add(c);
                } catch (NoClassDefFoundError ignore) {
                    // this try catch clause used for class javax.jms which can not be loaded from maven
                    // tried for 100 times
                    // I swear :)
                }


            }


        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return classes;
    }

    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList deleteKey = null;

        synchronized (cacheMap) {
            Iterator itr = cacheMap.entrySet().iterator();

            deleteKey = new ArrayList((cacheMap.size() / 2) + 1);
            Object key = null;
            CacheObject c = null;

            while (itr.hasNext()) {
                key = itr.next();
                c = (CacheObject) itr.next();

                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (Object key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }

            Thread.yield();
        }
    }


    public static class NoSuchClassException extends Exception {
        public NoSuchClassException(String errorMessage) {
            super(errorMessage);
        }
    }
}
