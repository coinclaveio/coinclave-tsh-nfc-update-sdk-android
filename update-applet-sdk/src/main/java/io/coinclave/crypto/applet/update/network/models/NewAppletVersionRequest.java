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

import java.util.Objects;

/**
 * NewAppletVersionRequest
 */
public class NewAppletVersionRequest {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_VERSION = "version";
  @SerializedName(SERIALIZED_NAME_VERSION)
  private String version;

  public static final String SERIALIZED_NAME_FILE_NAME = "fileName";
  @SerializedName(SERIALIZED_NAME_FILE_NAME)
  private String fileName;

  public static final String SERIALIZED_NAME_DATA = "data";
  @SerializedName(SERIALIZED_NAME_DATA)
  private String data;

  public static final String SERIALIZED_NAME_FILE_ID = "fileId";
  @SerializedName(SERIALIZED_NAME_FILE_ID)
  private String fileId;

  public static final String SERIALIZED_NAME_CRITICAL = "critical";
  @SerializedName(SERIALIZED_NAME_CRITICAL)
  private Boolean critical;

  public NewAppletVersionRequest() { 
  }

  public NewAppletVersionRequest name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * One of the (WALLET_OS)
   * @return name
  **/

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public NewAppletVersionRequest version(String version) {
    
    this.version = version;
    return this;
  }

   /**
   * New applet version name (format xx.xx.xx)
   * @return version
  **/

  public String getVersion() {
    return version;
  }


  public void setVersion(String version) {
    this.version = version;
  }


  public NewAppletVersionRequest fileName(String fileName) {
    
    this.fileName = fileName;
    return this;
  }

   /**
   * Original CAP-file name
   * @return fileName
  **/

  public String getFileName() {
    return fileName;
  }


  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  public NewAppletVersionRequest data(String data) {
    
    this.data = data;
    return this;
  }

   /**
   * Base64 of CAP-file content
   * @return data
  **/

  public String getData() {
    return data;
  }


  public void setData(String data) {
    this.data = data;
  }


  public NewAppletVersionRequest fileId(String fileId) {
    
    this.fileId = fileId;
    return this;
  }

   /**
   * Cap file identifier
   * @return fileId
  **/

  public String getFileId() {
    return fileId;
  }


  public void setFileId(String fileId) {
    this.fileId = fileId;
  }


  public NewAppletVersionRequest critical(Boolean critical) {
    
    this.critical = critical;
    return this;
  }

   /**
   * Flag for critical characteristic
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
    NewAppletVersionRequest newAppletVersionRequest = (NewAppletVersionRequest) o;
    return Objects.equals(this.name, newAppletVersionRequest.name) &&
        Objects.equals(this.version, newAppletVersionRequest.version) &&
        Objects.equals(this.fileName, newAppletVersionRequest.fileName) &&
        Objects.equals(this.data, newAppletVersionRequest.data) &&
        Objects.equals(this.fileId, newAppletVersionRequest.fileId) &&
        Objects.equals(this.critical, newAppletVersionRequest.critical);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version, fileName, data, fileId, critical);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewAppletVersionRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    fileName: ").append(toIndentedString(fileName)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
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

