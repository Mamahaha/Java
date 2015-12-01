package org.led.tools.BmcDbOperator.common;

@SuppressWarnings("serial")
public class BmcValidationRunTimeException extends RuntimeException {

    private BmcErrorCode errorcode;

    private String errorMessage;

    /**
     * 
     * The internalError is a deprecated field replaced by the responseErrorStatusCode because the internalError is only to determine response error code(500 or
     * 400). Actually, the response error code will be 400, 500 and 404, and so on.
     */
    @Deprecated
    private boolean internalError;

    private int responseErrorStatusCode = HttpResponseStatusCode.SC_BAD_REQUEST;

    /**
     * 
     * @param errorcode
     * @param detailInfo
     * @param responseErrorStatusCode
     *            The responseErrorStatusCode value is from HttpResponseStatusCode class field. if 400, responseErrorStatusCode =
     *            HttpResponseStatusCode.SC_BAD_REQUEST if 500, responseErrorStatusCode = HttpResponseStatusCode.SC_INTERNAL_SERVER_ERROR if 404,
     *            responseErrorStatusCode = HttpResponseStatusCode.SC_NOT_FOUND.
     */
    public BmcValidationRunTimeException(BmcErrorCode errorcode, String detailInfo,
            int responseErrorStatusCode) {
        super(errorcode.getDescription(detailInfo));
        this.errorcode = errorcode;
        this.errorcode = errorcode;
        this.errorMessage = errorcode.getDescription(detailInfo);
        this.setResponseErrorStatusCode(responseErrorStatusCode);
    }

    /**
     * 
     * The BmcValidationRunTimeException(BmcErrorCode errorcode, String detailInfo, boolean internalError) is a deprecated method replaced by
     * BmcValidationRunTimeException(BmcErrorCode errorcode, String detailInfo, int responseErrorStatusCode) because the internalError field is deprecated.
     */
    @Deprecated
    public BmcValidationRunTimeException(BmcErrorCode errorcode, String detailInfo,
            boolean internalError) {
        super(errorcode.getDescription(detailInfo));
        this.errorcode = errorcode;
        this.errorMessage = errorcode.getDescription(detailInfo);
        this.internalError = internalError;
    }

    public BmcValidationRunTimeException(BmcErrorCode errorcode, String detailInfo) {
        super(errorcode.getDescription(detailInfo));
        this.errorcode = errorcode;
        this.errorMessage = errorcode.getDescription(detailInfo);
        this.internalError = false;
    }

    public BmcValidationRunTimeException(BmcErrorCode errorcode, String detailInfo, Throwable casue) {
        super(errorcode.getDescription(detailInfo), casue);
        this.errorcode = errorcode;
        this.errorMessage = errorcode.getDescription(detailInfo);
        this.internalError = false;

    }

    public BmcErrorCode getErrorcode() {
        return errorcode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * 
     * The isInternalError is a deprecated method replaced by the getResponseErrorStatusCode because the internalError is only to determine response error
     * code(500 or 400). Actually, the response error code will be 400, 500 and 404, and so on.
     */
    @Deprecated
    public boolean isInternalError() {
        return internalError;
    }

    /**
     * 
     * The setInternalError is a deprecated method replaced by the setResponseErrorStatusCode because the internalError is only to determine response error
     * code(500 or 400). Actually, the response error code will be 400, 500 and 404, and so on.
     */
    @Deprecated
    public void setInternalError(boolean internalError) {
        this.internalError = internalError;
    }

    public int getResponseErrorStatusCode() {
        if (isInternalError()) {
            this.responseErrorStatusCode = HttpResponseStatusCode.SC_INTERNAL_SERVER_ERROR;
        }
        return responseErrorStatusCode;
    }

    /**
     * 
     * @param responseErrorStatusCode
     *            The responseErrorStatusCode value is from HttpResponseStatusCode class field. if 400,
     *            setResponseErrorStatusCode(HttpResponseStatusCode.SC_BAD_REQUEST) if 500,
     *            setResponseErrorStatusCode(HttpResponseStatusCode.SC_INTERNAL_SERVER_ERROR) if 404,
     *            setResponseErrorStatusCode(HttpResponseStatusCode.SC_NOT_FOUND).
     */
    public void setResponseErrorStatusCode(int responseErrorStatusCode) {
        this.responseErrorStatusCode = responseErrorStatusCode;
    }
}
