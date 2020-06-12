package com.zz.dats.kindergarten.controller;

import com.zz.dats.kindergarten.db.entity.FamilyNameEntity;
import com.zz.dats.kindergarten.db.entity.KidEntity;
import com.zz.dats.kindergarten.db.entity.KindergartenEntity;
import com.zz.dats.kindergarten.db.entity.QueueEntity;
import com.zz.dats.kindergarten.db.repository.*;
import com.zz.dats.kindergarten.handler.KidHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Optional;

@Controller
public class RestWeb {
    @Autowired
    KidHandler kidHandler;
    @Autowired
    KindergartenRepository kindergartenRepository;
    @Autowired
    KindergartenKidsRepository kindergartenKidsRepository;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    KidRepository kidRepository;
    @Autowired
    QueueRepository queueRepository;

    @GetMapping("")
    public ModelAndView homepage() {
        return new ModelAndView("layout");
    }

    @GetMapping("/kindergarten")
    public ModelAndView kindergarten() {
        return getKindergartenListModel();
    }

    @GetMapping("/kindergarten/{id}")
    public ModelAndView kindergarten(
        @PathVariable("id") Integer id
    ) {
        Optional<KindergartenEntity> kindergarten = kindergartenRepository.findById(id);

        if (kindergarten.isEmpty()) {
            return getKindergartenListModel().addObject("error", String.format("bērnudārzs ar id %d nav atrasts", id));
        }

        return new ModelAndView("kindergartenInfo")
            .addObject("kindergarten", kindergarten.get())
            .addObject("kids", kidRepository.getKidsAvailableFor(id));
    }

    @DeleteMapping("/kindergarten/{id}")
    @ResponseBody
    public String kindergartenRemoveKid(
        @PathVariable("id") Integer id,
        @RequestParam(name = "kidId") Integer kidId
    ) {
        queueRepository.deleteByKindergartenAndKid(id, kidId);

        return "done";
    }

    @PostMapping("/kindergarten/dispatch/{id}")
    public ModelAndView kindergartenAddKid(
        @PathVariable("id") Integer id
    ) {
        kindergartenKidsRepository.addKidsFromQueue(id);
        queueRepository.removeAddedKids(id);

        return new ModelAndView(String.format("redirect:/kindergarten/%d", id));
    }

    @PostMapping("/kindergarten/{id}")
    public ModelAndView kindergartenAddKid(
        @PathVariable("id") Integer id,
        @RequestParam(name = "newMember") Integer kidId
    ) {
        Optional<KindergartenEntity> kindergarten = kindergartenRepository.findById(id);
        if (kindergarten.isEmpty()) {
            throw new IllegalArgumentException(String.format("kindergarten with id %d doesn't exists", id));
        }

        Optional<KidEntity> kid = kidRepository.findById(kidId);
        if (kid.isEmpty()) {
            throw new IllegalArgumentException(String.format("kindergarten with id %d doesn't exists", kidId));
        }

        QueueEntity queueEntity = new QueueEntity();

        queueEntity.setKidByKidId(kid.get());
        queueEntity.setKindergartenByKindergartenId(kindergarten.get());
        queueEntity.setAdded(new Timestamp(System.currentTimeMillis()));

        try {
            queueRepository.save(queueEntity);
        } catch (DataIntegrityViolationException ignored) {
        } catch (Exception exception) {
            LoggerFactory.getLogger(this.getClass()).error("could not save new record to queue", exception);
        }

        return this.kindergarten(id);
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
        if (familyId.equals(-1)) {
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

    private ModelAndView getKindergartenListModel() {
        return new ModelAndView("kindergartenList")
            .addObject("kindergartens", kindergartenRepository.findAll());
    }
}
