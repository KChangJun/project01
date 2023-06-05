package com.groupc.weather.entity.resultSet;

public interface BoardCommentResultSet {
    public int getCommentNumber();
    public int getBoardNumber();
    public int getCommentWriterNumber();
    public String getCommentContent();
    public String getCommentWriterNickname();
    public String getCommentWriterProfileImageUrl();
    public String getCommentWriteDatetime();
}
