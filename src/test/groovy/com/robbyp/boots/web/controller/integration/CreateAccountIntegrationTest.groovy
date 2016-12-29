/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller.integration

import static com.robbyp.boots.web.controller.fixture.AccountDataFixture.standardAccountInfo
import static com.robbyp.boots.web.controller.fixture.AccountDataFixture.standardAccountInfoJSON
import static com.robbyp.boots.web.controller.fixture.AccountDataFixture.standardAccountInfoResource
import static org.mockito.Matchers.any
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc

import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.services.AccountService
import com.robbyp.boots.web.controller.AccountCommandsController
import com.robbyp.boots.web.domain.AccountInfoResourceAssembler


class CreateAccountIntegrationTest {

    private static final ACCOUNT_INFO = standardAccountInfo()

    private MockMvc mockMvc

    @InjectMocks
    private AccountCommandsController controller

    @Mock
    private AccountService accountService

    @Mock
    private AccountInfoResourceAssembler accountInfoResourceAssembler

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)

        this.mockMvc = standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter()).build()

        when(
            accountService.createNewAccount(any(AccountInfo))
        ).thenReturn(
            ACCOUNT_INFO
        )

        when(
            accountInfoResourceAssembler.instantiateResource(any(AccountInfo))
        ).thenReturn(
            standardAccountInfoResource(ACCOUNT_INFO)
        )
    }

    @Test
    void thatCreateAccountUsesHttpCreated() {
        this.mockMvc.perform(
            post('/api/accounts')
                .content(standardAccountInfoJSON(ACCOUNT_INFO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isCreated())
    }

    @Test
    void thatCreateAccountRendersAsJson() {
        this.mockMvc.perform(
            post('/api/accounts')
                .content(standardAccountInfoJSON(ACCOUNT_INFO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(jsonPath("\$.uniqueId").value(ACCOUNT_INFO.uniqueId.intValue()))
            .andExpect(jsonPath("\$.name").value(ACCOUNT_INFO.name))
            .andExpect(jsonPath("\$.number").value(ACCOUNT_INFO.number))
            .andExpect(jsonPath("\$.institution").value(ACCOUNT_INFO.institution))
            .andExpect(jsonPath("\$.currency").value(ACCOUNT_INFO.currency.code))
            .andExpect(jsonPath("\$.type").value(ACCOUNT_INFO.type.toString()))
            .andDo(print())
    }

    @Test
    void thatCreateAccountPassesLocationHeader() {
        this.mockMvc.perform(
            post('/api/accounts')
                .content(standardAccountInfoJSON(ACCOUNT_INFO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(header().string('Location',
                                    Matchers.endsWith
                                        ("/accounts/${ACCOUNT_INFO.uniqueId}"))
        ).andDo(print())
    }
}
