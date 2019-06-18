package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.domain.MobsterWrapper;
import com.adrian.mobsters.repository.MobsterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MobsterServiceImplTest {

    @Mock
    private MobsterRepository mobsterRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private MobsterServiceImpl mobsterServiceImpl;

    private String JOHN_SMITH = "John Smith";

    @Before
    public void setUp() throws Exception {
        given(userService.getUser()).willReturn("tracy");
    }

    @Test
    public void readUserPassword() {
        Mobster mobster = Mobster.builder().id("1").user("zombie").password("hax").user("tracy").build();
        given(mobsterRepository.findByUsername("zombie")).willReturn(Optional.of(mobster));

        assertThat(mobsterServiceImpl.retrieveMobsterPassword("zombie"), equalTo("hax"));
    }

    @Test
    public void createMobsters() {
        Mobster mobster = Mobster.builder().id("1").username(JOHN_SMITH).user("tracy").build();
        MobsterWrapper mobsterWrapper = new MobsterWrapper(Collections.singletonList(mobster));
        given(mobsterRepository.saveAll(anyCollection())).willReturn(mobsterWrapper.getMobsters());

        List<Mobster> createdMobster = mobsterServiceImpl.createMobsters(Collections.singletonList(mobster));

        assertThat(createdMobster, is(equalTo(mobsterWrapper.getMobsters())));
    }
}
