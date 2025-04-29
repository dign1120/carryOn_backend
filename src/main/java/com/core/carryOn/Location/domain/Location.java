package com.core.carryOn.Location.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "location")
public class Location {

    @Id
    private ObjectId id;

    @Field(name = "source_address")
    private String sourceAddress;

    @Field(name = "source_searched")
    private String sourceSearched;

    @Field(name = "dest_address")
    private String destAddress;

    @Field(name = "dest_searched")
    private String destSearched;

    @Field(name = "member_id")
    private String memberId;
}
