import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.util.Timeout;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String key = "afaJissfL0p9NYeZ6Og1KcNCx9Q1jpMtJKNtf46l";
        String url = "https://api.nasa.gov/planetary/apod?api_key=" +
                key + "&date=2024-04-28";

        CloseableHttpClient httpclient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(Timeout.ofMilliseconds(5000))
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);


        ObjectMapper mapper = new ObjectMapper();
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

        String[] splitteURL = answer.url.split("/");
        HttpGet imageRequest = new HttpGet(answer.url);
        CloseableHttpResponse image = httpclient.execute(imageRequest);

        FileOutputStream fos = new FileOutputStream("images/" + splitteURL[splitteURL.length - 1]);
        image.getEntity().writeTo(fos);
    }
}
