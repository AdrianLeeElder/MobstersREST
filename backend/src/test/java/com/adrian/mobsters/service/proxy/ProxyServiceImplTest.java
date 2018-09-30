package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.exception.ProxyNotAvailableException;
import com.adrian.mobsters.repository.ProxyReactiveRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProxyServiceImplTest {

    @Mock
    private ProxyReactiveRepository proxyReactiveRepository;
    @InjectMocks
    private ProxyServiceImpl proxyServiceImpl;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Proxy proxy1;
    private Proxy proxy2;
    private Proxy proxy3;

    @Before
    public void setUp() {
        proxy1 = new Proxy();
        proxy1.setSinceLastUpdate(1);

        proxy2 = new Proxy();
        proxy2.setSinceLastUpdate(2);

        proxy3 = new Proxy();
        proxy3.setSinceLastUpdate(3);
    }

    @Test
    public void sortedProxyList() {
        List<Proxy> unsortedProxyList = getUnsortedProxyList();
        List<Proxy> sortedProxyList = getSortedProxyList();
        Flux<Proxy> sortedProxies = proxyServiceImpl.getSortedProxyList(Flux.fromIterable(unsortedProxyList));

        assertEquals(sortedProxyList, sortedProxies.collectList().block());
    }

    private List<Proxy> getSortedProxyList() {
        return getProxyList(proxy1, proxy2, proxy3);
    }

    private List<Proxy> getUnsortedProxyList() {
        return getProxyList(proxy3, proxy2, proxy1);
    }

    private List<Proxy> getProxyList(Proxy... proxies) {
        return Arrays.asList(proxies);
    }

    @Test
    public void firstNotInUseProxy() throws ProxyNotAvailableException {
        proxy1.setInUse(true);
        proxy2.setInUse(true);

        given(proxyReactiveRepository.findAll()).willReturn(Flux.fromIterable(getProxyList(proxy1, proxy2, proxy3)));
        Proxy proxy = proxyServiceImpl.getAvailableProxy();

        assertEquals(proxy3, proxy);
    }

    @Test
    public void noProxyAvailable() throws ProxyNotAvailableException {
        expectedException.expect(ProxyNotAvailableException.class);
        proxy1.setInUse(true);
        proxy2.setInUse(true);
        proxy3.setInUse(true);

        given(proxyReactiveRepository.findAll()).willReturn(Flux.fromIterable(getProxyList(proxy1, proxy2, proxy3)));

        proxyServiceImpl.getAvailableProxy();
    }

    @Test
    public void handleProxyFailure() {
        given(proxyReactiveRepository.delete(proxy1)).willReturn(Mono.empty());

        proxy1.setAttempts(12);
        proxy1.setFailures(9);
        proxy1.setSuccesses(3);
        proxyServiceImpl.handleProxyFailure(proxy1);

        assertEquals(proxy1.getFailures(), 10);
        verify(proxyReactiveRepository).delete(proxy1);
    }

    @Test
    public void proxyShouldNotBeDeleted() {
        proxy1.setAttempts(12);
        proxy1.setFailures(2);
        proxy1.setSuccesses(10);
        proxyServiceImpl.handleProxyFailure(proxy1);

        assertEquals(proxy1.getFailures(), 3);
        verify(proxyReactiveRepository, times(0)).delete(proxy1);
    }
}