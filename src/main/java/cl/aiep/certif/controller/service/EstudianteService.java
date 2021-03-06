package cl.aiep.certif.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.aiep.certif.dao.EstudianteDAO;
import cl.aiep.certif.dao.dto.CursoDTO;
import cl.aiep.certif.dao.dto.EstudianteDTO;
import cl.aiep.certif.dao.dto.RegionDTO;

@Service
public class EstudianteService {

	@Autowired
	private EstudianteDAO dao;
	
	public List<EstudianteDTO> obtenerEstudiantes(){
		return dao.obtenerEstudiantes();
	}
	
	public boolean guardaEstudiante(EstudianteDTO estud) {
		
		return dao.guardaEstudiante(estud);
	}
	
	public List<RegionDTO> obtienRegiones(){
		return dao.obtieneRegiones();
	}
	
	public boolean asignarCurso(String rut, Integer idcurso) {
		return dao.agregaCursos(rut, idcurso);
	}

	public CursoDTO obtenerCurso(String rut) {
		return dao.obtieneCurso(rut);
	}
}
