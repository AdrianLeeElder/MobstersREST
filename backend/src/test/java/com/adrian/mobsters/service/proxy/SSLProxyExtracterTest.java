package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SSLProxyExtracterTest {

    @InjectMocks
    private SSLProxyExtracter sslProxyExtracter;

    @Test
    public void getProxiesFromPageString() {
        List<Proxy> actual = Collections.singletonList(new Proxy());

        String s = "<html />";

        assertEquals(sslProxyExtracter.getProxiesFromPageString(s), actual);
    }
}