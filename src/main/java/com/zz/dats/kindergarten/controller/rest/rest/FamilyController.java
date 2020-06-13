package com.zz.dats.kindergarten.controller.rest.rest;

import com.zz.dats.kindergarten.db.entity.FamilyNameEntity;
import com.zz.dats.kindergarten.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FamilyController {

    final FamilyRepository familyRepository;

    @Autowired
    public FamilyController(
        FamilyRepository familyRepository
    ) {
        this.familyRepository = familyRepository;
    }

    @GetMapping("/family")
    public ModelAndView family() {
        return getFamilyListModel();
    }

    @PostMapping("/family")
    public ModelAndView family(
        @RequestParam(name = "newFamily") String newFamily
    ) {
        FamilyNameEntity familyNameEntity = new FamilyNameEntity();
        familyNameEntity.setName(newFamily);

        try {
            familyRepository.save(familyNameEntity);
        } catch (DataIntegrityViolationException exception) {
            return getFamilyListModel().addObject("error", String.format("ģimene ar vārdu %s jau eksistē", newFamily));
        }

        return getFamilyListModel();
    }

    private ModelAndView getFamilyListModel() {
        return new ModelAndView("family")
            .addObject("families", familyRepository.findAll());
    }
}
