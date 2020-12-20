package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.Method;

public final class Rpcfx {

    public static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {

        // 0. 替换动态代理 -> AOP
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                RpcfxRequest request = new RpcfxRequest();
                request.setServiceClass(serviceClass.getName());
                request.setMethod(method.getName());
                request.setParams(objects);

                RpcfxResponse response = post(request, url);
                if (!response.isStatus()) {
                    throw response.getException();
                }
                return response.getResult();
            }

            private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
                String reqJson = JSON.toJSONString(req);
                System.out.println("req json: "+reqJson);

                // 1.可以复用client
                // 2.尝试使用httpclient或者netty client
                OkHttpClient client = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MEDIA_TYPE, reqJson))
                        .build();
                String respJson = client.newCall(request).execute().body().string();
                System.out.println("resp json: "+respJson);
                return JSON.parseObject(respJson, RpcfxResponse.class);
            }
        });
        return (T) enhancer.create();
    }
}
