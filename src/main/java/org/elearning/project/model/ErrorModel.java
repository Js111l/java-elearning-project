package org.elearning.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorModel {
    private int statusCode;
    private String shortDesc;
    private String shortMsg;

    public ErrorModel(int statusCode, String shortDesc, String shortMsg) {
        this.statusCode = statusCode;
        this.shortDesc = shortDesc;
        this.shortMsg = shortMsg;
    }
}
