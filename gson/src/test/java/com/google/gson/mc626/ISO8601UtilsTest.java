package com.google.gson.mc626;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.internal.bind.util.ISO8601Utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import junit.framework.TestCase;

/** Test cases for ISO8601Utils, using cause-effect graph for test coverage **/
public class ISO8601UtilsTest extends TestCase {
	public void testLowerBoundPosition() {
        String strDate = "whatever";
		ParsePosition position = new ParsePosition(-1);
		assertThrows(ParseException.class, () -> ISO8601Utils.parse(strDate, position));
    }
	
	public void testUpperBoundPosition() {
        String strDate = "2014-02-12";
        ParsePosition position = new ParsePosition(42);
        assertThrows(ParseException.class, () -> ISO8601Utils.parse(strDate, position));
    }

    public void testEmptyDate() throws ParseException {
        String strDate = "";
        ParsePosition position = new ParsePosition(0);
        assertThrows(ParseException.class, () -> ISO8601Utils.parse(strDate, position));
    }
    
	public void testInvalidDate() {
        String strDate = "2014z02-12";
        ParsePosition position = new ParsePosition(0);
        assertThrows(ParseException.class, () -> ISO8601Utils.parse(strDate, position));
    }
	
	public void testInvalidDateWithTime() {
        String strDate = "201402-12T";
        ParsePosition position = new ParsePosition(0);
        assertThrows(ParseException.class, () -> ISO8601Utils.parse(strDate, position));
    }
	
	public void testInvalidDateWithoutTimezone() {
        String strDate = "201402-12T01:01:59";
	    ParsePosition position = new ParsePosition(0);
        assertThrows(ParseException.class, () -> ISO8601Utils.parse(strDate, position));
	}

	public void testValidDate() throws ParseException {
        String strDate = "201402-12";
        ParsePosition position = new ParsePosition(0);
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2014-02-12");
        
        assertEquals(date, ISO8601Utils.parse(strDate, position));
    }
	
	public void testValidDateWithTime() throws ParseException {
        String strDate = "2014-02-12T01:01:59Z";
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        assertEquals(sdf.parse("2014-02-12 01:01:59"), ISO8601Utils.parse(strDate, position));
    }
	
    public void testValidDateWithTimeAndTimezone() throws ParseException {
        String strDate = "2014-02-12T01:01:59+0300";
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        assertEquals(sdf.parse("2014-02-11 20:01:59"), ISO8601Utils.parse(strDate, position));
    }
}
