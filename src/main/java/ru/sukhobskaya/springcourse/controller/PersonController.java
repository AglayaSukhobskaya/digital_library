package ru.sukhobskaya.springcourse.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sukhobskaya.springcourse.model.Book;
import ru.sukhobskaya.springcourse.model.Person;
import ru.sukhobskaya.springcourse.service.PersonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonController {
    PersonService personService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, @ModelAttribute("book") Book book, Model model) {
        model.addAttribute("person", personService.findById(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPersonPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personService.create(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("person", personService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, @PathVariable("id") Integer id,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
