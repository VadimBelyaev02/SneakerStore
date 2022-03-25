package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceUnitTest {

    @Autowired
    private CustomerServiceImpl customerService;
}
