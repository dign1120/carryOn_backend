package com.core.carryOn.Coords.domain;

import com.core.carryOn.Location.domain.Location;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.bson.types.ObjectId;
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
    private ObjectId id;

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

    @Field(name = "location_id")
    private String locationId;
}
