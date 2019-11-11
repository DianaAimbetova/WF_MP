package classloader;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;


public class OwnClassLoader extends ClassLoader {
    private int timeToLive;
    private final LRUMap cacheMap;
    private static Logger logger = LogManager.getLogger(OwnClassLoader.class);

    protected class CacheObject {
        private long lastAccessed = System.currentTimeMillis();

        protected CacheObject(Class value) {
        }
    }

    public OwnClassLoader() {

        timeToLive = 600 * 1000;
        cacheMap = new LRUMap();
        if (timeToLive > 0) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(timeToLive);
                } catch (InterruptedException ex) {
                    logger.error(ex);
                    Thread.currentThread().interrupt();
                }
                cleanup();
            });
            t.setDaemon(true);
            t.start();
        }
    }

    public void put(String key, Class value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }

    public Class addClass(String name) throws ClassNotFoundException {
        if (!contains(name)) {
            Class cl = findClass(name);
            put(name, cl);
            return cl;
        } else {
            logger.info("You've already added this class.");
        }
        return null;
    }


    public Class updateClass(String name) throws ClassNotFoundException {
        Class clazz = Class.forName(name);
        final URL[] urls = new URL[1];
        urls[0] = clazz.getProtectionDomain().getCodeSource().getLocation();
        final ClassLoader delegateParent = clazz.getClassLoader().getParent();
        try (final URLClassLoader cl = new URLClassLoader(urls, delegateParent)) {
            return cl.loadClass(clazz.getName());
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
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

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bt = loadClassFromFile(name);
        return defineClass(name, bt, 0, bt.length);
    }


    private byte[] loadClassFromFile(String className) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/") + ".class");
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        int len = 0;
        try {
            while ((len = is.read()) != -1) {
                byteSt.write(len);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return byteSt.toByteArray();
    }

    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList deleteKey = null;

        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();

            deleteKey = new ArrayList((cacheMap.size() / 2) + 1);
            Object key = null;
            CacheObject c = null;

            while (itr.hasNext()) {
                key = itr.next();
                c = (CacheObject) itr.getValue();

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


}
