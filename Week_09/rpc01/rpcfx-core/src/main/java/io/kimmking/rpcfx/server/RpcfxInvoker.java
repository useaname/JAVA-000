package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.exception.RpcCommonException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcfxInvoker implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver){
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) throws ClassNotFoundException {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

        // 作业1：改成泛型和反射
        Class<?> clz = Class.forName(serviceClass);
        Object service = applicationContext.getBean(clz);
        Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
        Object result = null;
        try {
            result = method.invoke(service, request.getParams());
            response.setStatus(true);
            response.setResult(result);
            return response;
        } catch (IllegalAccessException  | InvocationTargetException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            response.setException(new RpcCommonException("5001", "服务端异常"));
            response.setStatus(false);
            return response;
        }


//        Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);
//
//        try {
//            Method method = resolveMethodFromClass(service.getClass(), request.esgetMethod());
//            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
//            // 两次json序列化能否合并成一个
//            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
//            response.setStatus(true);
//            return response;
//        } catch ( IllegalAccessException | InvocationTargetException e) {
//
//            // 3.Xstream
//
//            // 2.封装一个统一的RpcfxException
//            // 客户端也需要判断异常
//            e.printStackTrace();
//            response.setException(e);
//            response.setStatus(false);
//            return response;
//        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
