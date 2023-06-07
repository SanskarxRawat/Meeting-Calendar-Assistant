package com.meeting.calendar.converter;

import com.meeting.calendar.constants.CalendarAssistantConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {


    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (Objects.isNull(attribute) || attribute.isEmpty()) {
            return null;
        }
        return String.join(CalendarAssistantConstants.DELIMITER, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData) || dbData.isEmpty()) {
            return null;
        }
        return Arrays.asList(dbData.split(CalendarAssistantConstants.DELIMITER));
    }
}
