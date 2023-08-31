package com.restful.service.cardsservice.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "description",
        "colour",
        "state"
})
public class CardsDto implements Serializable {

    @Getter
    @Setter
    @NotBlank
    @NotNull
    @JsonProperty("name")
    private String name;

    @Getter
    @Setter
    @JsonProperty("description")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String description;

    @Getter
    @Setter
    @JsonProperty("colour")
    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3,4}){1,2}$",message = "Invalid colour code")
    private String colour;

    @Getter
    @Setter
    @JsonProperty("state")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String state;

}
