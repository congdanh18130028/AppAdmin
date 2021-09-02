package com.example.adminapp.api;

import com.example.adminapp.models.Bill;
import com.example.adminapp.models.BillDetails;
import com.example.adminapp.models.Category;
import com.example.adminapp.models.FilePath;
import com.example.adminapp.models.Product;
import com.example.adminapp.models.ProductEdit;
import com.example.adminapp.models.Token;
import com.example.adminapp.models.User;
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
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

//"http://192.168.31.237:45455"
public interface ApiServices {

    Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();
    
    ApiServices apiService = new Retrofit.Builder()
            .baseUrl("https://chaudecor.tk")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices.class);


    @Multipart
    @POST("api/products")
    Call<Product> addProducts(@Header("Authorization") String Aut,
                              @Part("name")RequestBody name,
                              @Part("category")RequestBody category,
                              @Part MultipartBody.Part file,
                              @Part("description")RequestBody description,
                              @Part("quantity")RequestBody quantity,
                              @Part("price")RequestBody price);

    @Multipart
    @POST("api/products/img")
    Call<FilePath> addImg(@Header("Authorization") String Aut,
                          @Part MultipartBody.Part file);

    @GET("api/products/categories")
    Call<List<Category>> getCategories();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/products/category")
    Call<Void> addCategory(@Header("Authorization") String Aut,
                           @Query("categoryName") String categoryName);

    @GET("api/products")
    Call<List<Product>> getProducts();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("api/products/{id}")
    Call<Void> deleteProduct(@Header("Authorization") String Aut,
                             @Path("id") int id);

    @GET("api/products/{id}")
    Call<Product> getProduct(@Path("id") int id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PATCH("api/products/{id}")
    Call<Void> updateProduct(@Header("Authorization") String Aut,
                             @Path("id") int id,
                             @Body List<ProductEdit> list);

//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @FormUrlEncoded
    @PATCH("api/products/img/{id}")
    Call<Void> updateImgProduct(@Header("Authorization") String Aut,
                                @Path("id") int id,
                                @Field("link") String link);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/users")
    Call<List<User>> getUsers(@Header("Authorization") String Aut);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Header("Authorization") String Aut,
                          @Path("id") int id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/bills/state/{state}")
    Call<List<Bill>> getListBillConfirm(@Header("Authorization") String Aut,
                                        @Path("state") int state);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/bills/billDetails/{billId}")
    Call<List<BillDetails>> getListBillDetails(@Header("Authorization") String Aut,
                                               @Path("billId") int billId);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PATCH("api/bills/state")
    Call<Void> confirmBill(@Header("Authorization") String Aut,
                           @Query("billId") int billId,
                           @Query("state") int sate);

    @POST("api/login")
    Call<Token> authenticateUser(@Query("email") String email,
                                 @Query("password") String password);

    @POST("api/users/createAdmin")
    Call<User> createAdmin();
}
