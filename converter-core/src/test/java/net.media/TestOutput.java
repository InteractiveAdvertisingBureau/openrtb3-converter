package net.media;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sourav.p on .
 */
public class TestOutput {
    private Long totalTestCases;
    private Integer failedTestCases;
    private List<OutputTestPojo> failedTestList = new ArrayList<>();

    public List<OutputTestPojo> getFailedTestList() {
        return failedTestList;
    }

    public void setTotalTestCases(Long totalTestCases) {
        this.totalTestCases = totalTestCases;
    }

    public void setFailedTestCases(Integer failedTestCases) {
        this.failedTestCases = failedTestCases;
    }
}
