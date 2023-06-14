package org.study.commons.validators;

/**
 * 휴대전화 번호 검증
 *
 */
public interface CellPhoneValidator {
    default boolean checkCellPhoneNumber(String cellPhone) {
        cellPhone = cellPhone.replaceAll("\\D", "");
        String pattern = "^01[016]\\d{3,4}\\d{4}$";
        return cellPhone.matches(pattern);
    }
}
