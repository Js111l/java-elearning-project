package project.model;

public class ErrorModel {
    private final int statusCode;
    private final String shortDesc;
    private final String shortMsg;

    public ErrorModel(int statusCode, String shortDesc, String shortMsg) {
        this.statusCode = statusCode;
        this.shortDesc = shortDesc;
        this.shortMsg = shortMsg;
    }
}
