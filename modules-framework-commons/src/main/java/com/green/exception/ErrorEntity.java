package com.green.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 错误实体
 * 
 * yuanhualiang
 */
public abstract class ErrorEntity implements Cloneable {

	private String errorCode;
	private String errorMessage;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ErrorEntity.class);

	protected abstract String getHeadCode();

	public ErrorEntity(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getUniqErrorCode() {
		return (new StringBuilder()).append(getHeadCode()).append(errorCode)
				.toString();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public ErrorEntity newMessage(String errorMessage) {
		try {
			ErrorEntity errorEntity = (ErrorEntity) super.clone();
			errorEntity.errorMessage = errorMessage;
			return errorEntity;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return this;
	}

}