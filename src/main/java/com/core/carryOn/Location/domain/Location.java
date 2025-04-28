package com.core.carryOn.Location.domain;

import com.core.carryOn.member.domain.Member;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
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
    @Field("_id")
    private String id;

    @Field(name = "source_address")
    private String sourceAddress;

    @Field(name = "source_searched")
    private String sourceSearched;

    @Field(name = "dest_address")
    private String destAddress;

    @Field(name = "dest_searched")
    private String destSearched;

    @OneToOne
    @JoinColumn(name = "member")
    private Member member;
}
