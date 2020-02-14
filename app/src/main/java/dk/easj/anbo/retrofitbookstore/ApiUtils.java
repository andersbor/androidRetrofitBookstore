package dk.easj.anbo.retrofitbookstore;

// https://code.tutsplus.com/tutorials/sending-data-with-retrofit-2-http-client-for-android--cms-27845
// Singleton design pattern
class ApiUtils {
    private ApiUtils() {
    }

    public static final String BASE_URL = "http://anbo-restserviceproviderbooks.azurewebsites.net/Service1.svc/";

    public static BookStoreService getBookStoreService() {

        return RetrofitClient.getClient(BASE_URL).create(BookStoreService.class);
    }
}
