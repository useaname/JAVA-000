package io.kimmking.rpcfx.server;


import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.common.RpcConstant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RpcFileInvoker {

    private final RpcfxResolver resolver;
//    private final String fileName;


    RpcFileInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoker() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(RpcConstant.OBJECT_SERIALIZABLE_FILE_PATH);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object service = objectInputStream.readObject();

        RpcfxResponse response = new RpcfxResponse();
        
        return null;
    }
}
