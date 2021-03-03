package base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author lijun
 * @Date 2020年7月2日 17:05:01
 * @Description //TODO
 */

public class RealResponse {
    private InputStream inputStream;
    private InputStream errorStream;
    private int code;
    private long contentLength;
    private Exception exception;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getErrorStream() {
        return errorStream;
    }

    public void setErrorStream(InputStream errorStream) {
        this.errorStream = errorStream;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String is2String(InputStream inputStream) {
        if (inputStream != null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String sTempOneLine;
                while ((sTempOneLine = br.readLine()) != null) {
                    sb.append(sTempOneLine);
                }
                return sb.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
