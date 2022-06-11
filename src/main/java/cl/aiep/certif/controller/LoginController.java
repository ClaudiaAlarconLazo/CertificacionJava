package cl.aiep.certif.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cl.aiep.certif.controller.service.CursoService;
import cl.aiep.certif.controller.service.EstudianteService;
import cl.aiep.certif.dao.dto.EstudianteDTO;
import cl.aiep.certif.util.CommonUtil;

@Controller
public class LoginController {

	@Autowired
	private EstudianteService serviceEst;

	@Autowired
	private CursoService serviceCurso;

	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}

	@GetMapping("/salir")
	public String viewLogoutPage() {
		return "redirect:/";
	}

	@GetMapping("/registrate")
	public String viewRegister(final Model model) {
		model.addAttribute("estudiante", new EstudianteDTO());
		model.addAttribute("regiones", serviceEst.obtienRegiones());
		return "nuevo";

	}

	@PostMapping("/guardar")
	public String guardar(@Valid EstudianteDTO estudiante, BindingResult result, final Model model) {
		if (result.hasErrors()) {
			model.addAttribute("estudiante", estudiante);
			model.addAttribute("regiones", serviceEst.obtienRegiones());
			model.addAttribute("mensaje", result.getFieldError().getDefaultMessage());
			return "nuevo";
		} else {

		}

		if (CommonUtil.validarRut(estudiante.getRut()))
			serviceEst.guardaEstudiante(estudiante);
		else {
			model.addAttribute("estudiante", estudiante);
			model.addAttribute("regiones", serviceEst.obtienRegiones());
			model.addAttribute("mensaje", "Rut Inválido.");
			return "nuevo";

		}

		return "redirect:/home";

	}

	// inicio
	@GetMapping({ "/" })
	public String indexLibre(final Model model) {
		model.addAttribute("cursos", serviceCurso.obtenerCursos());
		return "indexLibre";
	}

	@GetMapping("/home")
	@PreAuthorize("isAuthenticated()")
	public String homeCursos(final Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toString();
		if (role.contains("ADMIN")) {
			return "redirect:/admin";
		} else {
			model.addAttribute("cursos", serviceCurso.obtenerCursos());
			return "index";
		}
	}

	@GetMapping("/admin")
	@PreAuthorize("isAuthenticated()")
	public String adminCursos(final Model model) {
		model.addAttribute("cursos", serviceCurso.obtenerCursos());
		return "indexAdmin";
	}

	@GetMapping("/panel")
	public String panelEstudiante(final Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int id = serviceEst.obtenerCurso(auth.getName()).getId();
		
		if (id != 0) {
			model.addAttribute("curso", serviceEst.obtenerCurso(auth.getName()));
			model.addAttribute("contenidos", serviceCurso.obtenerContenidos(id));
			return "panel";
		} else {
			model.addAttribute("curso", null);
			model.addAttribute("mensaje", "No estás inscrito en ningún curso. Vuelve para revisar los cursos disponibles.");
			return "panel";
		}
	}

}
