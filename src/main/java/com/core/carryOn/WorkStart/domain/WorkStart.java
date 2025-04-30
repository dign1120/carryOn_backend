package com.core.carryOn.WorkStart.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "work_start")
public class WorkStart {
    @Id
    private ObjectId id;

    @Field("start_time")
    private LocalDateTime startTime;

    @Field("member_id")
    private String memberId;
}
