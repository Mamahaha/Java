package org.led.tools.BmcDbOperator.common;

public class BmcRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -202960490515213039L;
    private BmcExceptionType type;

    public BmcRuntimeException() {
        super(BmcExceptionType.UNSPECIFIED.getDescription());
        this.type = BmcExceptionType.UNSPECIFIED;
    }

    public BmcRuntimeException(BmcExceptionType type) {
        super(type.getDescription());
        this.type = type;
    }

    public BmcRuntimeException(BmcExceptionType type, Throwable cause) {
        super(type.getDescription(), cause);
        this.type = type;
    }

    public BmcExceptionType getType() {
        return type;
    }

    /*
     * shall not use this constructor
     */
    @Deprecated
    public BmcRuntimeException(String message) {
        super(message);
        throw new UnsupportedOperationException();
    }

    /*
     * shall not use this constructor
     */
    @Deprecated
    public BmcRuntimeException(Throwable cause) {
        super(cause);
        throw new UnsupportedOperationException();
    }

    /*
     * shall not use this constructor
     */
    @Deprecated
    public BmcRuntimeException(String message, Throwable cause) {
        super(message, cause);
        throw new UnsupportedOperationException();
    }
}
