package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SSLProxyExtracterTest {

    @InjectMocks
    private SSLProxyExtracter sslProxyExtracter;

    @Test
    public void getProxiesFromPageString() {
        List<Proxy> expected = Collections.emptyList();

        String s = "<html />";

        assertEquals(expected, sslProxyExtracter.getProxiesFromPageString(s));
    }
}