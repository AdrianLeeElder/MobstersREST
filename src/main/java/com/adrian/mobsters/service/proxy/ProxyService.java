package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.exception.ProxyNotAvailableException;

public interface ProxyService {

    Proxy getAvailableProxy() throws ProxyNotAvailableException;

    void handleProxyFailure(Proxy proxy);
}
