package com.bloodredsun.web.spider;

import org.junit.Test;

public class SimpleSiteSpiderTest {


    String responseWithNoATags = "<html><body></body></html>";
    String responseWithOneATagNoHref = "<html><body><a>This</that></body></html>";
    String responseWithOneATagWithHref = "<html><body><a href=\"\"</body></html>";

    @Test
    public void testProcess() throws Exception {

    }
}
