package io.coinclave.crypto.applet.update.network.models;

import com.google.gson.annotations.SerializedName;

public class Token {

  @SerializedName("access_token")
  private String accessToken;

  public String getAccessToken() {
    return accessToken;
  }
}
