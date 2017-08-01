package com.loktra.githubcommits.api;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ayushdeothia on 02/07/17.
 */

public class RestClient {

    private static OkHttpClient mClient;


    private RestClient() {
        mClient = new OkHttpClient();
    }

    public static OkHttpClient getClientInstance(final Context context) {

        //creating request Interceptor
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request newRequest;
                newRequest = original.newBuilder()
                        .header("Accept", "application/vnd.github.cloak-preview")
                        .build();
                return chain.proceed(newRequest);
            }
        };

        if (mClient == null) {
            synchronized (RestClient.class) {
                if (mClient == null) {
                    mClient = new OkHttpClient().newBuilder()
                            .readTimeout(60, TimeUnit.SECONDS)
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(requestInterceptor)
                            .build();


                }
            }
        }
        return mClient;
    }

}
