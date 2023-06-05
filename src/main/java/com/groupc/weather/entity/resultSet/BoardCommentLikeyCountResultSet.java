package com.groupc.weather.entity.resultSet;

public interface BoardCommentLikeyCountResultSet {
    public int getBoardNumber();
    public String getBoardTitle();
    public String getBoardContent();
    public String getBoardWriterDatetime();
    public String getWeatherDescription();
    public int getTemperature();
    public int getViewCount();
    public String getBoardWriterNickname();
    public String getBoardWiterProfileImagerUrl();
    public int getCommentCount();
    public int getLikeCount();
}