package com.ecommerce.ecommerce_order_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SNSMessage {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("MessageId")
    private String messageId;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("TopicArn")
    private String topicArn;
    @JsonProperty("Timestamp")
    private String timestamp;
    @JsonProperty("UnsubscribeURL")
    private String unsubscribeUrl;
    @JsonProperty("SequenceNumber")
    private String sequenceNumber;

}
