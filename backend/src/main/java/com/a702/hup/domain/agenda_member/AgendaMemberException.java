package com.a702.hup.domain.agenda_member;

import com.a702.hup.global.error.ErrorCode;
import com.a702.hup.global.error.exception.BusinessException;

public class AgendaMemberException extends BusinessException {
    public AgendaMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
