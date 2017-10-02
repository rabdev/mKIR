package com.mkir.interfaces;

import com.mkir.ServerRequest;
import com.mkir.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nyulg on 2017. 06. 20..
 */

public interface LoginInterface {

    @POST("homokozo/mm2/index.php")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
