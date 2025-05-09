package com.core.carryOn.Cctv.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CctvDto {

    @JsonProperty("CCTVID")
    private String cctvId;

    @JsonProperty("XCOORD")
    private double xCoord;

    @JsonProperty("MOVIE")
    private String movie;

    @JsonProperty("KIND")
    private String kind;

    @JsonProperty("CENTERNAME")
    private String centerName;

    @JsonProperty("YCOORD")
    private double yCoord;

    @JsonProperty("CCTVNAME")
    private String cctvName;

    @JsonProperty("ID")
    private String id;

    @JsonProperty("STRMID")
    private String strmId;

    @JsonProperty("CCTVIP")
    private String cctvIp;

    @JsonProperty("CH")
    private String ch;
}
