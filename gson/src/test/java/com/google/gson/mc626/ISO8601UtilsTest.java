package com.google.gson.mc626;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gson.internal.bind.util.ISO8601Utils;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ISO8601UtilsTest extends TestCase{
	public void testFlow1() throws IOException {
		ISO8601Utils parser = new ISO8601Utils();
		Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        try {
			assertEquals(date, parser.parse("2014-02-11", new ParsePosition(0)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
	
	@SuppressWarnings("deprecation")
	public void testFlow2() throws IOException {
		ISO8601Utils parser = new ISO8601Utils();
		Date date = new Date(2014-1900, 1, 11, 01, 01, 59);
		// [yyyy-MM-dd|yyyyMMdd][T(hh:mm[:ss[.sss]]|hhmm[ss[.sss]])]?[Z|[+-]hh[:mm]]]
        try {
			assertEquals(date, parser.parse("2014-02-12T01:01:59+22:00", new ParsePosition(0)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
	
	@SuppressWarnings("deprecation")
	public void testFlow3() throws IOException {
		ISO8601Utils parser = new ISO8601Utils();
		Date date = new Date(2014-1900, 1, 11, 01, 01, 59);
		String d = "2014-02-12T01:01:59";
		String prefix = "Failed to parse date [\"" + d + "\"]: ";
        try {
			assertEquals(date, parser.parse(d, new ParsePosition(0)));
			Assert.fail("Should have thrown an exception.");
		} catch (ParseException e) {
			assertEquals(prefix + "No time zone indicator", e.getMessage());
		}
    }
	
	@SuppressWarnings("deprecation")
	public void testFlow4() throws IOException {
		ISO8601Utils parser = new ISO8601Utils();
		Date date = new Date(2014-1900, 1, 11, 01, 01, 59);
		String d = "2014-02-12T01:01:59?Z";
		String prefix = "Failed to parse date [\"" + d + "\"]: ";
        try {
			assertEquals(date, parser.parse(d, new ParsePosition(0)));
			Assert.fail("Should have thrown an exception.");
		} catch (ParseException e) {
			assertEquals(prefix + "Invalid time zone indicator '" + "?" +"'", e.getMessage());
		}
    }
	
	@SuppressWarnings("deprecation")
	public void testFlow5() throws IOException {
		ISO8601Utils parser = new ISO8601Utils();
		Date date = new Date(2014-1900, 1, 11, 01, 01, 59);
		String d = "2014-02-12T01:01:59+00000";
		String prefix = "Failed to parse date [\"" + d + "\"]: ";
        try {
			assertEquals(date, parser.parse(d, new ParsePosition(0)));
			Assert.fail("Should have thrown an exception.");
		} catch (ParseException e) {
			assertEquals(prefix + "Mismatching time zone indicator: GMT+00000 given, resolves to GMT+00:00", e.getMessage());
		}
    }
}
