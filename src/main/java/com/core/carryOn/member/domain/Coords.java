package com.core.carryOn.member.domain;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "coords")
public class Coords {

    @Id
    @Field(name = "_id")
    private String id;

    @Field(name = "source_latitude")
    private Double sourceLatitude;

    @Field(name = "source_longitude")
    private Double sourceLongitude;

    @Field(name = "dest_latitude")
    private Double destLatitude;

    @Field(name = "dest_longitude")
    private Double destLongitude;

    @Field(name = "total_path_coords")
    private List<Coordinate> totalPathCoords;

    @OneToOne
    @JoinColumn(name = "location")
    private Location location;
}
