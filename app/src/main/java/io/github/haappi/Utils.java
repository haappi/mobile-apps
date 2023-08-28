package io.github.haappi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static CompletableFuture<JSONObject> doApiCall(String url, Context context) {
        CompletableFuture<JSONObject> future = new CompletableFuture<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                future::complete,
                Throwable::printStackTrace);

        Volley.newRequestQueue(context).add(request); // line 4 of AndroidManifest.xml

        return future;
    }

    public static String createQueryString(HashMap<String, Object> params) { // i made these :}
        StringBuilder queryString = new StringBuilder();
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
                String value = URLEncoder.encode(sanitizeQueryParam(entry.getValue().toString()), StandardCharsets.UTF_8);
                queryString.append(key).append("=").append(value).append("&");
            }
            if (!queryString.toString().isEmpty()) {
                queryString.deleteCharAt(queryString.length() - 1); // remove trailing "&"
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryString.toString();
    }

    public static String sanitizeQueryParam(Object value) {
        return value.toString().replaceAll("[^a-zA-Z0-9-]", ""); // only allow alphanumeric characters and dashes
    }
}
