package com.groupc.weather.entity.resultSet;

public interface QnaBoardListResultSet {
    public Integer getBoardNumber();
    public String getBoardTitle();
    public String getBoardWriteDatetime();
    public int getBoardWriterNumber();
    public String getBoardWriterNickname();
    public String getBoardWriterProfileImageUrl();
    public int getReplyComplete();
}

