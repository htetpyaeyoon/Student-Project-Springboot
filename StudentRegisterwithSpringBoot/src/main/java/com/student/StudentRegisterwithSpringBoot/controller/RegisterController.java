package com.student.StudentRegisterwithSpringBoot.controller;

import com.student.StudentRegisterwithSpringBoot.Service.UserService;
import com.student.StudentRegisterwithSpringBoot.model.Role;
import com.student.StudentRegisterwithSpringBoot.model.UserBean;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
public class RegisterController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    UserService userservice;

    @GetMapping("/")
    public ModelAndView loginForm() {
        return new ModelAndView("login", "user", new UserBean());
    }

    @GetMapping("/registerform")
    public ModelAndView register() {
        return new ModelAndView("userRegister", "bean", new UserBean());
    }

    @PostMapping("/register")
    public String userRegister(@ModelAttribute("user") @Validated UserBean obj, ModelMap m) {
        obj.setRole(Role.USER.name());
        userservice.adduser(obj);
        return "redirect:/";
    }

    @GetMapping("/StudentRegisterwithSpringBoot/logout")
    public ModelAndView logout() {
        return new ModelAndView("login", "user", new UserBean());
    }


    @PostMapping("/login")
    public String Login(@ModelAttribute("user") @Validated UserBean bean, ModelMap m, HttpSession session) {
        List<UserBean> list = userservice.selectAlluser();
        boolean emailMatch = false;
        boolean passwordMatch = false;
        for (UserBean user : list) {
            if (user.getEmail().equals(bean.getEmail())) {
                emailMatch = true;

                if (user.getPassword().equals(bean.getPassword())) {
                    passwordMatch = true;

                    if (user.getRole().equals(Role.ADMIN.name())) {
                        session.setAttribute("adminId", user.getId());
                        session.setAttribute("adminName", user.getName());
                        session.setAttribute("adminRole", user.getRole());
                        return "redirect:/adminpage";
                    } else if (user.getRole().equals(Role.USER.name())) {
                        session.setAttribute("userId", user.getId());
                        session.setAttribute("userName", user.getName());
                        session.setAttribute("userRole", user.getRole());
                        return "redirect:/userpage";
                    }
                }
            }
        }

        if (!emailMatch) {
            m.addAttribute("erroremail", "Email is wrong. Check again.");
        } else if (!passwordMatch) {
            m.addAttribute("errorpass", "Password is wrong. Check again.");
        }

        return "login";
    }

    @GetMapping("/adminpage")
    public String adminhome(ModelMap model, HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return "redirect:/login";
        } else {
            return "adminpage";
        }
    }

    @GetMapping("/userpage")
    public String userhome(ModelMap model, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        } else {
            return "userpage";
        }
    }

    @GetMapping("/StudentRegisterwithSpringBoot/userlist")
    public ModelAndView userManagement(ModelMap m) {
        List<UserBean> list = userservice.selectAlluser();
        m.addAttribute("list", list);
        return new ModelAndView("userList");
    }

    @GetMapping("/setupUpdate/{id}")
    public ModelAndView userUpdateform(@PathVariable("id") String id) {
        UserBean user = userservice.selectById(id).orElse(null);
        return new ModelAndView("updateUser", "user", user);
    }

    @PostMapping("/updateuser")
    public String updateUser(@ModelAttribute("user") @Validated UserBean obj, ModelMap m, HttpSession session, RedirectAttributes redirectAttributes) {
        userservice.update(obj);
        UserBean userBean = userservice.selectById(obj.getId()).orElse(null);
        String name = userBean.getName();
        String id = (String) session.getAttribute("adminId");
        if (obj.getId().equals(id)) {
            session.removeAttribute("adminName");
            session.setAttribute("adminName", name);
        }
        redirectAttributes.addFlashAttribute("list", userservice.selectAlluser());
        return "redirect:/StudentRegisterwithSpringBoot/userlist";
    }

    @GetMapping("/StudentRegisterwithSpringBoot/adduserform")
    public ModelAndView addUsers() {
        return new ModelAndView("addUser", "bean", new UserBean());
    }

    @PostMapping("/adduser")
    public String UserAdd(@ModelAttribute("bean") @Validated UserBean obj, ModelMap m) {
        userservice.adduser(obj);
        return "redirect:/StudentRegisterwithSpringBoot/userlist";
    }

    @GetMapping("/deleteuser/{id}")
    public String deleteStudent(@PathVariable("id") String id) {
        // Soft delete the student by updating the 'deleted' flag
        userservice.softDeleteUser(id);
        return "redirect:/StudentRegisterwithSpringBoot/userlist";
    }

    @GetMapping("/ExportServlet/{export}")
    public void generateReport(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable("export") String export) throws IOException {
        String path = servletContext.getRealPath("/WEB-INF/jasper/user.jrxml");
        JRBeanCollectionDataSource source = null;
        JasperReport jasperReport;
        JasperPrint print;
        ArrayList<UserBean> list = new ArrayList<>();

        Object listAttribute = servletContext.getAttribute("list"); // Get the attribute from ServletContext
        if (listAttribute instanceof Vector) { // Check if the attribute is an instance of Vector
            Vector<UserBean> vectorList = (Vector<UserBean>) listAttribute; // Cast the attribute to Vector<User>
            list = new ArrayList<>(vectorList); // Convert Vector to ArrayList
        } else if (listAttribute instanceof ArrayList) { // Check if the attribute is an ArrayList
            list = (ArrayList<UserBean>) listAttribute; // Cast the attribute to ArrayList<User>
        } else {
            // Handle the case where the attribute is not set or is not of the expected type
            System.out.println("Attribute 'list' is null or not an instance of Vector or ArrayList");
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "User List");

        try {
            source = new JRBeanCollectionDataSource(list);
            jasperReport = JasperCompileManager.compileReport(path);
            print = JasperFillManager.fillReport(jasperReport, parameters, source);

            if ("excel".equals(export)) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=userList.xls");

                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setExporterInput(new SimpleExporterInput(print));
                exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                exporterXLS.setConfiguration(configuration);
                exporterXLS.exportReport();
            } else if ("pdf".equals(export)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=userList.pdf");

                JRPdfExporter exporterPdf = new JRPdfExporter();
                exporterPdf.setExporterInput(new SimpleExporterInput(print));
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                exporterPdf.exportReport();
            } else {
                // Handle invalid export types
                System.out.println("Invalid export type: " + export);
                // Optionally, you could return a response indicating invalid input or handle it in some other way
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
