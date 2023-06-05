package com.groupc.weather.entity.resultSet;

public interface ChattingMessageListResultSet {
    public String getRoomId();
    public Integer getUserNumber();
    public String getUserNickname();
    public String getUserProfileImageUrl();
    public String getDate();
    public String getMessage();
    public int getView(); // boolean으로 하니까 계속 오류나길래 걍 int로 바꿈..
}
