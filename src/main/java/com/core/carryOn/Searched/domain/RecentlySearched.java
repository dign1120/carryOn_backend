package com.core.carryOn.Searched.domain;

import com.core.carryOn.member.domain.Member;
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
@Document(collection = "recently_searched")
public class RecentlySearched {
    @Id
    @Field(name = "_id")
    private String id;

    @Field(name = "latitude")
    private Double latitude;

    @Field(name = "longitude")
    private Double longitude;

    @Field(name = "address")
    private String address;

    @Field(name = "searched_text")
    private String searchedText;

    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
