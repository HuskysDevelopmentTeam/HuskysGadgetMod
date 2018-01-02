package net.thegaminghuskymc.gadgetmod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Our Custom Class Loader to load the classes. Any class in the com.journaldev
 * package will be loaded using this ClassLoader. For other classes, it will
 * delegate the request to its Parent ClassLoader.
 *
 */
public class RemoteClassLoader extends ClassLoader {

    Map<String, Class> classes = new HashMap<>();

    /**
     * This constructor is used to set the parent ClassLoader
     */
    public RemoteClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * Loads the class from the file system. The class file should be located in
     * the file system. The name should be relative to get the file location
     *
     * @param name
     *            Fully Classified name of class, for example com.journaldev.Foo
     */
    private Class getClass(URL url, String name) throws ClassNotFoundException {
        byte[] b = null;
        try {
            // This loads the byte code data from the file
            b = loadClassFileData(url);
            // defineClass is inherited from the ClassLoader class
            // that converts byte array into a Class. defineClass is Final
            // so we cannot override it
            Class c = defineClass(name, b, 0, b.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Every request for a class passes through this method. If the class is in
     * com.journaldev package, we will use this classloader or else delegate the
     * request to parent classloader.
     *
     *
     * @param urlS
     *            Full class url
     */
    @Override
    public Class loadClass(String urlS) throws ClassNotFoundException {
        try {
            if (classes.containsKey(urlS)) return classes.get(urlS);
            URL url = null;
            url = new URL(urlS);
            return getClass(url, url.getPath().replace("/", "").replace(".class", ""));
        } catch (Exception e) {
            if (!(e instanceof MalformedURLException)) e.printStackTrace();
            return getParent().loadClass(urlS);
        }
    }

    /**
     * makes you be able to load fresh this class again
     */
    public void removeFromCache(String url) {
        if (classes.containsKey(url)) classes.remove(url);
    }

    /**
     * Reads the file (.class) into a byte array. The file should be
     * accessible as a resource and make sure that its not in Classpath to avoid
     * any confusion.
     *
     * @param url
     *            class files url
     * @return Byte array read from the file
     * @throws IOException
     *             if any exception comes in reading the file
     */
    private byte[] loadClassFileData(URL url) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = url.openStream ();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ( (n = is.read(byteChunk)) > 0 ) {
                baos.write(byteChunk, 0, n);
            }
        }
        catch (IOException e) {
            e.printStackTrace ();
            // Perform any other exception handling that's appropriate.
        }
        finally {
            if (is != null) { is.close(); }
        }
        return baos.toByteArray();
    }
}