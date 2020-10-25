package Week_02;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

        import java.io.IOException;

public class Q2 {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:8808/test");

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println("响应状态码：" + response.getStatusLine());
            String result = entity.getContent().toString();
            System.out.println("返回内容：" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
