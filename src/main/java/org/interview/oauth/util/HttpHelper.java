package org.interview.oauth.util;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import static com.google.api.client.http.HttpStatusCodes.STATUS_CODE_OK;

public class HttpHelper {
    public List<String> sendUrlRequestWithParams(HttpRequestFactory httpRequestFactory,
                                                 String url,
                                                 Map<String, String> params) {
        GenericUrl genericUrl = new GenericUrl(url);
        if (Objects.nonNull(params) && !params.isEmpty()) params.forEach(genericUrl::set);
        List<String> twits = null;
        HttpRequest httpRequest;
        try {
            //use duration class
            httpRequest = httpRequestFactory.buildGetRequest(genericUrl).setConnectTimeout((int) Duration.ofSeconds(30).toMillis());
            HttpResponse httpResponse = httpRequest.execute();
            if (httpResponse.getStatusCode() == STATUS_CODE_OK) {
                InputStream content = httpResponse.getContent();
                twits = fetchStreamLineByLine(content);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return twits;
    }

    private List<String> fetchStreamLineByLine(InputStream gzipInputStream) throws IOException {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        AtomicBoolean receiveUntil = new AtomicBoolean(true);

        scheduledExecutor.schedule(() -> {
            receiveUntil.set(false);
        }, 30, TimeUnit.SECONDS);

        List<String> twits = new ArrayList<>(100);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream));
            int twitsReceived = 0;

            while ((twitsReceived <= 100) && receiveUntil.get()) {
                String message = reader.readLine();
                twits.add(message);
                twitsReceived++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            scheduledExecutor.shutdownNow();
            gzipInputStream.close();
        }
        return twits;
    }
}
