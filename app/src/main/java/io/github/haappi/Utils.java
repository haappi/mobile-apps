package io.github.haappi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.CharArrayWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Utils {

    static BitSet dontNeedEncoding;
    static final int caseDiff = ('a' - 'A');
    static String dfltEncName = null;

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static CompletableFuture<JSONObject> doApiCall(String url, Context context) {
        CompletableFuture<JSONObject> future = new CompletableFuture<>();
        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        future::complete,
                        Throwable::printStackTrace);

        Volley.newRequestQueue(context).add(request); // line 4 of AndroidManifest.xml

        return future;
    }

    public static String createQueryString(HashMap<String, Object> params) { // i made these :}
        StringBuilder queryString = new StringBuilder();
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = encode(entry.getKey(), StandardCharsets.UTF_8);
                String value =
                        encode(
                                sanitizeQueryParam(entry.getValue().toString()),
                                StandardCharsets.UTF_8);
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
        return value.toString()
                .replaceAll("[^a-zA-Z0-9-]", ""); // only allow alphanumeric characters and dashes
    }

    /**
     * Translates a string into {@code application/x-www-form-urlencoded}
     * format using a specific {@linkplain java.nio.charset.Charset Charset}.
     * This method uses the supplied charset to obtain the bytes for unsafe
     * characters.
     * <p>
     * <em><strong>Note:</strong> The <a href=
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that
     * UTF-8 should be used. Not doing so may introduce incompatibilities.</em>
     *
     * @param   s   {@code String} to be translated.
     * @param charset the given charset
     * @return  the translated {@code String}.
     * @throws NullPointerException if {@code s} or {@code charset} is {@code null}.
     * @since 10
     * @author Fancy people who made URLEncoder
     */
    public static String encode(String s, Charset charset) {
        Objects.requireNonNull(charset, "charset");

        boolean needToChange = false;
        StringBuilder out = new StringBuilder(s.length());
        CharArrayWriter charArrayWriter = new CharArrayWriter();

        for (int i = 0; i < s.length(); ) {
            int c = (int) s.charAt(i);
            // System.out.println("Examining character: " + c);
            if (dontNeedEncoding.get(c)) {
                if (c == ' ') {
                    c = '+';
                    needToChange = true;
                }
                // System.out.println("Storing: " + c);
                out.append((char) c);
                i++;
            } else {
                // convert to external encoding before hex conversion
                do {
                    charArrayWriter.write(c);
                    /*
                     * If this character represents the start of a Unicode
                     * surrogate pair, then pass in two characters. It's not
                     * clear what should be done if a byte reserved in the
                     * surrogate pairs range occurs outside of a legal
                     * surrogate pair. For now, just treat it as if it were
                     * any other character.
                     */
                    if (c >= 0xD800 && c <= 0xDBFF) {
                        /*
                          System.out.println(Integer.toHexString(c)
                          + " is high surrogate");
                        */
                        if ((i + 1) < s.length()) {
                            int d = (int) s.charAt(i + 1);
                            /*
                              System.out.println("\tExamining "
                              + Integer.toHexString(d));
                            */
                            if (d >= 0xDC00 && d <= 0xDFFF) {
                                /*
                                  System.out.println("\t"
                                  + Integer.toHexString(d)
                                  + " is low surrogate");
                                */
                                charArrayWriter.write(d);
                                i++;
                            }
                        }
                    }
                    i++;
                } while (i < s.length() && !dontNeedEncoding.get((c = (int) s.charAt(i))));

                charArrayWriter.flush();
                String str = new String(charArrayWriter.toCharArray());
                byte[] ba = str.getBytes(charset);
                for (int j = 0; j < ba.length; j++) {
                    out.append('%');
                    char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
                    // converting to use uppercase letter as part of
                    // the hex value if ch is a letter.
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                    ch = Character.forDigit(ba[j] & 0xF, 16);
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                }
                charArrayWriter.reset();
                needToChange = true;
            }
        }

        return (needToChange ? out.toString() : s);
    }
}
