package com.core.carryOn.member.domain;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "searched_location_dest")
public class SearchedLocationDest {
    @Id
    @Field(name = "_id")
    private String id;

    @Field(name = "dest_latitude")
    private Double destLatitude;

    @Field(name = "dest_longitude")
    private Double destLongitude;

    @Field(name = "dest_address")
    private String destAddress;

    @Field(name = "dest_searched")
    private String destSearched;

    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;
}
