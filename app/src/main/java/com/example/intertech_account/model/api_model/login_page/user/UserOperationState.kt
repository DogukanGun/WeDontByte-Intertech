package com.example.intertech_account.model.api_model.login_page.user

enum class UserOperationState {
    WRONG_PASSWORD,
    WRONG_CITIZENSHIP_ID,
    PASSWORD_DOES_NOT_MATCH,
    MISSING_NAME_OR_SURNAME,
    PASSWORD_LENGTH_MUST_BE_BIGGER_THAN_8_CHARACTERS,
    MISSING_OR_EMPTY_LABEL,
    NO_ERROR
}