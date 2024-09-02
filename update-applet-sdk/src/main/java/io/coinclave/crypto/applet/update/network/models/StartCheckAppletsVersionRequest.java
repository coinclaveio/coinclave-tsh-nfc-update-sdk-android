package io.coinclave.crypto.applet.update.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * StartCheckAppletsVersionRequest
 */

public class StartCheckAppletsVersionRequest {

  @SerializedName("appletName")
  private String appletName;

  public StartCheckAppletsVersionRequest appletName(String appletName) {
    this.appletName = appletName;
    return this;
  }

  /**
   * Get appletName
   * @return appletName
  */
  public String getAppletName() {
    return appletName;
  }

  public void setAppletName(String appletName) {
    this.appletName = appletName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StartCheckAppletsVersionRequest startCheckAppletsVersionRequest = (StartCheckAppletsVersionRequest) o;
    return Objects.equals(this.appletName, startCheckAppletsVersionRequest.appletName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appletName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StartCheckAppletsVersionRequest {\n");
    sb.append("    appletName: ").append(toIndentedString(appletName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

