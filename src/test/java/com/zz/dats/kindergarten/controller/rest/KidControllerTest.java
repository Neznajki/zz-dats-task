package com.zz.dats.kindergarten.controller.rest;

import com.zz.dats.kindergarten.controller.rest.rest.KidController;
import com.zz.dats.kindergarten.db.entity.FamilyKidsEntity;
import com.zz.dats.kindergarten.db.entity.FamilyNameEntity;
import com.zz.dats.kindergarten.db.entity.KidEntity;
import com.zz.dats.kindergarten.db.repository.FamilyRepository;
import com.zz.dats.kindergarten.db.repository.KidRepository;
import com.zz.dats.kindergarten.handler.KidHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KidController.class)
public class KidControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private KidHandler kidHandler;
    @MockBean
    private FamilyRepository familyRepository;
    @MockBean
    private KidRepository kidRepository;

    @Test
    public void kidDisplayTest() throws Exception {
        KidTestData kidTestData = new KidTestData().invoke();
        List<KidEntity> kidEntityList = kidTestData.getKidEntityList();
        KidEntity kid2 = kidTestData.getKid2();

        when(kidRepository.findAll()).thenReturn(kidEntityList);
        this.mockMvc.perform(get("/kid")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(kidTestData.getKid1().toString())))
            .andExpect(content().string(containsString(kid2.toString())))
            .andExpect(content().string(containsString(kidTestData.getKid3().toString())))
            .andExpect(content().string(containsString(String.format("data-family-id=\"%d\"", kid2.getFamilyEntity().getFamilyNamesByFamilyId().getId()))))
        ;
    }

    private class KidTestData {
        private List<KidEntity> kidEntityList;
        private KidEntity kid1;
        private KidEntity kid2;
        private KidEntity kid3;

        public List<KidEntity> getKidEntityList() {
            return kidEntityList;
        }

        public KidEntity getKid1() {
            return kid1;
        }

        public KidEntity getKid2() {
            return kid2;
        }

        public KidEntity getKid3() {
            return kid3;
        }

        public KidTestData invoke() {
            kidEntityList = new ArrayList<>();

            kid1 = new KidEntity();
            kid2 = new KidEntity();
            kid3 = new KidEntity();

            FamilyNameEntity familyNameEntity = new FamilyNameEntity();
            familyNameEntity.setId(666);

            kid1.setGender("m");
            kid2.setGender("m");
            kid3.setGender("f");

            FamilyKidsEntity familyKidsEntity = new FamilyKidsEntity();
            familyKidsEntity.setKidByKidId(kid2);
            familyKidsEntity.setFamilyNamesByFamilyId(familyNameEntity);
            kid2.setFamilyEntity(familyKidsEntity);

            kidEntityList.add(kid1);
            kidEntityList.add(kid2);
            kidEntityList.add(kid3);
            return this;
        }
    }
}
