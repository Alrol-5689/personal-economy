package com.economy.persistence.converter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Persists {@link YearMonth} as the first day of the month.
 */
@Converter(autoApply = false)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, Date> {

    @Override
    public Date convertToDatabaseColumn(YearMonth attribute) {
        if (attribute == null) return null;
        LocalDate firstDay = attribute.atDay(1);
        return Date.valueOf(firstDay);
    }

    @Override
    public YearMonth convertToEntityAttribute(Date dbData) {
        if (dbData == null) return null;
        LocalDate localDate = dbData.toLocalDate();
        return YearMonth.from(localDate);
    }
}
