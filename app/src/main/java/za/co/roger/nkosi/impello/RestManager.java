package za.co.roger.nkosi.impello;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by gracepinto on 9/15/16.
 */
public class RestManager {
    private API api;
    public API getApi(){
        if (api == null){
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(String.class, new StringDesirializer());
            api = new RestAdapter.Builder()
                    .setEndpoint(Constants.BASE_URL)
                    .setConverter(new GsonConverter(gsonBuilder.create()))
                    .build()
                    .create(API.class);
        }
        return api;
    }
}
