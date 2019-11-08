package classloader;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * Custom ClassLoader by extension of the standard ClassLoader.
 * @author Diana_Aimbetova
 * @version 4.0
 */
public class OwnClassLoader extends ClassLoader {
    private long timeToLive;
    private LRUMap cacheMap;


    protected class CacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public Class value;

        protected CacheObject(Class value) {
            this.value = value;
        }
    }

    public OwnClassLoader() {

        timeToLive = 600 * 1000;
        cacheMap = new LRUMap();

            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(timeToLive);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });

            t.setDaemon(true);
            t.start();

    }

    public void put(String key, Class value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }

    public Class addClass(String name) throws ClassNotFoundException {
        if(!contains(name)) {
            Class cl = findClass(name);
            put(name,cl);
            return cl;
        } else{
            System.out.println("You've already added this class.");
        }
        return null;
    }


    public Class updateClass(String name) throws ClassNotFoundException {
        Class clazz = Class.forName(name);
        final URL[] urls = new URL[1];
        urls[0] =  clazz.getProtectionDomain().getCodeSource().getLocation();
        final ClassLoader delegateParent = clazz.getClassLoader().getParent();
        try (final URLClassLoader cl = new URLClassLoader(urls, delegateParent)) {
            final Class<?> reloadedClazz = cl.loadClass(clazz.getName());
            return reloadedClazz;
        } catch (IOException e) {
            e.printStackTrace();
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

    /**
     * Overrided method from ClassLoader it returns class
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bt = loadClassFromFile(name);
        return defineClass(name, bt, 0, bt.length);
    }

    /**
     * Custom method to read class and returns it as array of bytes
     * @param className
     * @return
     */
    private byte[] loadClassFromFile(String className) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/")+".class");
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        int len =0;
        try {
            while((len=is.read())!=-1){
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteSt.toByteArray();
    }

    public void cleanup(String name) {
        synchronized (cacheMap) {
            CacheObject c = (CacheObject) cacheMap.get(name);
            if (c != null) {
                cacheMap.remove(name);
            } else {
                c.lastAccessed = System.currentTimeMillis();
            }
        }
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
