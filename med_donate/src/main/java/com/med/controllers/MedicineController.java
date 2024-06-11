package com.med.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.med.entities.Medicine;
import com.med.entities.RequestMedicine;
import com.med.entities.User;
import com.med.forms.MedicineForm;
import com.med.forms.MedicineSearchForm;
import com.med.helpers.AppConstants;
import com.med.helpers.Helper;
import com.med.helpers.Message;
import com.med.helpers.MessageType;
import com.med.services.MedicineService;
import com.med.services.RequestMedicineService;
import com.med.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/medicines")
public class MedicineController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(MedicineController.class);

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private RequestMedicineService requestMedicineService;

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    // add medicine page: handler
    public String addMedicineView(Model model) {
        MedicineForm medicineForm = new MedicineForm();

        model.addAttribute("medicineForm", medicineForm);
        return "user/add_medicine";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveMedicine(@Valid @ModelAttribute MedicineForm medicineForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        // process the form data

        // 1 validate form

        if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_medicine";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> Medicine

        User user = userService.getUserByEmail(username);
        // 2 process the medicine picture


        Medicine medicine = new Medicine();
        medicine.setName(medicineForm.getName());
        medicine.setPhoneNumber(medicineForm.getPhoneNumber());
        medicine.setAddress(medicineForm.getAddress());
        medicine.setDescription(medicineForm.getDescription());
        medicine.setUser(user);                   
        medicineService.save(medicine);
        System.out.println(medicineForm);

        // 3 set the medicine picture url

        // 4 `set message to be displayed on the view

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new medicine")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/medicines/add";

    }

    // view medicines

    @RequestMapping(method = RequestMethod.GET)
    public String viewsMedicine(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
            Authentication authentication) {

        // load all the user medicines
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Medicine> pageMedicine = medicineService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageMedicine", pageMedicine);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("medicineSearchForm", new MedicineSearchForm());

        return "user/medicines";
    }

    // all available medicines
    @RequestMapping("/all_medicines")
    public String getAllMedicines(Model m) {
        m.addAttribute("allDonatedMedicine", medicineService.getAll());
        return "all_donatedMedicines";
    }

       //  medicines
       @RequestMapping("/requestForMedicine")
       public String Medicines() {
           return "requestForMedicine";
       }

       
       //  check medicine present at this moment
       @RequestMapping("/checkMedicine")
       public String checkMedicine(@ModelAttribute RequestMedicine requestMedicinemedicine) {
        requestMedicineService.save(requestMedicinemedicine);
           return "checkMedicine";
       }  
}
