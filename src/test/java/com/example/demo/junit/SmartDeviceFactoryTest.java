package com.example.demo.junit;

import com.example.demo.service.SmartDeviceFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartDeviceFactoryTest {

    @Test
    void createByType() {
        // SmartDeviceFactory.createByType("sfsrgddsgds");

        assertThrows(
                IllegalArgumentException.class,
                () -> SmartDeviceFactory.createByType("sfsrgddsgds")
        );

    }
}