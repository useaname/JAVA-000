package Week_01;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;

/**
 *
 * 第一周-第二题
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，
 * 执行 hello 方法，此文件内 容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 *
 */
public class Q2 extends ClassLoader{

    static final String XHELLO_CLAZZ_PATH = "./Week_01/Hello.xlass";
    static final String HELLO_CLAZZ_NAME = "Hello";
    static final String HELLO_CLAZZ_METHOD_NAME = "hello";

    public static void main(String[] args) {
        try {
            Class<?> helloClazz = new Q2().findClass(HELLO_CLAZZ_NAME);
            Method helloMethod = helloClazz.getMethod(HELLO_CLAZZ_METHOD_NAME);
            helloMethod.invoke(helloClazz.newInstance());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Class<?> findClass(String name) {
        try {
            byte[] xHelloClassBytes = getFileBytes(XHELLO_CLAZZ_PATH);
            byte[] formatClassBytes = new byte[xHelloClassBytes.length];
            for (int i = 0; i < xHelloClassBytes.length; i++) {
                formatClassBytes[i] = (byte) (255-xHelloClassBytes[i]);
            }
            return defineClass(name, formatClassBytes, 0, formatClassBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定文件的 byte 数组
     * @param location 文件路径
     * @return 文件 byte 数组
     * @throws IOException IOException
     */
    public static byte[] getFileBytes(String location) throws IOException {
        File file = new File(location);
        return Files.readAllBytes(file.toPath());
    }
}

