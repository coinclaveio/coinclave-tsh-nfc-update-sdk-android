/*
 * Internal API of remote card management service
 * Internal API of remote card management service
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.coinclave.crypto.applet.update.network.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

/**
 * CheckAppletVersionResponse
 */
public class CheckAppletVersionData implements Serializable {
  @SerializedName("newVersion")
  private String newVersion;

  @SerializedName("oldVersion")
  private String oldVersion;

  public static final String SERIALIZED_NAME_NEED_UPDATE = "needUpdate";
  @SerializedName(SERIALIZED_NAME_NEED_UPDATE)
  private Boolean needUpdate;

  public static final String SERIALIZED_NAME_CRITICAL = "critical";
  @SerializedName(SERIALIZED_NAME_CRITICAL)
  private Boolean critical;

  public CheckAppletVersionData() {
  }

  public CheckAppletVersionData newVersion(String newVersion) {

    this.newVersion = newVersion;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  public String getNewVersion() {
    return newVersion;
  }

  public String getOldVersion() {
    return oldVersion;
  }


  public void setNewVersion(String version) {
    this.newVersion = version;
  }

  public void setOldVersion(String version) {
    this.oldVersion = version;
  }


  public CheckAppletVersionData needUpdate(Boolean needUpdate) {
    
    this.needUpdate = needUpdate;
    return this;
  }

   /**
   * Get needUpdate
   * @return needUpdate
  **/
  public Boolean getNeedUpdate() {
    return needUpdate;
  }


  public void setNeedUpdate(Boolean needUpdate) {
    this.needUpdate = needUpdate;
  }


  public CheckAppletVersionData critical(Boolean critical) {
    
    this.critical = critical;
    return this;
  }

   /**
   * Get critical
   * @return critical
  **/
  public Boolean getCritical() {
    return critical;
  }


  public void setCritical(Boolean critical) {
    this.critical = critical;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CheckAppletVersionData checkAppletVersionResponse = (CheckAppletVersionData) o;
    return Objects.equals(this.newVersion, checkAppletVersionResponse.newVersion) &&
        Objects.equals(this.oldVersion, checkAppletVersionResponse.oldVersion) &&
        Objects.equals(this.needUpdate, checkAppletVersionResponse.needUpdate) &&
        Objects.equals(this.critical, checkAppletVersionResponse.critical);
  }

  @Override
  public int hashCode() {
    return Objects.hash(newVersion, oldVersion, needUpdate, critical);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CheckAppletVersionData {\n");
    sb.append("    newVersion: ").append(toIndentedString(newVersion)).append("\n");
    sb.append("    oldVersion: ").append(toIndentedString(oldVersion)).append("\n");
    sb.append("    needUpdate: ").append(toIndentedString(needUpdate)).append("\n");
    sb.append("    critical: ").append(toIndentedString(critical)).append("\n");
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

