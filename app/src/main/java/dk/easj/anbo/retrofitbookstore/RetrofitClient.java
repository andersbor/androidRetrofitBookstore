package dk.easj.anbo.retrofitbookstore;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// https://code.tutsplus.com/tutorials/sending-data-with-retrofit-2-http-client-for-android--cms-27845
// Singleton design patter, lazy load
class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    // https://futurestud.io/tutorials/retrofit-2-adding-customizing-the-gson-converter
                    // Gson is no longer the default converter
                    .build();
        }
        return retrofit;
    }
}

