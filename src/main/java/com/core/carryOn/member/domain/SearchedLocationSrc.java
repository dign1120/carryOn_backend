package com.core.carryOn.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "searched_location_src")
public class SearchedLocationSrc {
    @Id
    @Field(name = "_id")
    private String id;

    @Field(name = "source_latitude")
    private Double sourceLatitude;

    @Field(name = "source_longitude")
    private Double sourceLongitude;

    @Field(name = "source_address")
    private String sourceAddress;

    @Field(name = "source_searched")
    private String sourceSearched;

    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;
}
