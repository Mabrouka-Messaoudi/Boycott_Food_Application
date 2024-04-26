package com.example.boycott_food.view;
import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OpenFoodFactsAPI {

    public interface OnProductInfoListener {
        void onSuccess(String brandName, String productName);
        void onFailure(String message);
    }

    private static final String BASE_URL = "https://world.openfoodfacts.org/api/v0/product/";

    public void getProductInformation(Context context, String barcode, OnProductInfoListener listener) {
        String apiUrl = BASE_URL + barcode + ".json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onFailure("Erreur lors de la récupération des informations du produit");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONObject product = json.getJSONObject("product");
                        String brandName = product.optString("brands");
                        String productName = product.optString("product_name");

                        listener.onSuccess(brandName, productName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onFailure("Erreur lors de l'analyse de la réponse du serveur");
                    }
                } else {
                    listener.onFailure("Code-barres non trouvé");
                }
            }
        });
    }
}
