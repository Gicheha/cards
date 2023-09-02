package com.restful.service.cardsservice.model;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,insertable = false)
    @ApiModelProperty(position = 1)
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty(position = 2)
    private String name;

    @Getter
    @Setter
    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private String colour;

    @ApiModelProperty(position = 5)
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ApiModelProperty(position = 6)
    private Users user;

    @CreatedDate
    @Column(insertable = false)
    @ApiModelProperty(position = 7)
    private Timestamp created;

}
