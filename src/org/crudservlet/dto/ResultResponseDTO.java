package org.crudservlet.dto;

public class ResultResponseDTO {
    ResultDTO Result;

    public ResultResponseDTO() {
    }

    public ResultResponseDTO(ResultDTO result) {
        Result = result;
    }

    public ResultDTO getResult() {
        return Result;
    }

    public void setResult(ResultDTO result) {
        Result = result;
    }
}
