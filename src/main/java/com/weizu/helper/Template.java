package com.weizu.helper;


import java.util.List;

public class Template {

    /** 接收者（用户）的 openid */
    private String touser;
    /** 所需下发的订阅模板id */
    private String template_id;
    /** 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。 */
    private String page;
    /** 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版 */
    private String programState;
    /** 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } } */
    private List<TemplateParam> templateParamList;


    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getTemplate_id() {
        return template_id;
    }
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public String getProgramState() {
        return programState;
    }
    public void setProgramState(String programState) {
        this.programState = programState;
    }



    public String toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.touser)).append(",");
        buffer.append(String.format("\"template_id\":\"%s\"", this.template_id)).append(",");
        buffer.append(String.format("\"page\":\"%s\"", this.page)).append(",");
        buffer.append(String.format("\"miniprogram_state\":\"%s\"", this.programState)).append(",");
        buffer.append("\"data\":{");
        TemplateParam param = null;
        for (int i = 0; i < this.templateParamList.size(); i++) {
            param = templateParamList.get(i);
            // 判断是否追加逗号
            if (i < this.templateParamList.size() - 1){
                buffer.append(String.format("\"%s\": {\"value\":\"%s\"},", param.getKey(), param.getValue()));
            }else{
                buffer.append(String.format("\"%s\": {\"value\":\"%s\"}", param.getKey(), param.getValue()));
            }
        }
        buffer.append("}");
        buffer.append("}");
        return buffer.toString();
    }

    public List<TemplateParam> getTemplateParamList() {
        return templateParamList;
    }

    public void setTemplateParamList(List<TemplateParam> templateParamList) {
        this.templateParamList = templateParamList;
    }
}
