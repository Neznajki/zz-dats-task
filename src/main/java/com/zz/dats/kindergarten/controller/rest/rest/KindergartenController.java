package com.zz.dats.kindergarten.controller.rest.rest;

import com.zz.dats.kindergarten.db.entity.KidEntity;
import com.zz.dats.kindergarten.db.entity.KindergartenEntity;
import com.zz.dats.kindergarten.db.entity.QueueEntity;
import com.zz.dats.kindergarten.db.repository.*;
import com.zz.dats.kindergarten.handler.OrderHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Optional;

@Controller
public class KindergartenController {
    final KindergartenRepository kindergartenRepository;
    final KindergartenKidsRepository kindergartenKidsRepository;
    final KidRepository kidRepository;
    final QueueRepository queueRepository;
    final OrderHandler orderHandler;

    @Autowired
    public KindergartenController(
        KindergartenRepository kindergartenRepository,
        KindergartenKidsRepository kindergartenKidsRepository,
        KidRepository kidRepository,
        QueueRepository queueRepository,
        OrderHandler orderHandler
    ) {
        this.kindergartenRepository = kindergartenRepository;
        this.kindergartenKidsRepository = kindergartenKidsRepository;
        this.kidRepository = kidRepository;
        this.queueRepository = queueRepository;
        this.orderHandler = orderHandler;
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

        KindergartenEntity kindergartenEntity = kindergarten.get();

        orderHandler.sortKids(kindergartenEntity);

        return new ModelAndView("kindergartenInfo")
            .addObject("kindergarten", kindergartenEntity)
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
    public synchronized ModelAndView kindergartenAddKid(
        @PathVariable("id") Integer id
    ) {
        Optional<KindergartenEntity> kindergartenOptional = kindergartenRepository.findById(id);

        if (kindergartenOptional.isPresent()) {
            KindergartenEntity kindergartenEntity = kindergartenOptional.get();
            int limit = kindergartenEntity.getMaxKids() - kindergartenEntity.getKindergartenKidsById().size();

            if (limit > 0) {
                kindergartenKidsRepository.addKidsFromQueue(id, limit);
                queueRepository.removeAddedKids(id);
            }

        }


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

    private ModelAndView getKindergartenListModel() {
        return new ModelAndView("kindergartenList")
            .addObject("kindergartens", kindergartenRepository.findAll());
    }
}
