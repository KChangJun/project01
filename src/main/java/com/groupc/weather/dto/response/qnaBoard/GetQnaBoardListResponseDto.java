package com.groupc.weather.dto.response.qnaBoard;

import java.util.ArrayList;
import java.util.List;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.entity.resultSet.QnaBoardListResultSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class GetQnaBoardListResponseDto extends ResponseDto {
    
    private List<QnaBoardSummary> qnaBoardList;

    public GetQnaBoardListResponseDto(List<QnaBoardListResultSet> resultSet) {
        super("SU", "Success");

        List<QnaBoardSummary> qnaBoardList = new ArrayList<>();

        for (QnaBoardListResultSet result: resultSet) {
            QnaBoardSummary qnaBoardSummary = new QnaBoardSummary(result);

            qnaBoardList.add(qnaBoardSummary);
        }

        this.qnaBoardList = qnaBoardList;
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class QnaBoardSummary {
    public Integer qnaBoardNumber;
    public String qnaBoardTitle;
    public String qnaBoardWriteDatetime;
    public int qnaBoardWriterNumber;
    public String qnaBoardWriterNickname;
    public String qnaBoardWriterProfileImageUrl;
    public boolean replyComplete;

    public QnaBoardSummary(QnaBoardListResultSet resultSet) {
        this.qnaBoardNumber = resultSet.getBoardNumber();
        this.qnaBoardTitle = resultSet.getBoardTitle();
        this.qnaBoardWriteDatetime = resultSet.getBoardWriteDatetime();
        this.qnaBoardWriterNumber = resultSet.getBoardWriterNumber();
        this.qnaBoardWriterNickname = resultSet.getBoardWriterNickname();
        this.qnaBoardWriterProfileImageUrl = resultSet.getBoardWriterProfileImageUrl();
        this.replyComplete = resultSet.getReplyComplete() == 1;
    }
}

