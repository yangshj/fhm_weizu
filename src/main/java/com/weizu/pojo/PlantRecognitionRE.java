package com.weizu.pojo;

/**
 * 植物识别
 */
public class PlantRecognitionRE {

    /** success/fail */
    private String result;
    /** 名称 */
    private String name;
    /** 置信度 */
    private String score;
    /** 描述 */
    private String description;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
}
