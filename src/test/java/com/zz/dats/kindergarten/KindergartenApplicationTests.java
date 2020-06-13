package com.zz.dats.kindergarten;

import com.zz.dats.kindergarten.controller.rest.rest.FamilyController;
import com.zz.dats.kindergarten.controller.rest.rest.KidController;
import com.zz.dats.kindergarten.controller.rest.rest.KindergartenController;
import com.zz.dats.kindergarten.controller.rest.rest.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KindergartenApplicationTests {
    @Autowired
    private FamilyController familyController;
    @Autowired
    private KidController kidController;
    @Autowired
    private KindergartenController kindergartenController;
    @Autowired
    private MainController mainController;

    @Test
    void contextLoads() {
        assertThat(mainController).isNotNull();
        assertThat(kindergartenController).isNotNull();
        assertThat(kidController).isNotNull();
        assertThat(familyController).isNotNull();
    }

}
