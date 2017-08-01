package com.loktra.githubcommits.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ayushdeothia on 02/07/17.
 */

public interface SearchQuery {

    @GET("/search/commits?q=repo:rails/rails+css")
    Call<ResponseBody> getSearchQuery();
}
