package com.apps.mobile.utn.adtd.geco.Api;

/**
 * Created by Jorge Luis on 20/09/2016.
 */




import com.apps.mobile.utn.adtd.geco.Model.Person;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth_login")
    Call<TokenResponse> login(@Field("email") String email, @Field("password") String password);

    @GET("logout")
    Call<MessageResponse> logout(@Query("token") String token);


    @GET("youtube/searchByCompetence")
    Call<YoutubeResponse> getYoutubeList(@Query("token") String token);


    @GET("link/searchByCompetence")
    Call<LinkResponse> getLinkList(@Query("token") String token);

    @GET("getProfile")
    Call<PersonResponse> getProfile(@Query("token") String token);

    @PUT("updateProfile")
    Call<PersonResponse> updateProfile(@Query("token") String token, @Body Person person);
    /* @GET("test/getAll")
    Call<AlumnosResponse> getAll();

    @GET("test/getById")
    Call<AlumnosResponse> getAlumno(@Query("id") int id);

    @POST("test/insert")
    Call<APIResponse>insertAlumno(@Body Alumno alumno);

    @PUT("test/update")
    Call<APIResponse>updateAlumno(@Body Alumno alumno);

    @DELETE("test/delete")
    Call<APIResponse>deleteAlumno(@Query("id") int id);
   */
   /* @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
*/
    /*
    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
    */
}