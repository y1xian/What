package com.yyxnb.module_login.bean;

import java.io.Serializable;

public class TestData implements Serializable {


    /**
     * code : 200
     * message : 成功!
     * result : [{"title":"李白","content":"风骨神仙籍里人，诗狂酒圣且平生。|开元一遇成何事，留得千秋万古名。","authors":"徐钧"}]
     */


    /**
     * title : 李白
     * content : 风骨神仙籍里人，诗狂酒圣且平生。|开元一遇成何事，留得千秋万古名。
     * authors : 徐钧
     */

    private String title;
    private String content;
    private String authors;
    private double testDouble;
    private double testDouble2;
    private double testDouble3;
    private int testInt;
    private int testInt2;
    private int testInt3;
    private String testString;
    private String testString2;
    private String testString3;

    public double getTestDouble2() {
        return testDouble2;
    }

    public void setTestDouble2(double testDouble2) {
        this.testDouble2 = testDouble2;
    }

    public double getTestDouble3() {
        return testDouble3;
    }

    public void setTestDouble3(double testDouble3) {
        this.testDouble3 = testDouble3;
    }

    public int getTestInt2() {
        return testInt2;
    }

    public void setTestInt2(int testInt2) {
        this.testInt2 = testInt2;
    }

    public int getTestInt3() {
        return testInt3;
    }

    public void setTestInt3(int testInt3) {
        this.testInt3 = testInt3;
    }

    public String getTestString2() {
        return testString2;
    }

    public void setTestString2(String testString2) {
        this.testString2 = testString2;
    }

    public String getTestString3() {
        return testString3;
    }

    public void setTestString3(String testString3) {
        this.testString3 = testString3;
    }

    public double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(double testDouble) {
        this.testDouble = testDouble;
    }

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authors='" + authors + '\'' +
                ", testDouble=" + testDouble +
                ", testDouble2=" + testDouble2 +
                ", testDouble3=" + testDouble3 +
                ", testInt=" + testInt +
                ", testInt2=" + testInt2 +
                ", testInt3=" + testInt3 +
                ", testString='" + testString + '\'' +
                ", testString2='" + testString2 + '\'' +
                ", testString3='" + testString3 + '\'' +
                '}';
    }
}
