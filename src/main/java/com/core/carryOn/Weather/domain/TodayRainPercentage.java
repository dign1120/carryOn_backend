package com.core.carryOn.Weather.domain;

import com.core.carryOn.member.domain.Member;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "today_rain_percentage")
public class TodayRainPercentage {
    @Id
    private ObjectId id;

    @Field("percentage")
    private Double percentage;

    @Field("member_id")
    private String memberId;
}
