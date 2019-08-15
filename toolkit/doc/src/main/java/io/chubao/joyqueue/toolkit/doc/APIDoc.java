package io.chubao.joyqueue.toolkit.doc;


import java.util.List;

/**
 * api doc description
 **/
public class APIDoc {
    private String id;
    private String packageName;
    private String service;
    private String method;
    private String path;
    private String desc;
    private String httpMethod;
    private List<Param> params;
    private String iDemo;
    private String oDemo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public String getiDemo() {
        return iDemo;
    }

    public void setiDemo(String iDemo) {
        this.iDemo = iDemo;
    }

    public String getoDemo() {
        return oDemo;
    }

    public void setoDemo(String oDemo) {
        this.oDemo = oDemo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
