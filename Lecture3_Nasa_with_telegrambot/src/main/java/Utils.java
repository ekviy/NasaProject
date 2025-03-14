import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;

public class Utils {
    public static String getImageUrl(String url) {
        CloseableHttpClient httpclient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(Timeout.ofMilliseconds(5000))
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet httpGet = new HttpGet(url);

        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);

            ObjectMapper mapper = new ObjectMapper();
            NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

            String imageUrl = answer.url;

            return imageUrl;
        } catch (IOException e) {
            return "Server NASA does not respond!";
        }
    }
}
