package com.core.carryOn.member.domain;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "work_start")
public class TodayRainPercentage {
    @Id
    @Field("_id")
    private String id;

    @Field("percentage")
    private Double percentage;

    @OneToOne
    @JoinColumn(name = "member")
    private Member member;
}
