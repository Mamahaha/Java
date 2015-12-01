
package org.led.tools.BmcDbOperator.common;

public class ConnectionResponse {

    private Integer statusCode = null;
    private String content = null;
    private String contentType = null;

    public ConnectionResponse(Integer statusCode, String content, String contentType) {
        this.statusCode = statusCode;
        this.content = content;
        this.contentType = contentType;
    }

    public ConnectionResponse() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }
}
