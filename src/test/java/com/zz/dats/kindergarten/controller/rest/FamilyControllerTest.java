package com.zz.dats.kindergarten.controller.rest;

import com.zz.dats.kindergarten.controller.rest.rest.FamilyController;
import com.zz.dats.kindergarten.db.entity.FamilyKidsEntity;
import com.zz.dats.kindergarten.db.entity.FamilyNameEntity;
import com.zz.dats.kindergarten.db.entity.KidEntity;
import com.zz.dats.kindergarten.db.repository.FamilyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FamilyController.class)
public class FamilyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FamilyRepository familyRepository;

    @Test
    public void familyListTest() throws Exception {
        TestData testData = new TestData().invoke();

        when(familyRepository.findAll()).thenReturn(testData.getFamilyNameEntityList());
        this.mockMvc.perform(get("/family")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(testData.getKidEntity().toString())))
            .andExpect(content().string(containsString(testData.getFamilyNameEntity().getName())))
        ;
    }

    @Test
    public void familyAddSuccessTest() throws Exception {
        TestData testData = new TestData().invoke();

        when(familyRepository.findAll()).thenReturn(testData.getFamilyNameEntityList());
        String newFamilyValueMock = "testMock";
        this.mockMvc.perform(post("/family").param("newFamily", newFamilyValueMock)).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(testData.getKidEntity().toString())))
            .andExpect(content().string(containsString(testData.getFamilyNameEntity().getName())))
            .andExpect(content().string(not(containsString(String.format("ģimene ar vārdu %s jau eksistē", newFamilyValueMock)))))
        ;

        verify(familyRepository, times(1)).save(argThat(familyNameEntity -> familyNameEntity.getName().equals(newFamilyValueMock)));
    }

    @Test
    public void familyAddDuplicateTest() throws Exception {
        TestData testData = new TestData().invoke();
        String newFamilyValueMock = "testMock";

        when(
            familyRepository.save(
                argThat(familyNameEntity -> familyNameEntity.getName().equals(newFamilyValueMock))
            )
        ).thenThrow(new DataIntegrityViolationException("unit test"));
        when(familyRepository.findAll()).thenReturn(testData.getFamilyNameEntityList());
        this.mockMvc.perform(post("/family").param("newFamily", newFamilyValueMock)).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(testData.getKidEntity().toString())))
            .andExpect(content().string(containsString(testData.getFamilyNameEntity().getName())))
            .andExpect(content().string(containsString(String.format("ģimene ar vārdu %s jau eksistē", newFamilyValueMock))))
        ;

    }

    private static class TestData {
        private List<FamilyNameEntity> familyNameEntityList;
        private FamilyNameEntity familyNameEntity;
        private KidEntity kidEntity;

        public List<FamilyNameEntity> getFamilyNameEntityList() {
            return familyNameEntityList;
        }

        public FamilyNameEntity getFamilyNameEntity() {
            return familyNameEntity;
        }

        public KidEntity getKidEntity() {
            return kidEntity;
        }

        public TestData invoke() {
            familyNameEntityList = new ArrayList<>();
            familyNameEntity = new FamilyNameEntity();

            familyNameEntity.setName("gg");
            familyNameEntity.setId(666);

            List<FamilyKidsEntity> familyKidsEntityList = new ArrayList<>();

            FamilyKidsEntity familyKidsEntity = new FamilyKidsEntity();
            kidEntity = new KidEntity();
            kidEntity.setFamilyEntity(familyKidsEntity);
            kidEntity.setPersonalCode("000000-00000");
            kidEntity.setLastName("lastNameMock");
            kidEntity.setName("nameMock");
            kidEntity.setGender("m");

            familyKidsEntity.setFamilyNamesByFamilyId(familyNameEntity);
            familyKidsEntity.setKidByKidId(kidEntity);

            familyKidsEntityList.add(familyKidsEntity);
            familyNameEntity.setFamilyKidsById(familyKidsEntityList);
            familyNameEntityList.add(familyNameEntity);
            return this;
        }
    }
}
