package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Proxy;
import com.adrian.mobsters.domain.ProxyContainer;
import com.adrian.mobsters.repository.ProxyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static com.adrian.mobsters.util.TestUtils.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProxyControllerTest {
    private static final String BASE_API = "/api/v1/proxy";
    @Mock
    private ProxyRepository proxyRepository;
    @InjectMocks
    private ProxyController proxyController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(proxyController).build();
    }

    @Test
    public void addProxy() throws Exception {
        Proxy proxy = new Proxy("localhost", 533);
        given(proxyRepository.save(proxy)).willReturn(proxy);
        mockMvc.perform(post(BASE_API + "/add", proxy)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(proxy)))
                .andExpect(status().isCreated());
    }

    @Test
    public void addProxyList() throws Exception {
        Proxy proxy = new Proxy("localhost", 533);
        List<Proxy> proxyList = Collections.singletonList(proxy);
        ProxyContainer proxyContainer = new ProxyContainer();
        proxyContainer.setProxies(proxyList);
        given(proxyRepository.saveAll(proxyList)).willReturn(proxyList);

        mockMvc.perform(post(BASE_API + "/add-list", proxyContainer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(proxyContainer)))
                .andExpect(status().isCreated());
    }
}