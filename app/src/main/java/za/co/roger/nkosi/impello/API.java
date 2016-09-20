package za.co.roger.nkosi.impello;


import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by gracepinto on 9/15/16.
 */
public interface API {
    @FormUrlEncoded
    @POST("/index.php")
    void getProducts(@FieldMap Map<String, String> map, Callback<String> callback);

    @FormUrlEncoded
    @POST("/index.php")
    void getCart(@FieldMap Map<String, String> map, Callback<String> callback);
}
