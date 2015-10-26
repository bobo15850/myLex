package _01_REs_hander.test;

import static org.junit.Assert.*;

import org.junit.Test;

import _01_REs_hander.REsHandler;

public class REsHandler_test {
	@Test
	public void testGetTargetRE() {
		String path = "D:/study/javaÎÄ¼þ/myLex/files/REs.txt";
		REsHandler handler = new REsHandler(path);
		String targetRE = handler.getTargetRE();
		System.out.println(targetRE);
		assertEquals(handler.getTargetRE(), "(((a|b)|¦Å).(a.a*))|((a.a*).(b|¦Å))");
	}
}
