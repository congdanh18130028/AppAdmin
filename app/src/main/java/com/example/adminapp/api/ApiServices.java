package com.example.adminapp.api;

import com.example.adminapp.models.Category;
import com.example.adminapp.models.FilePath;
import com.example.adminapp.models.Product;
import com.example.adminapp.models.ProductEdit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiServices {

    Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();
    
    ApiServices apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.31.237:45455")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices.class);

    @Multipart
    @POST("api/products")
    Call<Product> addProducts(@Part("name")RequestBody name,
                              @Part("category")RequestBody category,
                              @Part MultipartBody.Part file,
                              @Part("description")RequestBody description,
                              @Part("quantity")RequestBody quantity,
                              @Part("price")RequestBody price);

    @Multipart
    @POST("api/products/img")
    Call<FilePath> addImg(@Part MultipartBody.Part file);

    @GET("api/products/categories")
    Call<List<Category>> getCategories();

    @POST("api/products/category")
    Call<Void> addCategory(@Query("categoryName") String categoryName);

    @GET("api/products")
    Call<List<Product>> getProducts();

    @DELETE("api/products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

    @GET("api/products/{id}")
    Call<Product> getProduct(@Path("id") int id);

    @PATCH("api/products/{id}")
    Call<Void> updateProduct(@Path("id") int id,
                             @Body List<ProductEdit> list);

    @FormUrlEncoded
    @PATCH("api/products/img/{id}")
    Call<Void> updateImgProduct(@Path("id") int id,
                                @Field("link") String link);
}
