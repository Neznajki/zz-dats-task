package com.zz.dats.kindergarten.db.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(KidEntity.class)
public class KidEntityTest {

    @Test
    public void toStringTest()
    {
        KidEntity kidEntity = new KidEntity();

        kidEntity.setName("nameMock");
        kidEntity.setLastName("lastNameMock");
        kidEntity.setPersonalCode("000000-00000");
        kidEntity.setGender("m");

        assertEquals(String.format("%s %s (%s)| %s ", "nameMock", "lastNameMock", "000000-00000", "vīrietis"), kidEntity.toString());

        kidEntity.setGender("g");
        assertEquals(String.format("%s %s (%s)| %s ", "nameMock", "lastNameMock", "000000-00000", "sieviete"), kidEntity.toString());

        FamilyKidsEntity familyKidsEntity = new FamilyKidsEntity();
        FamilyNameEntity familyNameEntity = new FamilyNameEntity();

        familyKidsEntity.setKidByKidId(kidEntity);
        familyKidsEntity.setFamilyNamesByFamilyId(familyNameEntity);

        familyNameEntity.setName("familyMock");
        kidEntity.setFamilyEntity(familyKidsEntity);

        assertEquals(String.format("%s %s (%s)| %s @%s", "nameMock", "lastNameMock", "000000-00000", "sieviete", familyNameEntity.toString()), kidEntity.toString());
    }
}
