package com.zz.dats.kindergarten.db.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(FamilyNameEntity.class)
public class FamilyNameEntityTest {

    @Test
    public void toStringTest()
    {
        FamilyNameEntity familyNameEntity = new FamilyNameEntity();

        familyNameEntity.setName("nameMock");
        familyNameEntity.setId(666);

        assertEquals(String.format("%s (%d)", "nameMock", 666), familyNameEntity.toString());
    }
}
