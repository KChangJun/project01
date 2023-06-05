package com.groupc.weather.entity.primaryKey;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChattingRoomPk implements Serializable {

    @Column(name = "roomId")
    private String roomId;

    @Column(name = "userNumber")
    private Integer userNumber;

}
