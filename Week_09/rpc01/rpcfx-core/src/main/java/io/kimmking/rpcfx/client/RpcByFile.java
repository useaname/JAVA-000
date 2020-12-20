package io.kimmking.rpcfx.client;

import io.kimmking.rpcfx.common.RpcConstant;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//TODO 未完成
public final class RpcByFile {

    public static <T> T create(final Class<T> serviceClass, final String url) {
        // 0. 替换动态代理 -> AOP
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcByFile.RpcFileInvocationHandler(serviceClass, url, RpcConstant.OBJECT_SERIALIZABLE_FILE_PATH));
    }

    public static class RpcFileInvocationHandler implements InvocationHandler {

        private final Class<?> serviceClass;
        private final String url;
        private final String filePath;

        public <T> RpcFileInvocationHandler(Class<T> serviceClass, String url, String filePath) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filePath = filePath;
        }




        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcfxRequest rpcfxRequest = new RpcfxRequest();
            rpcfxRequest.setServiceClass(this.serviceClass.getName());
            rpcfxRequest.setMethod(method.getName());
            rpcfxRequest.setParams(rpcfxRequest.getParams());

            RpcfxResponse  rpcFileRes = noticeServer(rpcfxRequest , url, filePath);
            return null;
        }

        private RpcfxResponse noticeServer(RpcfxRequest rpcfxRequest,String url, String filePath) throws IOException {
            // 序列化到文件
            // "/Users/chengxiaoqi/temp/target"
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(rpcfxRequest);
            objectOutputStream.flush();
            objectOutputStream.close();

            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder().url(url).get().build();

            String responseResult = client.newCall(request).execute().toString();
            return null;
        }
    }
}
