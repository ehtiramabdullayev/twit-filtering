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
import static org.interview.oauth.util.AppConstants.REQUIRED_TWIT_COUNT;
import static org.interview.oauth.util.AppConstants.TWIT_FETCHING_TIME;

public class HttpHelper {
    public static List<String> sendUrlRequestWithParams(HttpRequestFactory httpRequestFactory,
                                                 String url,
                                                 Map<String, String> params) {

        GenericUrl genericUrl = new GenericUrl(url);
        if (Objects.nonNull(params) && !params.isEmpty()) params.forEach(genericUrl::set);
        List<String> twits = null;
        try {
            //use duration class
            HttpRequest httpRequest = httpRequestFactory
                    .buildGetRequest(genericUrl)
                    .setConnectTimeout((int) Duration.ofSeconds(TWIT_FETCHING_TIME).toMillis());

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


    private static List<String> fetchStreamLineByLine(InputStream gzipInputStream) throws IOException {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        AtomicBoolean receiveUntil = new AtomicBoolean(true);

        scheduledExecutor.schedule(() -> {
            receiveUntil.set(false);
        }, TWIT_FETCHING_TIME, TimeUnit.SECONDS);

        List<String> twits = new ArrayList<>(REQUIRED_TWIT_COUNT);
        try (gzipInputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream));
            int twitsReceived = 0;

            while ((twitsReceived <= REQUIRED_TWIT_COUNT) && receiveUntil.get()) {

                String message = reader.readLine();

                twits.add(message);
                twitsReceived++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            scheduledExecutor.shutdownNow();
        }

        return twits;
    }

}
