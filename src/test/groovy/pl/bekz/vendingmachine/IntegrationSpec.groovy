package pl.bekz.vendingmachine

import groovy.transform.TypeChecked
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@TypeChecked
@SpringBootTest(classes =  [VendingMachineApplication])
@Transactional
@Rollback
abstract class IntegrationSpec extends Specification {
    @Autowired
    protected WebApplicationContext applicationContext

    MockMvc mockMvc

    @Before
    void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
        .build()
    }
}
