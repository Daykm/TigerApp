package com.daykm.tiger.services;

import android.util.Base64;
import android.util.Log;

import com.daykm.tiger.realm.domain.TwitterServiceCredentials;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.realm.Realm;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class OAuth2Interceptor implements Interceptor {

    public static final String TAG = OAuth2Interceptor.class.getSimpleName();

    // https://dev.twitter.com/oauth/overview/authorizing-requests
    static final String OAUTH_SIGNATURE_METHOD_HMAC = "HMAC-SHA1";
    static final String OAUTH_VERSION_1_0 = "1.0";

    static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    static final String OAUTH_NONCE = "oauth_nonce";
    static final String OAUTH_SIGNATURE = "oauth_signature";
    static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    static final String OAUTH_TOKEN = "oauth_token";
    static final String OAUTH_VERSION = "oauth_version";
    static final String OAUTH_CALLBACK = "oauth_callback";

    static final String[] OAUTH_SIGNING_VALUES = {
            OAUTH_CALLBACK, OAUTH_CONSUMER_KEY, OAUTH_NONCE, OAUTH_SIGNATURE_METHOD, OAUTH_TIMESTAMP, OAUTH_TOKEN, OAUTH_VERSION
    };

    static final String HMAC_SHA1 = "HmacSHA1";

    public boolean isAuth;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String nonce = getRequestNonce();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);

        Realm realm = Realm.getDefaultInstance();
        TwitterServiceCredentials creds = realm.copyFromRealm(realm.where(TwitterServiceCredentials.class).findFirst());
        if(creds == null)
            creds = new TwitterServiceCredentials();
        realm.close();

        Map<String, String> parameters = new TreeMap<>(); // for ordering
        parameters.put(OAUTH_CONSUMER_KEY, percentEncode(creds.appToken));
        parameters.put(OAUTH_SIGNATURE_METHOD, percentEncode(OAUTH_SIGNATURE_METHOD_HMAC));
        parameters.put(OAUTH_TIMESTAMP, percentEncode(timestamp));
        parameters.put(OAUTH_NONCE, percentEncode(nonce));
        parameters.put(OAUTH_VERSION, percentEncode(OAUTH_VERSION_1_0));
        if (isAuth) {
            parameters.put(OAUTH_CALLBACK, percentEncode(TwitterApp.CALLBACK_URL));
        } else {
            parameters.put(OAUTH_TOKEN, percentEncode(creds.token));
        }

        Map<String, String> query = null;
        if (original.url().queryParameterNames() != null && !original.url().queryParameterNames().isEmpty()) {
            query = new TreeMap<>();
            for (String key : original.url().queryParameterNames()) {
                query.put(percentEncode(key), percentEncode(original.url().queryParameter(key)));
            }
        }

        String key = percentEncode(creds.appTokenSecret) + "&";
        if (creds.tokenSecret != null && !creds.tokenSecret.isEmpty()) {
            key += percentEncode(creds.tokenSecret);
        }

        HttpUrl url = original.url();

        String rebuild = url.scheme() + "://" + url.host() + url.encodedPath();
        Timber.i( rebuild);
        String signature = generateSignature(
                key,
                rebuild, original.method(), parameters, query
        );
        parameters.put(OAUTH_SIGNATURE, percentEncode(signature));

        String authorization = generateAuthorization(parameters);

        HttpUrl.Builder newurl = new HttpUrl.Builder().scheme("https")
                .host(original.url().host()).encodedPath(original.url().encodedPath());


        for (String paramName : original.url().queryParameterNames()) {
            Timber.i( "Parameter: " + paramName + " " + original.url().queryParameterValues(paramName).get(0));
            newurl.addEncodedQueryParameter(percentEncode(paramName), percentEncode(original.url().queryParameterValues(paramName).get(0)));
        }


        Timber.i( "Query: " + url.encodedQuery());

        Request.Builder requestBuilder = original.newBuilder()
                .header("User-Agent", TwitterApp.USER_AGENT)
                .header("Host", original.url().host())
                .header("Accept", "*/*")
                .header("Authorization", authorization)
                .method(original.method(), original.body())
                .url(newurl.build());


        Request request = requestBuilder.build();
        return chain.proceed(request);

    }


    private String generateSignature(String key, String url, String httpMethod, Map<String, String> oauthParameters, Map<String, String> query) {
        String base = httpMethod.toUpperCase() + "&" + percentEncode(url) + "&";

        StringBuilder builder = new StringBuilder();

        Map<String, String> parameters = new TreeMap<>();
        for (String signingValue : OAUTH_SIGNING_VALUES) {
            if (oauthParameters.get(signingValue) != null) {
                parameters.put(signingValue, oauthParameters.get(signingValue));
            }
        }
        if (query != null && !query.isEmpty()) {
            parameters.putAll(query);
        }
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        builder.deleteCharAt(builder.length() - 1);

        Timber.i( "Unsigned signature: " + builder.toString());
        return sign(key, base + percentEncode(builder.toString()));
    }

    private String generateAuthorization(Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder();
        builder.append("OAuth ");

        Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }


    SecureRandom random = new SecureRandom();
    Mac mac = null;


    // TODO: research this more
    private String getRequestNonce() {
        return new BigInteger(160, random).toString(32);
    }

    private String sign(String key, String data) {
        Timber.i( "Encoded signature: " + data);
        if (mac == null) {
            try {
                mac = Mac.getInstance("HmacSHA1");
            } catch (NoSuchAlgorithmException e) {
                // complain
            }
        }
        mac.reset();
        try {
            mac.init(new SecretKeySpec(key.getBytes(), HMAC_SHA1));
        } catch (InvalidKeyException e) {
            // complain
        }
        String signature = Base64.encodeToString(mac.doFinal(data.getBytes()), Base64.NO_WRAP);
        Timber.i( "Signature: " + signature);
        Timber.i( "Key: " + key);
        return signature;
    }

    // http://oauth.googlecode.com/svn/code/java/core/commons/src/main/java/net/oauth/OAuth.java
    /*
     * Copyright 2007 Netflix, Inc.
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *     http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    private static String percentEncode(String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, "UTF-8")
                    // OAuth encodes some characters differently:
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
            // This could be done faster with more hand-crafted code.
        } catch (UnsupportedEncodingException wow) {
            throw new RuntimeException(wow.getMessage(), wow);
        }
    }
}
