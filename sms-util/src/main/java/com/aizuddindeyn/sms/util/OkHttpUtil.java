/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.util;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public class OkHttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpUtil.class);

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    private OkHttpUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers, Long timeout)
            throws IOException {

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        return call(url, headers, true, builder.build(), timeout);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers, Long timeout)
            throws IOException {

        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            httpBuilder.addQueryParameter(param.getKey(), param.getValue());
        }

        String targetUrl = httpBuilder.build().toString();

        return call(targetUrl, headers, false, null, timeout);
    }

    private static String call(String url, Map<String, String> headers, boolean isPost, FormBody body, Long timeout)
            throws IOException {
        Request.Builder builder = new Request.Builder();

        Assert.notNull(url, "URL must be provided");
        builder.url(url);

        if (headers != null) {
            Headers headerBuilder = Headers.of(headers);
            builder.headers(headerBuilder);
        }

        if (isPost) {
            Assert.notNull(body, "Post body must be provided");
            builder.post(body);
        }

        Request request = builder.build();
        OkHttpClient client = createClient(timeout);

        LogLevelHelper.logDebug(LOGGER, "Start http call to {}", url);
        Response response = client.newCall(request).execute();

        String responseStr = response.body().string();
        LogLevelHelper.logDebug(LOGGER, "Http call {}, code: {}, response: {}", url, response.code(),
                responseStr);

        return responseStr;
    }

    private static OkHttpClient createClient(Long timeout) {
        if (timeout != null) {
            return HTTP_CLIENT.newBuilder().readTimeout(timeout, TimeUnit.SECONDS).build();
        } else {
            return HTTP_CLIENT.newBuilder().build();
        }
    }
}
