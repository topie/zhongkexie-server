package com.topie.zhongkexie.core.exception;

import com.topie.zhongkexie.common.exception.BusinessException;

public class RuntimeBusinessException extends BusinessException {
    @Override
    protected String getPropertiesPath() {
        return "/config/properties/business_code.properties";
    }

    public RuntimeBusinessException(int errCode) {
        super(errCode);
    }

    public RuntimeBusinessException(String message) {
        super(message);
    }
}
