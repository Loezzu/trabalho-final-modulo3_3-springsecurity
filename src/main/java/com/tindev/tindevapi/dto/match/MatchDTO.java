package com.tindev.tindevapi.dto.match;

import lombok.Data;

@Data
public class MatchDTO{

    private Integer matchId;
    private Integer matchedUserFirst;
//    private String matchedUserNameFirst;
//    private String machedUserEmailFirst;
    private Integer matchedUserSecond;
//    private String matchedUserNameSecond;
//    private String matchedUserEmailSecond;
}
