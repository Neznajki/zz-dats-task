package com.zz.dats.kindergarten.controller.rest.rest;

import com.zz.dats.kindergarten.db.repository.*;
import com.zz.dats.kindergarten.handler.KidHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KidController {

    final KidHandler kidHandler;
    final FamilyRepository familyRepository;
    final KidRepository kidRepository;

    @Autowired
    public KidController(
        KidHandler kidHandler,
        FamilyRepository familyRepository,
        KidRepository kidRepository
    ) {
        this.kidHandler = kidHandler;
        this.familyRepository = familyRepository;
        this.kidRepository = kidRepository;
    }

    @GetMapping("/kid")
    public ModelAndView kid() {
        return getKidListModel();
    }

    @PostMapping("/kid")
    public ModelAndView kid(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "lastName") String lastName,
        @RequestParam(name = "personalCode") String personalCode
    ) {
        try {
            if (! kidHandler.isNameValid(name)) {
                return getKidListModel().addObject("error", KidHandler.kidNameErrorMessage);
            }
            if (! kidHandler.isLastNameValid(lastName)) {
                return getKidListModel().addObject("error", KidHandler.kidLastNameErrorMessage);
            }
            if (! kidHandler.isPersonalCodeValid(personalCode)) {
                return getKidListModel().addObject("error", KidHandler.kidPersonalCodeErrorMessage);
            }

            kidHandler.addKid(name, lastName, personalCode);
        } catch (DataIntegrityViolationException exception) {
            return getKidListModel().addObject(
                "error",
                String.format("bērns ar %s personas kodu jau eksistē", personalCode)
            );
        }

        return getKidListModel();
    }

    @PutMapping("/kid/family/{kidId}/{familyId}")
    @ResponseBody
    public String changeKidFamily(
        @PathVariable("kidId") Integer kidId,
        @PathVariable("familyId") Integer familyId
    ) {
        if (familyId.equals(- 1)) {
            this.kidHandler.setFamily(kidId, null);
        } else {
            this.kidHandler.setFamily(kidId, this.familyRepository.getOne(familyId));
        }

        return "done";
    }

    private ModelAndView getKidListModel() {
        return new ModelAndView("kidList")
            .addObject("kids", kidRepository.findAll())
            .addObject("families", familyRepository.findAll())
            .addObject("kidNameErrorMessage", KidHandler.kidNameErrorMessage)
            .addObject("kidLastNameErrorMessage", KidHandler.kidLastNameErrorMessage)
            .addObject("kidPersonalCodeErrorMessage", KidHandler.kidPersonalCodeErrorMessage)
            .addObject("kidNamePattern", KidHandler.kidNamePattern)
            .addObject("kidLastNamePattern", KidHandler.kidLastNamePattern)
            .addObject("kidPersonalCodePattern", KidHandler.kidPersonalCodePattern);
    }
}
