package net.media;

/**
 * Created by sourav.p on .
 */
public class OutputTestPojo {
    private String inputFile;

    private String inputType;

    private String outputType;

    private String status;

    private String exception;

    public void setInputFile(String file) {
        this.inputFile = file;
    }
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
