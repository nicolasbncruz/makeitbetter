package com.optic.makeitbetter.providers;

import com.optic.makeitbetter.models.FCMBody;
import com.optic.makeitbetter.models.FCMResponse;
import com.optic.makeitbetter.retrofit.IFCMApi;
import com.optic.makeitbetter.retrofit.RetrofitClient;

import retrofit2.Call;

public class NotificationProvider {

    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {

    }

    public Call<FCMResponse> sendNotification(FCMBody body) {
        return RetrofitClient.getClient(url).create(IFCMApi.class).send(body);
    }

}
