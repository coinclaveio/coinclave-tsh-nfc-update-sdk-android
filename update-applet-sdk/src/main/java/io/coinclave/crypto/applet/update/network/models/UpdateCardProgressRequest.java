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
 * UpdateCardProgressRequest
 */
public class UpdateCardProgressRequest {
  public static final String SERIALIZED_NAME_CARD_ID = "cardId";
  @SerializedName(SERIALIZED_NAME_CARD_ID)
  private String cardId;

  public static final String SERIALIZED_NAME_COMMAND_RESPONSES = "commandResponses";
  @SerializedName(SERIALIZED_NAME_COMMAND_RESPONSES)
  private List<APDUCommandResponse> commandResponses = null;

  public UpdateCardProgressRequest() { 
  }

  public UpdateCardProgressRequest cardId(String cardId) {
    
    this.cardId = cardId;
    return this;
  }

   /**
   * Identifier of card
   * @return cardId
  **/

  public String getCardId() {
    return cardId;
  }


  public void setCardId(String cardId) {
    this.cardId = cardId;
  }


  public UpdateCardProgressRequest commandResponses(List<APDUCommandResponse> commandResponses) {
    
    this.commandResponses = commandResponses;
    return this;
  }

  public UpdateCardProgressRequest addCommandResponsesItem(APDUCommandResponse commandResponsesItem) {
    if (this.commandResponses == null) {
      this.commandResponses = new ArrayList<APDUCommandResponse>();
    }
    this.commandResponses.add(commandResponsesItem);
    return this;
  }

   /**
   * Get commandResponses
   * @return commandResponses
  **/

  public List<APDUCommandResponse> getCommandResponses() {
    return commandResponses;
  }


  public void setCommandResponses(List<APDUCommandResponse> commandResponses) {
    this.commandResponses = commandResponses;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateCardProgressRequest updateCardProgressRequest = (UpdateCardProgressRequest) o;
    return Objects.equals(this.cardId, updateCardProgressRequest.cardId) &&
        Objects.equals(this.commandResponses, updateCardProgressRequest.commandResponses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardId, commandResponses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateCardProgressRequest {\n");
    sb.append("    cardId: ").append(toIndentedString(cardId)).append("\n");
    sb.append("    commandResponses: ").append(toIndentedString(commandResponses)).append("\n");
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

