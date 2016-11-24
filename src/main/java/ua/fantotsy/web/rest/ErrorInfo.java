package ua.fantotsy.web.rest;

public class ErrorInfo {
    public final String url;
    public final String ex;
    public final String msg;

    public ErrorInfo(String url, Exception ex, String msg) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
        this.msg = msg;
    }
}