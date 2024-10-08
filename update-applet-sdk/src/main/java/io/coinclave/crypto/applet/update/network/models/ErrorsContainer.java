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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ErrorsContainer
 */
public class ErrorsContainer {
  public static final String SERIALIZED_NAME_ERRORS = "errors";
  @SerializedName(SERIALIZED_NAME_ERRORS)
  private List<java.lang.Error> errors = null;

  public ErrorsContainer() { 
  }

  public ErrorsContainer errors(List<java.lang.Error> errors) {
    
    this.errors = errors;
    return this;
  }

  public ErrorsContainer addErrorsItem(java.lang.Error errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<java.lang.Error>();
    }
    this.errors.add(errorsItem);
    return this;
  }

   /**
   * Errors container
   * @return errors
  **/

  public List<java.lang.Error> getErrors() {
    return errors;
  }


  public void setErrors(List<java.lang.Error> errors) {
    this.errors = errors;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorsContainer errorsContainer = (ErrorsContainer) o;
    return Objects.equals(this.errors, errorsContainer.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorsContainer {\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
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

