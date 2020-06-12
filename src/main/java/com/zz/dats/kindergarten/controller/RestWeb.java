package com.zz.dats.kindergarten.controller;

import com.zz.dats.kindergarten.db.entity.KindergartenEntity;
import com.zz.dats.kindergarten.db.repository.KindergartenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class RestWeb {

    @Autowired
    KindergartenRepository kindergartenRepository;

    @GetMapping("")
    public ModelAndView homepage() {
        return new ModelAndView("layout");
    }

    @GetMapping("kindergarten")
    public ModelAndView kindergarten() {
        return getKindergartenListModel();
    }

    @RequestMapping("/kindergarten/{id}")
    public ModelAndView kindergarten(
        @PathVariable("id") Integer id
    ) {
        Optional<KindergartenEntity> kindergarten = kindergartenRepository.findById(id);

        if (kindergarten.isEmpty()) {
            return getKindergartenListModel().addObject("error", String.format("bērnudārzs ar id %d nav atrasts", id));
        }

        return new ModelAndView("kindergartenInfo").addObject("kindergarten", kindergarten.get());
    }

    private ModelAndView getKindergartenListModel() {
        return new ModelAndView("kindergartenList")
            .addObject("kindergartens", kindergartenRepository.findAll());
    }
}
