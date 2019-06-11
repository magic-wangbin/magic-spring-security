package com.magic.security.rbac.exception;

public class RbacNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 7207451175263593487L;

    public RbacNotFoundException(String message) {
        super(message);
    }

}
